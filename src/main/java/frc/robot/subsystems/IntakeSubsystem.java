package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class IntakeSubsystem extends SubsystemBase {
  WPI_TalonSRX intake = new WPI_TalonSRX(Constants.intakeMotor);
  WPI_TalonSRX belt = new WPI_TalonSRX(Constants.beltMotor);
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

   double beltSpeed = RobotContainer.controller.getLeftBumper() ? 0.7 : 0;
   belt.set(beltSpeed);
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}