// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.enums.BeltStatus;
import frc.robot.enums.IncomingBalls;
import frc.robot.subsystems.ShooterSubsystem;


public class IntakeStateCommand extends CommandBase {
  private final IntakeSubsystem m_subsystem;
  private final IncomingBalls m_ball;
  private final BeltStatus m_status;
  private final ShooterSubsystem m_shootSubsystem;
  

  /** Creates a new IntakeStateCommand. */
  public IntakeStateCommand(IntakeSubsystem subsystem, IncomingBalls ball, BeltStatus status, ShooterSubsystem shootSubsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
    m_ball = ball;
    m_status = status;
    m_shootSubsystem = shootSubsystem;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute(){
    if(m_ball == IncomingBalls.NONE){
      m_subsystem.stop();
    }
    if(m_ball == IncomingBalls.RED){
      m_subsystem.start();
    }
    if(m_ball == IncomingBalls.BLUE){
      m_subsystem.reverse();
    }

    if(m_status == BeltStatus.NONE){
      m_shootSubsystem.runBelt();
    }
    if(m_status == BeltStatus.ONE){
      m_shootSubsystem.runBelt();
    }
    if(m_status == BeltStatus.TWO){
      m_shootSubsystem.dontRun();
    }
    if(m_status == BeltStatus.ATTOP &&  m_ball == IncomingBalls.RED){
      m_subsystem.start();
      } else if(m_status == BeltStatus.ATTOP){
        m_shootSubsystem.dontRun(); 
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
