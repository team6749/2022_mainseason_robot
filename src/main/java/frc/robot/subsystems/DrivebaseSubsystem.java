package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class DrivebaseSubsystem extends SubsystemBase{

    TalonFX frontLeft = new TalonFX(Constants.flMotor);
    TalonFX frontRight = new TalonFX(Constants.frMotor);
    TalonFX backLeft = new TalonFX(Constants.blMotor);
    TalonFX backRight = new TalonFX(Constants.brMotor);
    
    public DrivebaseSubsystem() {
        
    }



}
