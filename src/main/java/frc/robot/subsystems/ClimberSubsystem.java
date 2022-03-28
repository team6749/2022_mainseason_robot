package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ClimberSubsystem extends SubsystemBase  {
  DigitalInput climberTop = new DigitalInput(Constants.climberLimitSwitchTop02);
  DigitalInput climberBottom = new DigitalInput(Constants.climberLimitSwitchBottom01);
  public WPI_TalonSRX climber = new WPI_TalonSRX(Constants.climber); 
  public boolean shortArmsDown = false;
  Compressor pcmCompressor1 = new Compressor(0, PneumaticsModuleType.CTREPCM);
  DoubleSolenoid shortArms = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.doubleSolenoid[1], Constants.doubleSolenoid[0]);


public ClimberSubsystem() {
  climber.setInverted(true);
  climber.setNeutralMode(NeutralMode.Brake);
  climber.setInverted(true);
  pcmCompressor1.enableDigital();
  armMoveBackward();
  shortArmsDown = false;
}

@Override
public void periodic() {
  if(shortArms.get() != Value.kOff){
    shortArms.set(Value.kOff);
  }
  climber.set(0);
  SmartDashboard.putBoolean("Top Switch", climberTop.get());
  SmartDashboard.putBoolean("Bottom Switch", climberBottom.get());
}

public boolean getBool() {
  return shortArmsDown;
}

// public void armOff(){
//   shortArms.set(Value.kOff);
// }
public void armMoveBackward(){
  shortArms.set(Value.kReverse);
  shortArmsDown = false;
}
public void armMoveForward(){
  shortArms.set(Value.kForward);
  shortArmsDown = true;
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

