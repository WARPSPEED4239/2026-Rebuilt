package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class AlignToTag extends Command {
    private final CommandSwerveDrivetrain mDrivetrain;
    private Boolean mUseFieldCentric;
    private String mLimelightName;
    private CommandXboxController mController;
    private double mMaxAngularRate;
    private SwerveRequest.RobotCentric mDrive;

    private final PIDController m_turnPID = new PIDController(0.05, 0, 0.001);
    private final PIDController m_movePID = new PIDController(0.05, 0, 0);

    public AlignToTag(CommandSwerveDrivetrain drivetrain, SwerveRequest.RobotCentric drive, Boolean useFieldCentric, String limelightName, CommandXboxController controller, double maxAngularRate) {
        this.mDrivetrain = drivetrain;
        this.mUseFieldCentric = useFieldCentric;
        this.mLimelightName = limelightName;
        mController = controller;
        mMaxAngularRate = maxAngularRate;
        mDrive = drive;

        addRequirements(drivetrain);
        m_turnPID.setTolerance(2.0); // Stop within 2 degrees
    }

    @Override
    public void execute() {
        boolean hasTarget = LimelightHelpers.getTV(mLimelightName);
        double tx = LimelightHelpers.getTX(mLimelightName);
        double ty = LimelightHelpers.getTY(mLimelightName);

        // tune
        double rotationSpeed = m_turnPID.calculate(tx, 0);
        double forwardSpeed = m_movePID.calculate(ty, 5.0);

        if (hasTarget && !mUseFieldCentric) {        
            mDrivetrain.setControl(
                mDrive.withVelocityX(-forwardSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(rotationSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-mController.getRightX() * mMaxAngularRate) // Drive counterclockwise with negative X (left)
            );
        } else {
            mDrivetrain.idle();
        }
    }

    @Override
    public void end(boolean interrupted) {
        mDrivetrain.idle();
    }

    public boolean isFinished() {
        return false;
    }
}