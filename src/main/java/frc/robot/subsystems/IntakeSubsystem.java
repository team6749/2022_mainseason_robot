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
    double speedPos = RobotContainer.controller.getAButtonPressed() ? 1 : 0;
    double speedNeg = RobotContainer.controller.getBButtonPressed() ? -1 : 0;
   
    if(speedPos > 0 && speedNeg == 0){
      intake.set(speedPos);
    } else if (speedNeg < 0 && speedPos == 0) {
      intake.set(speedNeg);
    } 
    else {
      intake.set(0);
    }
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}