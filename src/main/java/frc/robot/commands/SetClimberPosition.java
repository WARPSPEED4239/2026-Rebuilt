package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;

public class SetClimberPosition extends Command {
    private final Climber mClimberMotor;
    private double mSpeed;
    //private double encoderValue;
    private double mGoalPosition;
    double mStartingPosition;
    private boolean mEnd;
    

    /**
     * @param Climber
     */

    public SetClimberPosition(Climber climberMotor, double goalPosition) {
        mClimberMotor = climberMotor;
        mGoalPosition = goalPosition;
        addRequirements(climberMotor);
    }

    public void initialize() {
        mStartingPosition = mClimberMotor.getEncoderValue();
        mEnd = false;
    }

    public void execute() {
        mClimberMotor.setSpeed(mSpeed);

        if (mClimberMotor.getBottomLimit() && mStartingPosition > mClimberMotor.getEncoderValue()) {
      mClimberMotor.setPosition(0.0);
    }

    mClimberMotor.setPosition(mGoalPosition);
    }

    @Override
    public void end(boolean interrupted) {
        mClimberMotor.stopMotor();
    }

    public boolean isFinished() {
        return mEnd;
    }
}
