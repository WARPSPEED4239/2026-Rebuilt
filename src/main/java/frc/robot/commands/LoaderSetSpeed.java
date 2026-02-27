package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;

public class LoaderSetSpeed extends Command {
    private final Loader mLoader;
    private final Shooter mShooter;
    private double mSpeed;
    private double sSpeed;
    private boolean mEnd;
    /**
     * @param Loader
     */
    public LoaderSetSpeed(Loader loader, Shooter shooter, double loaderSpeed, double shooterSpeed) {
        mLoader = loader;
        mShooter = shooter;
        mSpeed = loaderSpeed;
        sSpeed = shooterSpeed;
        addRequirements(loader, shooter);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        mLoader.setSpeed(mSpeed);
        mShooter.setSpeed(sSpeed);

        if(mSpeed == 0.0) {
            mLoader.stopMotor();
            mShooter.stopMotor();
        }
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