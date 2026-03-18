package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeMotor;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.UnderShooterMotor;

public class AutoIntakeSetSpeed extends Command {
    private final IntakeMotor mIntakeMotor;
    private final Loader mLoader;
    private final UnderShooterMotor mShooter;
    private double iSpeed;
    private double lSpeed;
    private double sSpeed;

    /**
     * @param IntakeMotor
     */
    public AutoIntakeSetSpeed(IntakeMotor intakeMotor, Loader loader, UnderShooterMotor underShooterMotor, double miSpeed, double mlSpeed, double msSpeed) {
        mIntakeMotor = intakeMotor;
        mLoader = loader;
        mShooter = underShooterMotor;
        iSpeed = miSpeed;
        lSpeed = mlSpeed;
        sSpeed = msSpeed;
        addRequirements(intakeMotor, loader, underShooterMotor);
    }

    public void initialize() {}

    public void execute() {
        mIntakeMotor.setSpeed(iSpeed);
        mLoader.setSpeed(lSpeed);
        mShooter.setSpeed(-sSpeed);

        if(iSpeed == 0.0) {
            mIntakeMotor.stopMotor();
        }

        if(lSpeed == 0.0) {
            mLoader.stopMotor();
        }

        if(sSpeed == 0.0) {
            mShooter.stopMotor();
        }
    }
    
    @Override
    public void end(boolean interrupted) {
        mIntakeMotor.stopMotor();
        mLoader.stopMotor();
        mShooter.stopMotor();
    }

    public boolean isFinished() {
        return false;
    }
}
