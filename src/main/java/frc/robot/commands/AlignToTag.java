package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveRequest.RobotCentric;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class AlignToTag extends Command {
    private final CommandSwerveDrivetrain m_Drivetrain;
    private Boolean m_useFieldCentric;
    private String m_limelightName;
    // Tune these constants for your robot!
    private final PIDController m_turnPID = new PIDController(0.05, 0, 0.001);
    private final PIDController m_movePID = new PIDController(0.05, 0, 0);

    public AlignToTag(CommandSwerveDrivetrain drivetrain, Boolean useFieldCentric, String limelightName) {
        this.m_Drivetrain = drivetrain;
        this.m_useFieldCentric = useFieldCentric;
        this.m_limelightName = limelightName;

        addRequirements(drivetrain);
        m_turnPID.setTolerance(2.0); // Stop within 2 degrees
    }

    @Override
    public void execute() {
        boolean hasTarget = LimelightHelpers.getTV(m_limelightName);
        double tx = LimelightHelpers.getTX(m_limelightName);
        double ty = LimelightHelpers.getTY(m_limelightName);

        // Calculate speeds: we want tx and ty to reach our "sweet spot"
        double rotationSpeed = m_turnPID.calculate(tx, 0);
        double forwardSpeed = m_movePID.calculate(ty, 5.0); // 5.0 is a sample ty setpoint

        if (hasTarget && !m_useFieldCentric) {
            var req = new RobotCentric()
                    .withVelocityX(-forwardSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(0.0) // Drive left with negative X (left)
                    .withRotationalRate(-rotationSpeed); // Drive counterclockwise with negative X (left)
            m_Drivetrain.setControl(req);
        } else {
            m_Drivetrain.idle();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_Drivetrain.idle();
    }
}