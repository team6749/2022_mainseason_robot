// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class MoveClimberToPosition extends CommandBase {

  ClimberSubsystem m_climberSubsystem;

  double newPosition;

  /** Creates a new MoveClimberToPosition. */
  public MoveClimberToPosition(ClimberSubsystem climberSubsystem, double position) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climberSubsystem = climberSubsystem;
    addRequirements(climberSubsystem);
    newPosition = position;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Move to position started");
    m_climberSubsystem.setClimberPosition(newPosition);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Move to position ended");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_climberSubsystem.isClimberAtPosition();
  }
}
