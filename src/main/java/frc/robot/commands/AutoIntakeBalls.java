// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.enums.IncomingBalls;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoIntakeBalls extends CommandBase {
  private final IntakeSubsystem m_intakeSubsystem;
  private IncomingBalls m_ball;
  private final ShooterSubsystem m_shootSubsystem;

  /** Creates a new IntakeStateCommand. */
  public AutoIntakeBalls(IntakeSubsystem subsystem, ShooterSubsystem shootSubsystem) {
    m_intakeSubsystem = subsystem;
    addRequirements(subsystem);
    m_shootSubsystem = shootSubsystem;
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
    m_ball = m_intakeSubsystem.ballColorToEnum();
    // switch (m_ball) {
    // case NONE:
    // m_subsystem.runIntakeForward();
    // break;
    // case RED:
    // m_subsystem.runIntakeForward();
    // break;
    // case BLUE:
    // m_subsystem.runIntakeReverse();
    // break;
    // }
    // if(m_shootSubsystem.ballInBelt() == false){
    // m_subsystem.runIntakeForward();
    // }
    // if(m_shootSubsystem.ballInBelt() == true){
    // m_shootSubsystem.dontRun();
    // }else{
    // m_shootSubsystem.runBelt();
    // }

    // Run the bottom belt unless there are 2 balls in the robot
    if ((m_shootSubsystem.ballInBelt() == true && m_ball != IncomingBalls.NONE) == false) {
      m_intakeSubsystem.runIntakeForward();
    } else {
      m_shootSubsystem.dontRun();
    }
    if (m_shootSubsystem.ballInBelt() == false) {
      m_shootSubsystem.runBelt();
    } else {
      m_shootSubsystem.dontRun();
    }

    // if (m_ball == IncomingBalls.RED) {
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
