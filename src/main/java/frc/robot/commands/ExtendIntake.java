package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakePneumatics;

public class ExtendIntake extends Command {
    private final IntakePneumatics mIntakePneumatics;

    /**
    @param IntakePneumatics
    */

    public ExtendIntake(IntakePneumatics mIntake) {
        mIntakePneumatics = mIntake;
        addRequirements(mIntake);
    }
    
    @Override
    public void initialize() {}

    @Override
    public void execute() {
        mIntakePneumatics.extend();
    }
}