package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LimelightHelpers;

public class llPoseDistance {
    /**
     * Gets the distance to the target using 3D pose data.
     * 
     * @param limelightName The name of the Limelight.
     * @return The distance in meters.
     */
    public static double getDistanceUsingPose(String limelightName) {
        // Get botpose in target space (an array of [tx, ty, tz, rx, ry, rz])
        // The first three elements (indices 0, 1, 2) are translation values (x, y, z)
        // in meters.
        double[] botPose = LimelightHelpers.getBotPose_TargetSpace(limelightName);

        if (botPose.length == 0) {
            return 0.0; // No target found
        }

        // Distance is the magnitude of the translation vector (Euclidean distance)
        double x = botPose[0];
        double y = botPose[1];
        double z = botPose[2];

        double distance = Math.sqrt(x * x + y * y + z * z);

        return distance; // Distance in meters
    }

    public void periodic() {
        SmartDashboard.putNumber("Distance To Tag", getDistanceUsingPose("limelight-climber"));
    }
}