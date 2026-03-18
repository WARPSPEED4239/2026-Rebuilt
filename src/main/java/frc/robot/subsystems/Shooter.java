package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    private final TalonFX mShooterMotor = new TalonFX(Constants.SHOOTER_MOTOR);
    //private final VelocityVoltage velocity = new VelocityVoltage(0);
    
    public Shooter() {
        var talonFxConfigs = new TalonFXConfiguration();
        var slot0Configs = talonFxConfigs.Slot0;
        var motionMagicConfigs = talonFxConfigs.MotionMagic;
        slot0Configs.kG = 0.0;
        slot0Configs.kS = 0.07;
        slot0Configs.kV = 0.06;
        slot0Configs.kA = 0.03;
        slot0Configs.kP = 0.04;
        slot0Configs.kI = 0.01;
        slot0Configs.kD = 0.04;
        talonFxConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        talonFxConfigs.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        slot0Configs.StaticFeedforwardSign = StaticFeedforwardSignValue.UseClosedLoopSign;
        motionMagicConfigs.MotionMagicCruiseVelocity = 40.0; //40
        motionMagicConfigs.MotionMagicAcceleration = 50.0; //50
        motionMagicConfigs.MotionMagicJerk = 0; //1600

       /*  var configs = new VoltageConfigs();
        var slot0 = configs.withPeakForwardVoltage(12)
            .withPeakReverseVoltage(12); */

        mShooterMotor.getConfigurator().apply(talonFxConfigs);
    }

    @Override
    public void periodic() {
    }

    public void setSpeed(double speed) {
        mShooterMotor.set(speed);
    }

    public void setVelocity(double rpm) {
        double rps = rpm / 60.0;
        mShooterMotor.setControl(new VelocityVoltage(0)
            .withVelocity(rps)
            .withEnableFOC(true));
    }

    public void stopMotor() {
        mShooterMotor.stopMotor();
    }
}
