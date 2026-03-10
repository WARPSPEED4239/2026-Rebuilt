// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest.RobotCentric;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Limelight;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class LimelightTest extends Command {

  private final String limelight = Constants.LIMELIGHT_NAME;
  private final Limelight mLimelight;
  private double maxSpeed;
  private double maxAngularRate;

  private final CommandXboxController controller;
  private final CommandSwerveDrivetrain drive;
  private final RobotCentric request = new RobotCentric()
    .withDeadband(0.02)
    .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  
  /**
   * @param limelight
   * @param drivetrain
   */

  /** Creates a new LmelightTest. */
  public LimelightTest(Limelight limelight, CommandSwerveDrivetrain drivetrain, CommandXboxController controller, double MaxSpeed, double MaxAngularRate) {
    mLimelight = limelight;
    drive = drivetrain;
    maxAngularRate = MaxAngularRate;
    maxSpeed = MaxSpeed;
    this.controller = controller;
    addRequirements(drivetrain, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(LimelightHelpers.getTV(limelight)) {
      drive.setControl(request.withVelocityX(mLimelight.limelightRangeProportional(maxSpeed))
        .withVelocityY(-controller.getLeftX())
        .withRotationalRate(mLimelight.limelightAimProportional(maxAngularRate)));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}