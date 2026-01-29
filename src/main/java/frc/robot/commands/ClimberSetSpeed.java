package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;

public class ClimberSetSpeed extends Command {
    private final Climber mClimberMotor;
    private double mSpeed;

    /**
     * @param Climber
     */

    public ClimberSetSpeed(Climber climberMotor, double speed) {
        mClimberMotor = climberMotor;
        mSpeed = speed;
        addRequirements(climberMotor);
    }

    public void initialize() {}

    public void execute() {
        mClimberMotor.setSpeed(mSpeed);

        if(mSpeed == 0.0) {
            mClimberMotor.stopMotor();
        }
    }

    @Override
    public void end(boolean interrupted) {
        mClimberMotor.stopMotor();
    }

    public boolean isFinished() {
        return false;
    }
}