package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeExtention extends SubsystemBase {
    Compressor phCompressor = new Compressor(1, PneumaticsModuleType.REVPH);
    DoubleSolenoid intakeDoublePH = new DoubleSolenoid(9, PneumaticsModuleType.REVPH, 4, 5);
    
    public void Extend() {
        intakeDoublePH.set(kForward);
    }

    public void Retract() {
        intakeDoublePH.set(kReverse);
    }

    @Override
    public void periodic() {
        }
}