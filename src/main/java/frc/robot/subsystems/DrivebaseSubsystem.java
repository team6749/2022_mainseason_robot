package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class DrivebaseSubsystem extends SubsystemBase{

    TalonFX frontLeft = new TalonFX(Constants.frontLeftMotor);
    
    public DrivebaseSubsystem() {
        
    }



}
