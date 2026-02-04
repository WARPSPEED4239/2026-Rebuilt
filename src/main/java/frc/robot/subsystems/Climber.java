package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    public static final TalonFX mClimberMotor = new TalonFX(Constants.CLIMBER_MOTOR);
    private final DigitalInput mBottomLimit = new DigitalInput(Constants.CLIMBER_LIMIT);
    private final DutyCycleOut mDutyCyle = new DutyCycleOut(0.0);

    public Climber() {
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

        mClimberMotor.getConfigurator().apply(talonFxConfigs);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("LiftLimitDown", getBottomLimit());
        SmartDashboard.putNumber("LiftEncoder", getEncoderValue());

        if(getBottomLimit()) {
            setPosition(0.0);
        }
    }

    public void setOutputWithLimitSensors(double speed) {
    mClimberMotor.setControl(mDutyCyle.withOutput(speed).withLimitForwardMotion(getTopLimit()).withLimitReverseMotion(mBottomLimit.get()));
  }

    public void setSpeed(double speed) {
        mClimberMotor.set(speed);
    }

    public double getEncoderValue() {
        return mClimberMotor.getPosition().getValueAsDouble();
    }

    public void setPosition(double encoderValue) {
        final MotionMagicVoltage request = new MotionMagicVoltage(0);
        mClimberMotor.setPosition(encoderValue);
    }

    public boolean getBottomLimit() {
        return mBottomLimit.get();
    }

    public boolean getTopLimit() {
        if(getEncoderValue() > Constants.CLIMBER_TOP_LIMIT) {
        return true;
        } else {
            return false;
        }
    }

    public void stopMotor() {
        mClimberMotor.stopMotor();
    }
}
