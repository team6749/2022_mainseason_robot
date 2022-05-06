package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.enums.IncomingBalls;
import frc.robot.Constants;
// import frc.robot.commands.AutoIntakeBalls;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj.DriverStation.Alliance;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;



// sensor positive for intake motor is when it is intaking balls
// motor speed is logged in rps, range is -15 to +15

public class IntakeSubsystem extends SubsystemBase {
  private final WPI_TalonSRX intake = new WPI_TalonSRX(Constants.intakeMotor);
  private final WPI_TalonSRX belt = new WPI_TalonSRX(Constants.beltMotor);
  private final ColorSensorV3 colorSensor = new ColorSensorV3(Constants.colorSensorPort);
  static DigitalInput beltSwitch = new DigitalInput(Constants.beltLimitSwitch);

  private final Encoder intakeEncoder = new Encoder(Constants.intakeEncoder[0] , Constants.intakeEncoder[1]);
  private final PIDController intakePID = new PIDController(0.1, 0, 0);
  private static int ballQuantity;
  private final ColorMatch _colorMatcher = new ColorMatch();
  Timer timer = new Timer();
  Timer stallTimer = new Timer();
  IncomingBalls lastBallColor = IncomingBalls.NONE;
  IncomingBalls topBeltBallColor = IncomingBalls.NONE;

  private final Color redColor = new Color(0.48, 0.35, 0.13);
  private final Color blueColor = new Color(0.15, 0.40, 0.43);

  NetworkTableEntry xEntry;
  NetworkTableEntry yEntry;

  NetworkTableInstance inst = NetworkTableInstance.getDefault();

  NetworkTable table = inst.getTable("datatable");

  public static DriverStation station;
  private IncomingBalls _ball;
  public boolean intakeEnabled = false;
  private boolean isShooting = false;
  SendableChooser<Boolean> _chooser2 = new SendableChooser<Boolean>();

  private double intakeSpeed = 0;
  public IntakeSubsystem() {
    belt.setNeutralMode(NeutralMode.Brake);
    belt.setInverted(false);
    timer.start();
    intake.setInverted(true);
    isShooting = false;
    // color stuff
    _colorMatcher.addColorMatch(redColor);
    _colorMatcher.addColorMatch(blueColor);
    intake.setNeutralMode(NeutralMode.Brake);
    intakeEnabled = true;
    _chooser2.setDefaultOption("true", true);
    _chooser2.addOption("false", false);
    SmartDashboard.putData(_chooser2);
  }
  //intake methods
  public void runIntakeForward() {
    intakeSpeed = 7.5;
  }

  public void runIntakeReverse() {
    intakeSpeed = -9.5;
  }
  
  public void intakeOff() {
    intakeSpeed = 0;
  }

  public double getIntakeSpeed(){
    return intakeEncoder.getRate() / 2048;
  }

  public boolean ballRejOn() {
    return _chooser2.getSelected();
  }
  
  //belt methods
  public boolean ballInBelt(){
    return beltSwitch.get();
  }
  public boolean getIsShooting(){
    return isShooting;
  }
  public void setIsShooting(boolean x){
    isShooting = x;
  }
  public void runBeltForward(){
    belt.set(0.4);
   }

   
  public void runBeltForwardShooting(){
    belt.set(0.9);
   }

  public void runBeltReverse(){
    belt.set(-0.4);
  }

  public void beltOff(){
    belt.set(0);
  }

  public int getBallsInBot() {
    return ballQuantity;
  }

  @Override
  public void periodic() {
    double power = intakePID.calculate(getIntakeSpeed(), intakeSpeed) + (intakeSpeed * 0.03);
    intake.set(power);
    // intake.set(intakeSpeed * 0.03);
    SmartDashboard.putNumber("intake power", power);
    SmartDashboard.putNumber("intake speed", getIntakeSpeed());
    SmartDashboard.putNumber("intake setpoint", intakeSpeed);

    intakeOff();
    beltOff();
    if(intakeEnabled){
      _ball = ballColorToEnum();

      if(ballInBelt()) {
        if(_ball == IncomingBalls.NONE) {
          ballQuantity = 1;
        } else {
          ballQuantity = 2;
        }
      } else {
        ballQuantity = 0;
      }

      if(!ballInBelt() && _ball != IncomingBalls.NONE) {
        topBeltBallColor = _ball; //sets last ball to the ball current ball
      }

      // Run the bottom belt unless there are 2 balls in the robot
      if ((ballInBelt() && _ball != IncomingBalls.NONE) == false) {
        runIntakeForward();
      } else {
        beltOff();
      }
      if (ballInBelt() == false) {
        runBeltForward();
      } else {
        beltOff();
      }
      
      boolean intakeStalling = false;
      if(getIntakeSpeed() == 0 && Math.abs(power) >= 0.1){
        stallTimer.start();
        if(stallTimer.hasElapsed(1)){
          intakeStalling = true;
        }
      } else {
        stallTimer.reset();
      }
      SmartDashboard.putBoolean("intake stalling?", intakeStalling);
      // SmartDashboard.putString("TeamColor", DriverStation.getAlliance().toString());
      SmartDashboard.putBoolean("Ball In Belt", ballInBelt());
      if(ballRejOn()){
        if(ballNotMatchTeam(_ball)){ //if the ball color is not the team color
          //if there is a ball at top of robot and its the right color;  then run intake reverse
          if(ballInBelt() && (lastBallCheck(topBeltBallColor))){
            runIntakeReverse(); //runs intake reverse slowly for delay
          }
          if(ballInBelt() && (ballNotMatchTeam(topBeltBallColor))) {
            runBeltForward();
          }
        }
      }
    }
  }

  public boolean ballNotMatchTeam(IncomingBalls ballColor) {
    if ((DriverStation.getAlliance() == Alliance.Red) && (ballColor == IncomingBalls.BLUE)) {
      return true;
    }
    if ((DriverStation.getAlliance() == Alliance.Blue) && (ballColor == IncomingBalls.RED)) {
      return true;
    }
    return false;
  }

  public boolean lastBallCheck(IncomingBalls ballColor) {
    if ((DriverStation.getAlliance() == Alliance.Blue) && (ballColor == IncomingBalls.BLUE)) {
      return true;
    }
    if ((DriverStation.getAlliance() == Alliance.Red) && (ballColor == IncomingBalls.RED)) {
      return true;
    }
    return false;
  }


  // color sensor to enum function
  public IncomingBalls ballColorToEnum() {
    Color detectedColor = colorSensor.getColor();


    _colorMatcher.matchColor(detectedColor);

    if (lastBallColor == IncomingBalls.RED) {
      SmartDashboard.putString("lastBallColor", "red");
    } else if (lastBallColor == IncomingBalls.BLUE) {
      SmartDashboard.putString("lastBallColor", "blue");
    } else {
      SmartDashboard.putString("lastBallColor", "none");
    }

    ColorMatchResult foundColor = _colorMatcher.matchColor(detectedColor);
    if (foundColor != null) {

      if (foundColor.color == redColor) {
        timer.reset();
        lastBallColor = IncomingBalls.RED;
        return IncomingBalls.RED;
      } else if (foundColor.color == blueColor) {
        timer.reset();
        lastBallColor = IncomingBalls.BLUE;
        return IncomingBalls.BLUE;
      }
    }
    if (timer.hasElapsed(0.5)) {
      lastBallColor = IncomingBalls.NONE;
      return IncomingBalls.NONE;
    }
    return lastBallColor;
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
