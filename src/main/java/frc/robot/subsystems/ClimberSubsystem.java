package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ClimberSubsystem extends SubsystemBase  {
    WPI_TalonSRX climber = new WPI_TalonSRX(Constants.climber); 

public ClimberSubsystem() {
  
}

@Override
public void periodic() {
  // This method will be called once per scheduler run
  //climber1.setVoltage(9);
  //needs to be set at break mode
  climber.feed();
  
}
public void goDown(){
  climber.setVoltage(-9);
}
public void goUp(){
  climber.setVoltage(9);
}

@Override
public void simulationPeriodic() {
  // This method will be called once per scheduler run during simulation
}
}
