package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

  private final WPI_TalonFX shooterMotor = new WPI_TalonFX(Constants.shooterMotor);

  public ShooterSubsystem() {}


  @Override
    public void periodic() {
      // This method will be called once per scheduler run
      // boolean shooterOn = false;
      double speed = RobotContainer.controller.getRightBumper() ? 1 : 0;
      System.out.println(RobotContainer.controller.getRightBumper()); 
      shooterMotor.set(speed);      
    }

    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
}
