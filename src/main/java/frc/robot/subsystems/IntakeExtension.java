package frc.robot.subsystems;

import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeExtension extends SubsystemBase {
    private final SparkMax mIntakeExtensionMotor = new SparkMax(Constants.INTAKE_EXTENSION_MOTOR, MotorType.kBrushed);
    private final SparkMaxConfig mIntakeExtensionMotorConfig = new SparkMaxConfig();
    private final SparkClosedLoopController mController = mIntakeExtensionMotor.getClosedLoopController();
    private final CANcoder mEncoder = new CANcoder(Constants.INTAKE_THROUGHBORE_ENCODER);

    public IntakeExtension() {
        mIntakeExtensionMotorConfig.inverted(false);
        try{
            mIntakeExtensionMotor.configure(mIntakeExtensionMotorConfig,ResetMode.kNoResetSafeParameters,PersistMode.kPersistParameters);
            System.out.println("Successfully configured Intake Extension Motor");
        } catch (Exception e1){
            e1.printStackTrace();
            DriverStation.reportWarning("Failed to configure Intake extension motor", true);
        }

        mIntakeExtensionMotorConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder).p(0.1).i(0.0).d(0.0)
            .outputRange(-1, 1).p(0.0001).i(0.0).d(0.0)
            .outputRange(-1, 1, ClosedLoopSlot.kSlot1).feedForward.kV(12.0 / 5767, ClosedLoopSlot.kSlot1);

        mIntakeExtensionMotor.configure(mIntakeExtensionMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        SmartDashboard.setDefaultNumber("Target Position", 0);
        SmartDashboard.setDefaultNumber("Target Velocity", 0);
        SmartDashboard.setDefaultBoolean("Control Mode", false);
        SmartDashboard.setDefaultBoolean("Reset Encoder", false);
    }

    public void setSpeed(double speed) {
        mIntakeExtensionMotor.set(speed);
    }

    public void stopMotor() {
        mIntakeExtensionMotor.stopMotor();
    }

    public double getPosition() {
        return mEncoder.getAbsolutePosition().getValueAsDouble() * 100;
    }

    public boolean getForwardLimit() {
        if(getPosition() < Constants.EXTENDER_FORWARD_LIMIT) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getBackLimit() {
        if(getPosition() > Constants.EXTENDER_BACK_LIMIT) {
            return true;
        } else {
            return false;
        }
    }

    public void setPosition(double position) {
        mController.setSetpoint(position, ControlType.kPosition, ClosedLoopSlot.kSlot0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Absolute Encoder Value", getPosition());
    }
}