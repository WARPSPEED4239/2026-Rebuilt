package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakePneumatics extends SubsystemBase {
    private final DoubleSolenoid m_DoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
    private final DoubleSolenoid m_DoubleSolenoid2 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
    private final Compressor m_compressor = new Compressor(PneumaticsModuleType.CTREPCM);

    public IntakePneumatics() {
        m_compressor.enableAnalog(40, 60);
    }

    public void extend() {
        m_DoubleSolenoid.set(Value.kForward);
        m_DoubleSolenoid2.set(Value.kForward);
    }

    public void retract() {
        m_DoubleSolenoid.set(Value.kReverse);
        m_DoubleSolenoid2.set(Value.kReverse);
    }

    @Override
    public void periodic() {
        SmartDashboard.getBoolean("Compressor is Enabled", m_compressor.isEnabled());
    }
}
