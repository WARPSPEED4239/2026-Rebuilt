package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeMotor extends SubsystemBase {
    private final SparkMax mIntakeMotor = new SparkMax(Constants.INTAKE_MOTOR, MotorType.kBrushed);
    private final SparkMaxConfig mIntakeMotorConfig = new SparkMaxConfig();

    public IntakeMotor() {
        mIntakeMotorConfig.inverted(false);
        try{
            mIntakeMotor.configure(mIntakeMotorConfig,ResetMode.kNoResetSafeParameters,PersistMode.kPersistParameters);
            System.out.println("Successfully configured Intake Motor");
        } catch (Exception e1){
            e1.printStackTrace();
            DriverStation.reportWarning("Failed to configure Intake motor", true);
        }   
    }

    @Override
    public void periodic() {

    }

    public void setSpeed(double speed) {
        mIntakeMotor.set(speed);
    }

    public void stopMotor() {
        mIntakeMotor.stopMotor();
    }
}
