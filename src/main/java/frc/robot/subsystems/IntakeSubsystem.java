package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.enums.IncomingBalls;
import frc.robot.Constants;
import frc.robot.commands.AutoIntakeBalls;
import edu.wpi.first.wpilibj.Timer;
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

  public IntakeSubsystem() {
    belt.setNeutralMode(NeutralMode.Brake);
    belt.setInverted(false);
    timer.start();
    intake.setInverted(false);
    // color stuff
    _colorMatcher.addColorMatch(redColor);
    _colorMatcher.addColorMatch(blueColor);
    intake.setNeutralMode(NeutralMode.Brake);
  }

  public void runIntakeForward() {
    intake.set(1);
  }

  public void runIntakeReverse() {
    intake.set(-1);
  }
  public void intakeOff() {
    intake.set(0);
  }

  public boolean ballInBelt(){
    return !beltSwitch.get();
  }

  public void runBeltForward(){
    belt.set(0.9);
   }

  public void runBeltReverse(){
    belt.set(-0.9);
  }

  public void beltOff(){
    belt.set(0);
  }

  @Override
  public void periodic() {
    

    intake.set(0);
    belt.set(0);

    

    ballColorToEnum();
    
  }

  // public boolean ballColorToTeam(IncomingBalls ballColor) {
  //   if (DriverStation.getAlliance() == Alliance.Red && ballColor == IncomingBalls.RED) {
  //     return true;
  //   }
  //   if (DriverStation.getAlliance() == Alliance.Blue && ballColor == IncomingBalls.BLUE) {
  //     return true;
  //   }
  //   return false;
  // }



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
    if (timer.hasElapsed(2)) {
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
