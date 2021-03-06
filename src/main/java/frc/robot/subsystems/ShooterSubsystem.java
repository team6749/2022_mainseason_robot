package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

  private final WPI_TalonFX shooterMotor = new WPI_TalonFX(Constants.shooterMotor);
  // SendableChooser<Boolean> shooterChooser = new SendableChooser<Boolean>();

  public ShooterSubsystem() {
    // shooterChooser.setDefaultOption("true", true);
    // shooterChooser.addOption("false", false);
    // SmartDashboard.putData(shooterChooser);

    shooterMotor.setInverted(true);
    shooterMotor.configFactoryDefault();
    shooterMotor.config_kF(0, 0.045d, 0);
    shooterMotor.config_kP(0, 0.025d, 0);
    // shooterMotor.config_kI(0, 0.7d, 0);
    //shooterMotor.configNeutralDeadband(0.001);
    shooterMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    shooterMotor.setNeutralMode(NeutralMode.Coast);
    // upperShooter.set(ControlMode.Velocity, 204.8d * rps);
  }


  public void setShooterSpeed(double rpm){
    // shooterMotor.set(-(speed));
    if(rpm < 0){
      rpm = 0;
    }
    shooterMotor.set(ControlMode.Velocity, 204.8d * rpm);
  }

  public double getShooterSpeed () {
    return shooterMotor.getSelectedSensorVelocity() / 204.8d;
  }

  
  @Override
    public void periodic() {
      // if(shooterChooser.getSelected() == false){  
      //   shooterMotor.set(ControlMode.PercentOutput, 0);
      // }
      // if(shooterChooser.getSelected() == true){
      //  shooterMotor.set(ControlMode.Velocity, 204.8d * 40);
      // }
      
      // This method will be called once per scheduler run
      // shooterMotor.set(-0.2);
      // shooterMotor.set(ControlMode.Velocity, 204.8d * 40);
      setShooterSpeed(40);
      SmartDashboard.putNumber("shooter speed", getShooterSpeed());
    }


    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
  }
