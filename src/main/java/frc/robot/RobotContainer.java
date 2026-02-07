// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.AlignToTag;
import frc.robot.commands.ClimberSetSpeed;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.IntakeSetSpeed;
import frc.robot.commands.LoaderSetSpeed;
import frc.robot.commands.RetractIntake;
import frc.robot.commands.ShooterSetSpeed;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.IntakeMotor;
import frc.robot.subsystems.IntakePneumatics;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
    private double MaxSpeed = 0.5 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top 1
                                                                                        // speed
    private double MaxAngularRate = RotationsPerSecond.of(0.25).in(RadiansPerSecond); // 3/4 of a rotation per second .75
                                                                                      // max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    public final SwerveRequest.FieldCentric fieldCentric = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    public final SwerveRequest.RobotCentric robotCentric = new SwerveRequest.RobotCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public boolean useFieldCentric = true;
    private double time;

    private final CommandXboxController controller = new CommandXboxController(0);
    private final CommandJoystick joystick = new CommandJoystick(1);

    private final Climber m_climber = new Climber();
    private final IntakePneumatics m_intakePneumatics = new IntakePneumatics();
    private final IntakeMotor m_IntakeMotor = new IntakeMotor();
    private final Loader m_loader = new Loader();
    private final Shooter m_shooter = new Shooter();

    private final AlignToTag m_alignToTag = new AlignToTag(drivetrain, useFieldCentric, "limelight-climber", controller,
            MaxSpeed);
    private final ClimberSetSpeed m_climberSetSpeed = new ClimberSetSpeed(m_climber, 1.0);
    private final ExtendIntake m_extendIntake = new ExtendIntake(m_intakePneumatics);
    private final RetractIntake m_retractIntake = new RetractIntake(m_intakePneumatics);
    private final IntakeSetSpeed m_intakeSetSpeed = new IntakeSetSpeed(m_IntakeMotor, 1.0);
    private final LoaderSetSpeed m_loaderSetSpeed = new LoaderSetSpeed(m_loader, 1.0);
    private final ShooterSetSpeed m_shooterSetSpeed = new ShooterSetSpeed(m_shooter, 1.0);

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
                // Drivetrain will execute this command periodically
                drivetrain.applyRequest(() -> {
                    if (useFieldCentric) {
                        return fieldCentric.withVelocityX(-controller.getLeftY() * MaxSpeed) // Drive forward with
                                                                                             // negative Y (forward)
                                .withVelocityY(-controller.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                                .withRotationalRate(-controller.getRightX() * MaxAngularRate); // Drive counterclockwise
                                                                                               // with negative X (left)
                    } else {
                        return robotCentric.withVelocityX(-controller.getLeftY() * MaxSpeed) // Drive forward with
                                                                                             // negative Y (forward)
                                .withVelocityY(-controller.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                                .withRotationalRate(-controller.getRightX() * MaxAngularRate); // Drive counterclockwise
                                                                                               // with negative X (left)
                    }
                }));

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
                drivetrain.applyRequest(() -> idle).ignoringDisable(true));

        controller.a().whileTrue(drivetrain.applyRequest(() -> brake));
        controller.b().whileTrue(drivetrain.applyRequest(
                () -> point.withModuleDirection(new Rotation2d(-controller.getLeftY(), -controller.getLeftX()))));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        controller.back().and(controller.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        controller.back().and(controller.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        controller.start().and(controller.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        controller.start().and(controller.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        controller.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));
        controller.rightBumper().onTrue(Commands.runOnce(() -> {
            useFieldCentric = !useFieldCentric;
            SmartDashboard.putBoolean("Field Centric", useFieldCentric);
        }));
        joystick.button(1).and(joystick.povUp()).whileTrue(m_intakeSetSpeed);
        joystick.button(1).and(joystick.povDown()).whileTrue(m_intakeSetSpeed); // need to make negative
        joystick.povUp().whileTrue(m_shooterSetSpeed);
        joystick.povDown().whileTrue(m_shooterSetSpeed); // need to make negative
        joystick.button(7).whileTrue(m_loaderSetSpeed);
        joystick.button(11).onTrue(m_extendIntake);
        joystick.button(12).onTrue(m_retractIntake);
        controller.povUp().whileTrue(m_alignToTag);

        joystick.button(3).whileTrue(m_climberSetSpeed);
        joystick.button(4).whileTrue(m_climberSetSpeed); // make negative

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        // Simple drive forward auton
        final var idle = new SwerveRequest.Idle();
        return Commands.sequence(
                // Reset our field centric heading to match the robot
                // facing away from our alliance station wall (0 deg).
                drivetrain.runOnce(() -> drivetrain.seedFieldCentric(Rotation2d.kZero)),
                // Then slowly drive forward (away from us) for 5 seconds.
                drivetrain.applyRequest(() -> fieldCentric.withVelocityX(0.5)
                        .withVelocityY(0)
                        .withRotationalRate(0))
                        .withTimeout(5.0),
                // Finally idle for the rest of auton
                drivetrain.applyRequest(() -> idle));
    }

    public void periodic() {
        time = DriverStation.getMatchTime();
        SmartDashboard.putNumber("Match Time", time);
        SmartDashboard.putNumber("Match Time", time < 0 ? 0 : time);
    }
}