package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.UnderShooterMotor;

public class ShooterSetSpeedAuto extends Command {
    private final Shooter mShooterMotor;
    private final Loader mLoaderMotor;
    private final UnderShooterMotor mUnderShooterMotor;
    private double msSpeed;
    private double mlSpeed;
    private double muSpeed;
    private boolean mEnd;

    /**
     * @param Shooter
     * @param Loader
     */

    public ShooterSetSpeedAuto(Shooter shooter, Loader loader, UnderShooterMotor underShooterMotor, double sSpeed, double lSpeed, double uSpeed) {
        mShooterMotor = shooter;
        mLoaderMotor = loader;
        mUnderShooterMotor = underShooterMotor;
        msSpeed = sSpeed;
        mlSpeed = lSpeed;
        muSpeed = uSpeed;
        addRequirements(mShooterMotor, mLoaderMotor, mUnderShooterMotor);
    }

    public void initialize() {
        mEnd = false;
    }

    public void execute() {
        mShooterMotor.setSpeed(msSpeed);
        mLoaderMotor.setSpeed(mlSpeed);
        Timer.delay(.75);
        mUnderShooterMotor.setSpeed(muSpeed);
        Timer.delay(5);
        mEnd = true;

    }

    @Override
    public void end(boolean interrupted) {
        mShooterMotor.stopMotor();
        mLoaderMotor.stopMotor();
        mUnderShooterMotor.stopMotor();
    }

    @Override
    public boolean isFinished() {
        if(mEnd) {
            return true;
        }
        return false;
    }
}
