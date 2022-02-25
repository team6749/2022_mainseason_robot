package frc.robot.subsystems;



import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import frc.robot.RobotContainer;
import frc.robot.commands.DriveWithController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class DrivebaseSubsystem extends SubsystemBase{

    SmartDashboard dashboard;
    private final WPI_TalonFX frontLeft = new WPI_TalonFX(Constants.flMotor);
    private final WPI_TalonFX frontRight = new WPI_TalonFX(Constants.frMotor);
    private final WPI_TalonFX backLeft = new WPI_TalonFX(Constants.blMotor);
    private final WPI_TalonFX backRight = new WPI_TalonFX(Constants.brMotor);
    
    MotorControllerGroup left = new MotorControllerGroup(frontLeft, backLeft);
    MotorControllerGroup right = new MotorControllerGroup(frontRight, backRight);
    
    public static final ADIS16470_IMU gyro = new ADIS16470_IMU();

    Encoder leftEncoder = new Encoder(Constants.encoderLeft[0], Constants.encoderLeft[1]);
    Encoder rightEncoder = new Encoder(Constants.encoderRight[0], Constants.encoderRight[1]);

    
    public DifferentialDriveKinematics driveKinematics;
    DifferentialDriveOdometry odometry;
    DifferentialDrive myDrive = new DifferentialDrive(left, right);

    public DrivebaseSubsystem() {
      

      leftEncoder.setDistancePerPulse(0.1524 * Math.PI / 360);
      rightEncoder.setDistancePerPulse(0.1524 * Math.PI / 360);
      rightEncoder.setReverseDirection(false);
      leftEncoder.setReverseDirection(false);

      gyro.calibrate();

      driveKinematics = new DifferentialDriveKinematics(0.5842);
      odometry = new DifferentialDriveOdometry(new Rotation2d());




      left.setInverted(true);

      myDrive.setMaxOutput(0.25);
    }
    
    public void arcadeDrive(double speed, double rotation){
      double nspeed = Math.pow(speed, 2);
      double nrotation = Math.pow(rotation, 2);
      if(speed < 0){
        nspeed = -(nspeed);
      }
      if(rotation < 0){
        nrotation = -(nrotation);
      }
      myDrive.arcadeDrive(nspeed, nrotation);
    }
  
    @Override
    public void periodic() {

      // System.out.println(getPose());

      double lDistance = leftEncoder.getDistance();
      double rDistance = rightEncoder.getDistance();
      SmartDashboard.putNumber("Encoder Left value", lDistance);  
      SmartDashboard .putNumber("Encoder Right value", rDistance);
         
      odometry.update(Rotation2d.fromDegrees(gyro.getAngle()), leftEncoder.getDistance(), rightEncoder.getDistance());

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
    myDrive.tankDrive(-left, right);
  }

  public double getLeftEncoder(){
   return leftEncoder.getDistance();
  }

  public double getRightEncoder(){
    return rightEncoder.getDistance();
  }
  

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
