package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Loader;

public class LoaderSetSpeed extends Command {
    private final Loader mLoader;
    private double mSpeed;
    /**
     * @param Loader
     */
    public LoaderSetSpeed(Loader loader, double speed) {
        mLoader = loader;
        mSpeed = speed;
        addRequirements(loader);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        mLoader.setSpeed(mSpeed);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}