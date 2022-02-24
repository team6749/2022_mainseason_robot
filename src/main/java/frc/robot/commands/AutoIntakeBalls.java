// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.enums.IncomingBalls;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoIntakeBalls extends CommandBase {
  private final IntakeSubsystem _intakeSubsystem;
  private IncomingBalls _ball;
  private final ShooterSubsystem _shootSubsystem;

  /** Creates a new IntakeStateCommand. */
  public AutoIntakeBalls(IntakeSubsystem subsystem, ShooterSubsystem shootSubsystem) {
    _intakeSubsystem = subsystem;
    addRequirements(subsystem);
    _shootSubsystem = shootSubsystem;
    addRequirements(shootSubsystem);
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
    if ((_shootSubsystem.ballInBelt() == true && _ball != IncomingBalls.NONE) == false) {
      _intakeSubsystem.runIntakeForward();
    } else {
      _shootSubsystem.dontRun();
    }
    if (_shootSubsystem.ballInBelt() == false) {
      _shootSubsystem.runBelt();
    } else {
      _shootSubsystem.dontRun();
    }

    // if (_ball == IncomingBalls.RED) {
    // m_shootSubsystem.runBelt();
    // }

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
