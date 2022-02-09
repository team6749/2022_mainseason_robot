package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase  {
    private final WPI_TalonSRX ClimbMotor1 = new WPI_TalonSRX(Constants.climber1);
    private final WPI_TalonSRX ClimbMotor2 = new WPI_TalonSRX(Constants.climber2);

public ClimberSubsystem() {}

@Override
public void periodic() {
  // This method will be called once per scheduler run
}

@Override
public void simulationPeriodic() {
  // This method will be called once per scheduler run during simulation
}
}
