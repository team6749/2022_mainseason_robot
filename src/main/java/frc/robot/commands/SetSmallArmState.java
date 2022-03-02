// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.ClimberControl;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.enums.SmallArmState;


public class SetSmallArmState extends CommandBase {
  private final ClimberSubsystem _subsystem;
  private final SmallArmState _armState;
  /** Creates a new SetSmallArmState. */
  public SetSmallArmState(ClimberSubsystem subsystem, SmallArmState armState) {
    _subsystem = subsystem;
    addRequirements(subsystem);
    _armState = armState;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(_armState == SmallArmState.BACKWARD){
      _subsystem.armMoveBackward();
    }

    if(_armState == SmallArmState.FORWARD){
      _subsystem.armMoveForward();
    }

    if(_armState == SmallArmState.OFF){
      _subsystem.armOff();
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
