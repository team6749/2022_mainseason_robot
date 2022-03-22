// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.enums.IncomingBalls;

public class AutoIntakeBalls extends CommandBase {
  private final IntakeSubsystem _intakeSubsystem;
  private IncomingBalls _ball;

  /** Creates a new IntakeStateCommand. */
  public AutoIntakeBalls(IntakeSubsystem subsystem) {
    _intakeSubsystem = subsystem;
    addRequirements(subsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    _ball = _intakeSubsystem.ballColorToEnum();


    
    // Run the bottom belt unless there are 2 balls in the robot
    if ((_intakeSubsystem.ballInBelt() == true && _ball != IncomingBalls.NONE) == false) {
      _intakeSubsystem.runIntakeForward();
    } else {
      _intakeSubsystem.beltOff();
    }
    if (_intakeSubsystem.ballInBelt() == false) {
      _intakeSubsystem.runBeltForward();
    } else {
      _intakeSubsystem.beltOff();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return false;
  }
}
