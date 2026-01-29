package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakePneumatics;

public class RetractIntake extends Command {
    private final IntakePneumatics mIntakePneumatics;

    /**
    @param IntakePneumatics
    */

    public RetractIntake(IntakePneumatics mIntake) {
        mIntakePneumatics = mIntake;
        addRequirements(mIntake);
    }
    
    @Override
    public void initialize() {}

    @Override
    public void execute() {
        mIntakePneumatics.retract();
    }
}