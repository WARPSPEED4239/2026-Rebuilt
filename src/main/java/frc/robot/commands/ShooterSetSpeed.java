package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.UnderShooterMotor;

public class ShooterSetSpeed extends Command {
    private final Shooter mShooterMotor;
    private final Loader mLoaderMotor;
    private final UnderShooterMotor mUnderShooterMotor;
    private double mSpeed;

    /**
     * @param Shooter
     * @param Loader
     */

    public ShooterSetSpeed(Shooter shooter, Loader loader, UnderShooterMotor underShooterMotor, double speed) {
        mShooterMotor = shooter;
        mLoaderMotor = loader;
        mUnderShooterMotor = underShooterMotor;
        mSpeed = speed;
        addRequirements(mShooterMotor, mLoaderMotor, mUnderShooterMotor);
    }

    public void initialize() {}

    public void execute() {
        mShooterMotor.setSpeed(-mSpeed);
        mLoaderMotor.setSpeed(mSpeed);
        mUnderShooterMotor.setSpeed(mSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        mShooterMotor.stopMotor();
        mLoaderMotor.stopMotor();
        mUnderShooterMotor.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
