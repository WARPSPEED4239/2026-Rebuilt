package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeMotor;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.UnderShooterMotor;

public class StopIntake extends Command {
    private final IntakeMotor mIntakeMotor;
    private final Loader mLoader;
    private final UnderShooterMotor mUnderShooterMotor;

    /**
     * @param intakeMotor
     * @param loader
     * @param underShooterMotor
     * @param iSpeed
     * @param lSpeed
     * @param uSpeed
     */

    public StopIntake(IntakeMotor intakeMotor, Loader loader, UnderShooterMotor underShooterMotor) {
        mIntakeMotor = intakeMotor;
        mLoader = loader;
        mUnderShooterMotor = underShooterMotor;

        addRequirements(intakeMotor, loader, underShooterMotor);
    }

    public void initialize() {}

    public void execute() {
        mIntakeMotor.stopMotor();
        mLoader.stopMotor();
        mUnderShooterMotor.stopMotor();
    }

    public void end(boolean interrupted) {
        mIntakeMotor.stopMotor();
        mLoader.stopMotor();
        mUnderShooterMotor.stopMotor();
    }

    public boolean isFinished() {
        return false;
    }
}
