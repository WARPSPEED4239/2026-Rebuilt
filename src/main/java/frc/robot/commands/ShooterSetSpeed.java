package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class ShooterSetSpeed extends Command {
    private final Shooter mShooterMotor;
    private double mSpeed;

    /**
     * @param Shooter
     */

    public ShooterSetSpeed(Shooter shooter, double speed) {
        mShooterMotor = shooter;
        mSpeed = speed;
        addRequirements(shooter);
    }

    public void initialize() {}

    public void execute() {
        mShooterMotor.setSpeed(mSpeed);

        if(mSpeed == 0.0) {
            mShooterMotor.stopMotor();
        }
    }

    @Override
    public void end(boolean interrupted) {
        mShooterMotor.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
