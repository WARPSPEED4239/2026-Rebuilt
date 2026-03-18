package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeExtension;

public class AutoExtendIntake extends Command {
    private final IntakeExtension mIntakeExtension;
    private double mSpeed;
    private boolean mEnd;

    /**
    @param IntakeExtension
    */

    public AutoExtendIntake(IntakeExtension mIntake, double speed) {
        mIntakeExtension = mIntake;
        mSpeed = speed;
        addRequirements(mIntake);        
    }
    
    @Override
    public void initialize() {
        mEnd = false;
    }

    public void execute() {
        mIntakeExtension.setSpeed(mSpeed);
        if(mSpeed > 0) {
            Timer.delay(0.4);
            mIntakeExtension.stopMotor();
            mEnd = true;
        } else if(mSpeed < 0) {
            Timer.delay(0.5);
            mIntakeExtension.stopMotor();
            mEnd = true;
        }
    }

    public void end() {
        mIntakeExtension.stopMotor();
    }

    public boolean isFinished() {
        if(mEnd) {
            return true;
        }
        return false;
    }
}