package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.enums.IncomingBalls;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class IntakeSubsystem extends SubsystemBase {
private final WPI_TalonFX intake = new WPI_TalonFX(Constants.intakeMotor);

private final ColorSensorV3 colorSensor = new ColorSensorV3(Constants.colorSensorPort);
private final ColorMatch m_colorMatcher = new ColorMatch();

private final Color redColor = new Color(0.48, 0.35, 0.13);
private final Color blueColor = new Color(0.17, 0.42, 0.41);

NetworkTableEntry xEntry;
NetworkTableEntry yEntry;

NetworkTableInstance inst = NetworkTableInstance.getDefault();


NetworkTable table = inst.getTable("datatable");

public IntakeSubsystem() {
  m_colorMatcher.addColorMatch(redColor);
  m_colorMatcher.addColorMatch(blueColor);
 
}
public void runIntakeForward(){
  intake.set(-0.9);
}
public void runIntakeReverse(){
  intake.set(0.9);
}



@Override
  public void periodic() {
    double speed = 0;
    if(RobotContainer.controller.getAButton()) {
      speed = -0.7;
    } else if (RobotContainer.controller.getBButton()) {
      speed = 0.7;
    }
    intake.set(speed);
   

  }
    //color sensor to enum function
    public IncomingBalls ballColorToEnum(){
    Color detectedColor = colorSensor.getColor();
    
    m_colorMatcher.matchColor(detectedColor);
    ColorMatchResult foundColor = m_colorMatcher.matchColor(detectedColor);

      if(foundColor != null){
        if(foundColor.color == redColor){
          return IncomingBalls.RED;
        } else if(foundColor.color == blueColor){
          return IncomingBalls.BLUE;
        }
      }else{
        return IncomingBalls.NONE;
      }
    }
      
    }


    //SmartDashboard.putNumber("confidende", foundColor.confidence);
    //oujiu
    // This method will be called once per scheduler run
    }
  //methods used in commands
  public void stop(){
    intake.set(0);
  }
  public void start(){
    intake.set(0.7);
  }
  public void reverse(){
    intake.set(-0.7);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
