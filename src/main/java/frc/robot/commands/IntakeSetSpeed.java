package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeMotor;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;

public class IntakeSetSpeed extends Command {
    private final IntakeMotor mIntakeMotor;
    private final Loader mLoader;
    private final Shooter mShooter;
    private double iSpeed;
    private double lSpeed;
    private double sSpeed;

    /**
     * @param IntakeMotor
     */
    public IntakeSetSpeed(IntakeMotor intakeMotor, Loader loader, Shooter shooter, double miSpeed, double mlSpeed, double msSpeed) {
        mIntakeMotor = intakeMotor;
        mLoader = loader;
        mShooter = shooter;
        iSpeed = miSpeed;
        lSpeed = mlSpeed;
        sSpeed = msSpeed;
        addRequirements(intakeMotor, loader, shooter);
    }

    public void initialize() {}

    public void execute() {
        mIntakeMotor.setSpeed(iSpeed);
        mLoader.setSpeed(lSpeed);
        mShooter.setSpeed(sSpeed);



        if(iSpeed == 0.0) {
            mIntakeMotor.stopMotor();
        }

        if(lSpeed == 0.0) {
            mIntakeMotor.stopMotor();
        }

        if(sSpeed == 0.0) {
            mIntakeMotor.stopMotor();
        }
    }
    
    @Override
    public void end(boolean interrupted) {
        mIntakeMotor.stopMotor();
    }

    public boolean isFinished() {
        return false;
    }
}
