package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class ClimberSubsystem extends SubsystemBase  {
    DigitalInput climberTop = new DigitalInput(Constants.climberLimitSwitch01);
    DigitalInput climberBottom = new DigitalInput(Constants.climberLimitSwitch02);
    public WPI_TalonSRX climber = new WPI_TalonSRX(Constants.climber); 

public ClimberSubsystem() {
  climber.setNeutralMode(NeutralMode.Brake);
}

@Override
public void periodic() {
  // This method will be called once per scheduler run
  //climber1.setVoltage(9);
  //needs to be set at break mode
  climber.feed();
  
  
}

public void goDown(){
  if(climberBottom.get() == false){
  climber.setVoltage(-9);
  }
}
public void goUp(){
  if(climberTop.get() == false){
  climber.setVoltage(9);
  }
}

@Override
public void simulationPeriodic() {
  // This method will be called once per scheduler run during simulation
}
}
