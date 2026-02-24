package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.hardware.CANcoder;
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
    }

    public void setSpeed(double speed) {
        mIntakeExtensionMotor.set(speed);
    }

    public void stopMotor() {
        mIntakeExtensionMotor.stopMotor();
    }

    public double getAngle() {
        return mEncoder.getAbsolutePosition().getValueAsDouble();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Absolute Encoder Value", getAngle());
    }
}