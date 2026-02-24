package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Loader extends SubsystemBase {
    private final SparkMax mLoaderMotor = new SparkMax(Constants.LOADER_MOTOR, MotorType.kBrushed);
    private final SparkMaxConfig mLoaderMotorConfig = new SparkMaxConfig();
    
    public Loader() {
        mLoaderMotorConfig.inverted(true);
        try{
            mLoaderMotor.configure(mLoaderMotorConfig,ResetMode.kNoResetSafeParameters,PersistMode.kPersistParameters);
            System.out.println("Successfully configured Coral Intake Motor");
        } catch (Exception e1){
            e1.printStackTrace();
            DriverStation.reportWarning("Failed to configure coral intake motor", true);
        }
    }

    public void setSpeed(double speed) {
    mLoaderMotor.set(speed);
    }

    public void stopMotor() {
        mLoaderMotor.stopMotor();
    }
  
    @Override
    public void periodic() {}
}
