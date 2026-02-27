package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;

public class ShooterSetSpeed extends Command {
    private final Shooter mShooterMotor;
    private final Loader mLoaderMotor;
    private double mSpeed;

    /**
     * @param Shooter
     * @param Loader
     */

    public ShooterSetSpeed(Shooter shooter, Loader loader, double speed) {
        mShooterMotor = shooter;
        mLoaderMotor = loader;
        mSpeed = speed;
        addRequirements(shooter, loader);
    }

    public void initialize() {}

    public void execute() {
        mShooterMotor.setSpeed(mSpeed);
        mLoaderMotor.setSpeed(mSpeed);

        if(mSpeed == 0.0) {
            mShooterMotor.stopMotor();
            mLoaderMotor.stopMotor();
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
