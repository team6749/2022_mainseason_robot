package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;


public class IntakeSubsystem extends SubsystemBase {
private final WPI_TalonFX intake = new WPI_TalonFX(Constants.intakeMotor);
public IntakeSubsystem() {
 
 
}

@Override
  public void periodic() {
    double speed = 0;
    if(RobotContainer.controller.getAButton()) {
      speed = -0.7;
    } else if (RobotContainer.controller.getBButton()) {
      speed = 0.7;
    }
    intake.set(speed);

    // This method will be called once per scheduler run
  }

  //methods used in commands
  public void stop(){
    intake.set(0);
  }
  public void start(){
    intake.set(0.7);
  }
  public void reverse(){
    intake.set(-0.7);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
