package frc.robot.subsystems;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
     private final TalonFX mShooterMotor = new TalonFX(Constants.SHOOTER_MOTOR);
    private final DutyCycleOut mDutyCyle = new DutyCycleOut(0.0);
}
