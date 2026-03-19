package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;

public class ClimberSetPos extends Command {
    private final Climber mClimber;
    private double mGoalPos;
    double startingPos;
    private boolean mEnd;

    /**
     * @param Climber
     */

    public ClimberSetPos(Climber climber, double goalPos) {
        mClimber = climber;
        mGoalPos = goalPos;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        startingPos = mClimber.getEncoderValue();
        mEnd = false;
    }

    public void execute() {
        mClimber.setPosition(mGoalPos);
    }

    public void end(boolean interrupted) {
        mClimber.stopMotor();
    }

    public boolean isFinished() {
        if(mEnd) {
            return true;
        }
        return false;
    }
}
