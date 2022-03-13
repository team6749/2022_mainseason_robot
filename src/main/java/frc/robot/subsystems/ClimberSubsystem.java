package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DigitalInput;
public class ClimberSubsystem extends SubsystemBase  {
  DigitalInput climberTop = new DigitalInput(Constants.climberLimitSwitchTop02);
  DigitalInput climberBottom = new DigitalInput(Constants.climberLimitSwitchBottom01);
  public WPI_TalonSRX climber = new WPI_TalonSRX(Constants.climber); 

public ClimberSubsystem() {
  climber.setNeutralMode(NeutralMode.Brake);
  climber.setInverted(true);
}

@Override
public void periodic() {
  // This method will be called once per scheduler run
  //climber1.setVoltage(9);
  //needs to be set at break mode
  climber.set(0);
  //System.out.println(climberBottom.get());
  //System.out.println(climberTop.get());
  
}

public void goDown(){
  if(climberBottom.get() == false){
    climber.set(-0.8);
  }
}
public void goUp(){
  if(climberTop.get() == false){
    climber.set(0.8);
  }
}
@Override
public void simulationPeriodic() {
  // This method will be called once per scheduler run during simulation
}
}

