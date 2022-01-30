package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;


public class IntakeSubsystem extends SubsystemBase {
  WPI_TalonFX intake = new WPI_TalonFX(Constants.intakeMotor);

public IntakeSubsystem() {
 
 
 
}

@Override
  public void periodic() {
    double speed = 0;
    if(RobotContainer.controller.getAButtonPressed()) {
      speed = 1;
    } else if (RobotContainer.controller.getBButtonPressed()) {
      speed = -1;
    }
   
    intake.set(speed);
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}