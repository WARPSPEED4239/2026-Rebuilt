package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest.RobotCentric;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Limelight;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class LimelightTest extends Command {

  private final String limelight = Constants.LIMELIGHT_NAME;

  private double maxSpeed;
  private double maxAngularRate;
  private boolean mEnd;

  private final CommandXboxController controller;
  private final CommandSwerveDrivetrain drive;
  private final RobotCentric request = new RobotCentric()
    .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  
  /**
   * @param drivetrain
   */

  /** Creates a new LmelightTest. */
  public LimelightTest(CommandSwerveDrivetrain drivetrain, CommandXboxController controller, double MaxSpeed, double MaxAngularRate) {
    drive = drivetrain;
    maxAngularRate = MaxAngularRate;
    maxSpeed = MaxSpeed;
    this.controller = controller;
    addRequirements(drivetrain);
  }

  public double limelightAimProportional() {
        double kp = 0.07;
        double tx = LimelightHelpers.getTX(limelight);
        double offset = 0;
        double targetingAngularVelocity = (tx + offset) * kp;
        targetingAngularVelocity *= maxAngularRate;
        return targetingAngularVelocity;
    }

  public double limelightRangeProportional() {
        double kp = 0.06;
        double ty = LimelightHelpers.getTY(limelight);
        double offset = 4.7;
        double targetingRangeVelocity = (ty + offset) * kp;
        targetingRangeVelocity *= maxSpeed;
        targetingRangeVelocity *= 1.0;
        return targetingRangeVelocity;
    }

  @Override
  public void initialize() {
    mEnd = false;
  }

  @Override
  public void execute() {
    limelightAimProportional();
    limelightRangeProportional();

    if(LimelightHelpers.getTV(limelight)) {
      drive.setControl(request.withVelocityX(limelightRangeProportional())
        .withVelocityY(-controller.getLeftX())
        .withRotationalRate(limelightAimProportional()));
    }

    if(limelightRangeProportional() > -0.5 && limelightRangeProportional() < 0.5 && limelightAimProportional() > -0.5 && limelightAimProportional() < 0.5 && LimelightHelpers.getTV(limelight)) {
      mEnd = true;
    }

    SmartDashboard.putNumber("Aim Proportional", limelightAimProportional());
    SmartDashboard.putNumber("Range Proportional", limelightRangeProportional());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    if(mEnd) {
      return true;
    }
    return false;
  }
}