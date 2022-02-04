package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

  private final WPI_TalonFX shooterMotor = new WPI_TalonFX(Constants.shooterMotor);
  private final WPI_TalonFX belt = new WPI_TalonFX(Constants.beltMotor);
  public ShooterSubsystem() {}


  @Override
    public void periodic() {
      // This method will be called once per scheduler run
      double speed = -(RobotContainer.controller.getRightBumper() ? 0.69 : 0.2);
      double beltSpeed = RobotContainer.controller.getRightBumper() ? 0.7 : 0;
      belt.set(beltSpeed);
      shooterMotor.set(speed);      
    }

    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
}
