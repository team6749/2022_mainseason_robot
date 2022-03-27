package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
public class ClimberSubsystem extends SubsystemBase  {
  DigitalInput climberTop = new DigitalInput(Constants.climberLimitSwitchTop02);
  DigitalInput climberBottom = new DigitalInput(Constants.climberLimitSwitchBottom01);
  public WPI_TalonFX climber = new WPI_TalonFX(Constants.climber); 
  public boolean shortArmsDown = true;
  Compressor pcmCompressor1 = new Compressor(0, PneumaticsModuleType.CTREPCM);
  DoubleSolenoid shortArms = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.doubleSolenoid[1], Constants.doubleSolenoid[0]);

  PIDController pid = new PIDController(0, 0, 0);
  double climberSetpoint = 0;

public ClimberSubsystem() {
  climber.setInverted(true);
  climber.setNeutralMode(NeutralMode.Brake);
  climber.setInverted(true);
  pcmCompressor1.enableDigital();
  // shortArmsDown = false;
}

@Override
public void periodic() {
  if(shortArms.get() != Value.kOff){
    shortArms.set(Value.kOff);
}
  // System.out.println(pcmCompressor1.getPressure());
  // This method will be called once per scheduler run
  //climber1.setVoltage(9);
  //needs to be set at break mode
  // System.out.println(climberBottom.get());
  // System.out.println(climberTop.get());

  System.out.println(getClimberSensor());

  //Reset the climber sensor position to zero IF we are at the bottom.
  if(climberBottom.get()){
    climber.setSelectedSensorPosition(0);
  }

  //Calculate and apply motor movement if it is safe.
  climber.set(ControlMode.PercentOutput, 0);

  if(climberSetpoint == 0) {
    //Run the climber all of the way down to the limit switch

    //   if(climberBottom.get() == false){
    //     climber.set(ControlMode.PercentOutput, -0.3);
    //   }
  } else {
    //Otherwise control using PID

    // double pidResult = pid.calculate(getClimberSensor(), climberSetpoint);
    // if(pidResult >= 0) {
    //   //Go up check
    //   if(climberTop.get() == false){
    //     climber.set(ControlMode.PercentOutput, pidResult);
    //   }
    // } else {
    //   //Go down check
    //   if(climberBottom.get() == false){
    //     climber.set(ControlMode.PercentOutput, pidResult);
    //   }
    // }
  }
  
}

public boolean getBool() {
  return shortArmsDown;
}

// public void armOff(){
//   shortArms.set(Value.kOff);
// }
public void armMoveBackward(){
  shortArms.set(Value.kReverse);
  shortArmsDown = true;
}
public void armMoveForward(){
  shortArms.set(Value.kForward);
  shortArmsDown = false;
}


public void setClimberPosition(double newPosition) {
  climberSetpoint = newPosition;
}

public boolean isClimberAtPosition() {
  return Math.abs(getClimberSensor() - climberSetpoint) < 0.01;
}

//Position in meters.
double getClimberSensor(){
  return climber.getSelectedSensorPosition() * 0.029 * Math.PI / 2048;
}

public void goDown(){
  if(climberBottom.get() == false){
    climber.set(ControlMode.PercentOutput, -0.3);
  }
}
public void goUp(){
  if(climberTop.get() == false){
    climber.set(ControlMode.PercentOutput, 0.3);
  }
}

public boolean getTopSwitch(){
  return climberTop.get();
}
public boolean getBottomSwitch(){
  return climberBottom.get();
}


@Override
public void simulationPeriodic() {
  // This method will be called once per scheduler run during simulation
}
}

