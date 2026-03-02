package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class UnderShooterMotor extends SubsystemBase {
    private final TalonFX m_underShooterMotor = new TalonFX(Constants.UNDER_SHOOTER_MOTOR);

    public UnderShooterMotor() {
        var talonFxConfigs = new TalonFXConfiguration();
        var slot0Configs = talonFxConfigs.Slot0;
        var motionMagicConfigs = talonFxConfigs.MotionMagic;
        slot0Configs.kG = 0.0;
        slot0Configs.kS = 0.25;
        slot0Configs.kV = 0.12;
        slot0Configs.kA = 0.01;
        slot0Configs.kP = 2.0;
        slot0Configs.kI = 0.0;
        slot0Configs.kD = 0.05;
        talonFxConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        talonFxConfigs.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        slot0Configs.StaticFeedforwardSign = StaticFeedforwardSignValue.UseClosedLoopSign;
        motionMagicConfigs.MotionMagicCruiseVelocity = 40.0; //40
        motionMagicConfigs.MotionMagicAcceleration = 50.0; //50
        motionMagicConfigs.MotionMagicJerk = 0; //1600

        m_underShooterMotor.getConfigurator().apply(talonFxConfigs);
    }

    @Override
    public void periodic() {}

    public void setSpeed(double speed) {
        m_underShooterMotor.set(speed);
    }

    public void stopMotor() {
        m_underShooterMotor.stopMotor();
    }
}
