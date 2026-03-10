package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
    private String limelight = Constants.LIMELIGHT_NAME;

    public Limelight() {}

    public double limelightAimProportional(double MaxAngularRate) {
        double kp = 0.1;
        double tx = LimelightHelpers.getTX(limelight);
        double offset = -2.5;
        double targetingAngularVelocity = (tx + offset) * kp;
        targetingAngularVelocity *= MaxAngularRate;
        targetingAngularVelocity *= -1.0;
        return targetingAngularVelocity;
    }

    public double limelightRangeProportional(double MaxSpeed) {
        double kp = 0.1;
        double ty = LimelightHelpers.getTY(limelight);
        double offset = -7.5;
        double targetingRangeVelocity = (ty + offset) * kp;
        targetingRangeVelocity *= MaxSpeed;
        targetingRangeVelocity *= -1.0;
        return targetingRangeVelocity;
    }
}