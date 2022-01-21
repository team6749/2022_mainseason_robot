package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;




public class DrivebaseSubsystem extends SubsystemBase{

    private final WPI_TalonFX frontLeft = new WPI_TalonFX(Constants.flMotor);
    private final WPI_TalonFX frontRight = new WPI_TalonFX(Constants.frMotor);
    private final WPI_TalonFX backLeft = new WPI_TalonFX(Constants.blMotor);
    private final WPI_TalonFX backRight = new WPI_TalonFX(Constants.brMotor);
    
    MotorControllerGroup left = new MotorControllerGroup(frontLeft, backLeft);
    MotorControllerGroup right = new MotorControllerGroup(frontRight, backRight);
    
    DifferentialDrive myDrive = new DifferentialDrive(left, right);
    
    

    public DrivebaseSubsystem() {
      
    }

    public void arcadeDrive(double speed, double rotation){
      
      myDrive.arcadeDrive(speed, rotation);
    }
  
    @Override
  public void periodic() {
    
    
    }
    // This method will be called once per scheduler run
  

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }


}
