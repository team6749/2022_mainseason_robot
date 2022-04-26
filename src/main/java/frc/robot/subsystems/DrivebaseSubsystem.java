package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class DrivebaseSubsystem extends SubsystemBase{

    SmartDashboard dashboard;
    private final WPI_TalonFX frontLeft = new WPI_TalonFX(Constants.flMotor);
    private final WPI_TalonFX frontRight = new WPI_TalonFX(Constants.frMotor);
    private final WPI_TalonFX backLeft = new WPI_TalonFX(Constants.blMotor);
    private final WPI_TalonFX backRight = new WPI_TalonFX(Constants.brMotor);
    
    private final AnalogInput input = new AnalogInput(Constants.ultrasonic);

    MotorControllerGroup left = new MotorControllerGroup(frontLeft, backLeft);
    MotorControllerGroup right = new MotorControllerGroup(frontRight, backRight);
    
    public static final ADIS16470_IMU gyro = new ADIS16470_IMU();

    
    public DifferentialDriveKinematics driveKinematics;
    DifferentialDriveOdometry odometry;
    DifferentialDrive myDrive = new DifferentialDrive(left, right);

    public DrivebaseSubsystem() {
      frontLeft.configFactoryDefault();
      backRight.configFactoryDefault();
      input.setAverageBits(2);
      frontLeft.getSensorCollection().setIntegratedSensorPosition(0, 20);
      backRight.getSensorCollection().setIntegratedSensorPosition(0, 20);



      // frontLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
      // frontRight.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

      gyro.calibrate();

      driveKinematics = new DifferentialDriveKinematics(0.5842);
      odometry = new DifferentialDriveOdometry(new Rotation2d());

    


      left.setInverted(true);
      myDrive.setMaxOutput(0.5);
      //  myDrive.setDeadband(0.05);
      setBreakMode(NeutralMode.Brake);
    }

    public void setBreakMode(NeutralMode mode){
      frontRight.setNeutralMode(mode);
      frontRight.setNeutralMode(mode);
      frontLeft.setNeutralMode(mode);
      backRight.setNeutralMode(mode);
    }
    public void arcadeDrive(double speed, double rotation){
      myDrive.arcadeDrive(-speed, -rotation * 0.65);
    }
    
    public double atShootPosNum(){
      double currentDistanceMeters = ((input.getAverageValue() / 200d) - 0.6) /2;
      SmartDashboard.putNumber("ultrasonic in m", currentDistanceMeters);
      // -0.3 to 0.3 is the feesible range
      return currentDistanceMeters - 0.9;
    }

    public boolean atShootPosBool(){
      double currentDistanceMeters = ((input.getAverageValue() / 200d) - 0.6) /2;
      double currentDistanceCM = currentDistanceMeters * 100;
      //0.3m to 5m range
      if(currentDistanceCM <= 120 && currentDistanceCM >= 60){
        return true;
      }
      return false;
    }


    @Override
    public void periodic() {
      //distance from hoop is good or not
      SmartDashboard.putNumber("Ultrasonic distance from hoop (try to be within ±0.3", atShootPosNum());
      SmartDashboard.putBoolean("Close enough to hoop?", atShootPosBool());
      double lDistance = getLeftEncoder();
      double rDistance = getRightEncoder();
      SmartDashboard.putNumber("Encoder Left value", Math.floor(lDistance * 100) / 100);  
      SmartDashboard .putNumber("Encoder Right value", Math.floor(rDistance * 100) / 100);
      
      odometry.update(Rotation2d.fromDegrees(gyro.getAngle()), getLeftEncoder(), getRightEncoder());

    }
    // This method will be called once per scheduler run

    public Pose2d getPose() {
      if (odometry != null && odometry.getPoseMeters() != null) {
        return odometry.getPoseMeters();
      } else {
        return new Pose2d(0.0, 0.0, new Rotation2d(0));
      }
    }

  public void driveRobotRaw(double left, double right){
    myDrive.tankDrive(-left, -right);
  }

  public double getLeftEncoder(){
    return frontLeft.getSelectedSensorPosition() * 0.1524 * Math.PI / 2048 / 10.75;
  }

  public double getRightEncoder(){
    return -backRight.getSelectedSensorPosition() * 0.1524 * Math.PI / 2048 / 10.75;
  }
  

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
