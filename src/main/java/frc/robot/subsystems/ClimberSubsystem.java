package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase  {
    private final WPI_TalonFX ClimbMotor1 = new WPI_TalonFX(Constants.climber1);
    private final WPI_TalonFX ClimbMotor2 = new WPI_TalonFX(Constants.climber2);

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
