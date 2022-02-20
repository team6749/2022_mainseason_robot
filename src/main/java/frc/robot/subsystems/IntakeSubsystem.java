package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class IntakeSubsystem extends SubsystemBase {
private final WPI_TalonSRX intake = new WPI_TalonSRX(Constants.intakeMotor);
public IntakeSubsystem() {
 
 
 
}
public void runIntakeForward(){
  intake.set(-0.9);
}
public void runIntakeReverse(){
  intake.set(0.9);
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

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
