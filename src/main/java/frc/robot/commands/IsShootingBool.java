// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IsShootingBool extends CommandBase {
  /** Creates a new IsClimbingBool. */
  private IntakeSubsystem _intake;
  private boolean state;
  public IsShootingBool(IntakeSubsystem intake, boolean bool) {
    // Use addRequirements() here to declare subsystem dependencies.
    _intake = intake;
    addRequirements(intake);
    state = bool;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    _intake.setIsShooting(state);
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
