// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LightsSubsystem;

public class SetLights extends CommandBase {
  /** Creates a new SetLights. */
  private String _color;
  private LightsSubsystem _subsystem;
  public SetLights(LightsSubsystem subsystem,String color) {
    // Use addRequirements() here to declare subsystem dependencies.
    _subsystem = subsystem;
    addRequirements(subsystem);
    _color = color;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      switch (_color) {
        case "Off":
          _subsystem.Off();
          break;
        case "Alliance Color":
          _subsystem.setAllianceColors();
          break;
        case "White":
          _subsystem.White();
          break;
        case "Red":  
          _subsystem.Red();
          break;
        case "Blue":
          _subsystem.Blue();
          break;
        case "Green":
          _subsystem.Green();
          break;
        case "Yellow":
          _subsystem.Yellow();
          break;
        case "Cyan":
          _subsystem.Cyan();
          break;
        case "Magenta":
          _subsystem.Magenta();
          break;    
        default:
          _subsystem.White();
          break;
      }   
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
