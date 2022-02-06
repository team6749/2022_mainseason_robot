package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilib.Timer;

public class ShooterSubsystem extends SubsystemBase {

  private final WPI_TalonFX shooterMotor = new WPI_TalonFX(Constants.shooterMotor);
  private final WPI_TalonFX belt = new WPI_TalonFX(Constants.beltMotor);
  private final WPI_TalonFX intake = new WPI_TalonFX(Constants.intakeMotor);
  public ShooterSubsystem() {}


  public void shootAllBalls(){
    private double time = 5.0;
    Timer myTimer = new Timer();
    while(myTimer.get() < time){
      belt.set(0.7);
      shooterMotor.set(0.7);
      //intake.set(0.6);
    }
  }
  

  @Override
    public void periodic() {
      // This method will be called once per scheduler run
      double speed = -(RobotContainer.controller.getRightBumper() ? 0.69 : 0.2);
      double beltSpeed = RobotContainer.controller.getRightBumper() ? 0.7 : 0;
      belt.set(beltSpeed);
      shooterMotor.set(speed);
      
      if(RobotContainer.controller.getTrigger(hand.kRight())){
         shootAllBalls();
      }
    }

    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
}
