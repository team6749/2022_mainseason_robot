// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LightsSubsystem;

public class SetLights extends CommandBase {
  /** Creates a new SetLights. */
  private LightsSubsystem _lights;
  private ClimberSubsystem _climber;
  private IntakeSubsystem _intake;

  public SetLights(LightsSubsystem subsystem, ClimberSubsystem climb, IntakeSubsystem intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    _lights = subsystem;
    addRequirements(subsystem);
    _climber = climb;
    _intake = intake;
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      if(_climber.getIsClimbing()){
        _lights.Green();
      } else if(_intake.getBallsInBot() >= 1){
        _lights.Cyan();
      }  else if(_intake.getIsShooting()){
        _lights.Magenta();
      } else {
        _lights.setAllianceColors();
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
