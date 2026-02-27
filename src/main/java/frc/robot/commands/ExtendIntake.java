package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeExtension;

public class ExtendIntake extends Command {
    private final IntakeExtension mIntakeExtension;
    private double mGoalPos;

    /**
    @param IntakeExtension
    */

    public ExtendIntake(IntakeExtension mIntake, double targetPosition) {
        mIntakeExtension = mIntake;
        mGoalPos = targetPosition;
        addRequirements(mIntake);

        
    }
    
    @Override
    public void initialize() {}

    public void execute() {
        mIntakeExtension.setPosition(mGoalPos);
    }
}