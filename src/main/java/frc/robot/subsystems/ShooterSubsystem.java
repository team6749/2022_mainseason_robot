package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.ShootAllBalls;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class ShooterSubsystem extends SubsystemBase {

  private final WPI_TalonFX shooterMotor = new WPI_TalonFX(Constants.shooterMotor);
  DigitalInput beltSwitch = new DigitalInput(Constants.beltLimitSwitch);
  
  private final WPI_TalonFX belt = new WPI_TalonFX(Constants.beltMotor);

  public ShooterSubsystem() {
    shooterMotor.setInverted(true);
    shooterMotor.configFactoryDefault();
    shooterMotor.config_kF(0, 0.05d, 0);
    shooterMotor.config_kP(0, 0.175d, 0);
    // shooterMotor.config_kI(0, 0.7d, 0);
    //shooterMotor.configNeutralDeadband(0.001);
    shooterMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    shooterMotor.setNeutralMode(NeutralMode.Coast);
    // upperShooter.set(ControlMode.Velocity, 204.8d * rps);
  }

  public boolean ballInBelt(){
    return !beltSwitch.get();
  }
  public void setShooterSpeed(double rpm){
    // shooterMotor.set(-(speed));
    shooterMotor.set(ControlMode.Velocity, 204.8d * rpm);
  }

   public void runBeltForward(){
    belt.set(0.9);
   } 
   public void runBelt(){
     belt.set(0.4);
   }

  public void runBeltReverse(){
    belt.set(-0.9);
  }

  @Override
    public void periodic() {

      // This method will be called once per scheduler run
      // shooterMotor.set(-0.2);
      shooterMotor.set(ControlMode.Velocity, 204.8d * 65);
      belt.set(0.0);
      //System.out.println(beltSwitch.get());
    }

    public void dontRun(){
      belt.set(0);
    }

    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
}
