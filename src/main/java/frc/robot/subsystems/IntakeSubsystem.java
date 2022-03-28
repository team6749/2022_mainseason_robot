package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.enums.IncomingBalls;
import frc.robot.Constants;
// import frc.robot.commands.AutoIntakeBalls;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj.DriverStation.Alliance;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class IntakeSubsystem extends SubsystemBase {
  private final WPI_TalonSRX intake = new WPI_TalonSRX(Constants.intakeMotor);
  private final WPI_TalonFX belt = new WPI_TalonFX(Constants.beltMotor);
  private final ColorSensorV3 colorSensor = new ColorSensorV3(Constants.colorSensorPort);
  DigitalInput beltSwitch = new DigitalInput(Constants.beltLimitSwitch);

  private final ColorMatch _colorMatcher = new ColorMatch();
  Timer timer = new Timer();
  IncomingBalls lastBallColor;

  private final Color redColor = new Color(0.48, 0.35, 0.13);
  private final Color blueColor = new Color(0.15, 0.40, 0.43);

  NetworkTableEntry xEntry;
  NetworkTableEntry yEntry;

  NetworkTableInstance inst = NetworkTableInstance.getDefault();

  NetworkTable table = inst.getTable("datatable");

  public static DriverStation station;
  private IncomingBalls _ball;
  public boolean intakeEnabled = false;

  public IntakeSubsystem() {
    belt.setNeutralMode(NeutralMode.Brake);
    belt.setInverted(false);
    timer.start();
    intake.setInverted(false);
    
    // color stuff
    _colorMatcher.addColorMatch(redColor);
    _colorMatcher.addColorMatch(blueColor);
    intake.setNeutralMode(NeutralMode.Brake);
    intakeEnabled = true;
  }

  public void runIntakeForward() {
    intake.set(0.6);
  }

  public void runIntakeReverse() {
    intake.set(-0.6);
  }
  public void intakeOff() {
    intake.set(0);
  }

  public boolean ballInBelt(){
    return !beltSwitch.get();
  }

  public void runBeltForward(){
    belt.set(0.5);
   }

  public void runBeltReverse(){
    belt.set(-0.5);
  }

  public void beltOff(){
    belt.set(0);
  }

  public void runIntakeSlowerReverse(){
    intake.set(-0.3); //may need to change power or time of pause of intake (same for runIntakeSlowerForward)
  }

  public void runIntakeSlowerForward(){
    intake.set(0.3);
  } 

  @Override
  public void periodic() {
    intake.set(0);
    belt.set(0);
    // System.out.println(intakeEnabled);
    if(intakeEnabled){
      _ball = ballColorToEnum();
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
        lastBallColor = _ball;
      }
      SmartDashboard.putString("TeamColor", DriverStation.getAlliance().toString());
      SmartDashboard.putBoolean("BAllNotMatchTeam", ballNotMatchTeam(_ball));
      if(ballNotMatchTeam(_ball)){ //if the ball color is not the team color
        //if there is a ball at top of robot and its the right color;  then run intake reverse
        
        if(ballInBelt() && (lastBallCheck(lastBallColor))){
          runIntakeSlowerReverse(); //runs intake reverse slowly for delay
        } else if(!ballInBelt()) {  //if there is no ball at top of robot; then run shooter at low speed (30 - 50)
          // how to run shooter in intake subsytem
          ;
        } else { // default behavior : run intake slowly
          runIntakeSlowerReverse(); 
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

    SmartDashboard.putNumber("red color", detectedColor.red);
    SmartDashboard.putNumber("blue color", detectedColor.blue);
    SmartDashboard.putNumber("green color", detectedColor.green);

    _colorMatcher.matchColor(detectedColor);

    if (lastBallColor == IncomingBalls.RED) {
      SmartDashboard.putString("ballColor", "red");
    } else if (lastBallColor == IncomingBalls.BLUE) {
      SmartDashboard.putString("ballColor", "blue");
    } else {
      SmartDashboard.putString("ballColor", "none");
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
