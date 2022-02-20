package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.ShootAllBalls;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class ShooterSubsystem extends SubsystemBase {

  private final WPI_TalonFX shooterMotor = new WPI_TalonFX(Constants.shooterMotor);
  private final WPI_TalonFX belt = new WPI_TalonFX(Constants.beltMotor);
  DigitalInput beltSwitch = new DigitalInput(Constants.beltLimitSwitch);
  
  public boolean ballInBelt(){
  return beltSwitch.get();
  }

  public ShooterSubsystem() {}


  // public void shootAllBalls(){
  //   Timer myTimer = new Timer();
  //   myTimer.reset();
  //   myTimer.hasElapsed(2.5);
  // }

  
  public void setShooterSpeed(double speed){
    if(speed > 0.7){
      //backward
      shooterMotor.set(0.7);
    } else if(speed < -0.7){
      //forward
      shooterMotor.set(-0.7);
    } else {
      shooterMotor.set(speed);
    }
  }

   public void runBeltForward(){
    belt.set(0.7);
   } 

  public void runBeltReverse(){
    belt.set(-0.7);
  }

  @Override
    public void periodic() {

      // This method will be called once per scheduler run
      shooterMotor.set(-0.2);
      belt.set(0.0);
      System.out.println(ballInBelt());
    }

    public void runBelt(){
      belt.set(0.4);
    }
    public void dontRun(){
      belt.set(0);
    }

    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
}
