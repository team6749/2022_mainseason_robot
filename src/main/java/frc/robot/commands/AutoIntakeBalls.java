// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class AutoIntakeBalls extends CommandBase {
  private final IntakeSubsystem m_system;
  private final Constants m_colorSensor;
  /** Creates a new AutoIntakeBalls. */
  public AutoIntakeBalls(IntakeSubsystem system, Constants color) {
    addRequirements(system);
    m_colorSensor = color;
    m_system = system;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(IntakeSubsystem.)
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
