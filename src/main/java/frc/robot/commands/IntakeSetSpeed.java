package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeMotor;

public class IntakeSetSpeed extends Command {
    private final IntakeMotor mIntakeMotor;
    private double mSpeed;

    /**
     * @param IntakeMotor
     */
    public IntakeSetSpeed(IntakeMotor intakeMotor, double speed) {
        mIntakeMotor = intakeMotor;
        mSpeed = speed;
        addRequirements(intakeMotor);
    }

    public void initialize() {}

    public void execute() {
        mIntakeMotor.setSpeed(mSpeed);

        if(mSpeed == 0.0) {
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
