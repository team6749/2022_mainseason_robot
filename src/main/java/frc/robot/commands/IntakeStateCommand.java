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
  private IncomingBalls m_ball;
  private BeltStatus m_status;
  private final ShooterSubsystem m_shootSubsystem;
  

  /** Creates a new IntakeStateCommand. */
  public IntakeStateCommand(IntakeSubsystem subsystem, ShooterSubsystem shootSubsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
    m_shootSubsystem = shootSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute(){
    // if(m_ball == IncomingBalls.NONE){
    //   m_subsystem.stop();
    // }
    // if(m_ball == IncomingBalls.RED){
    //   m_subsystem.start();
    // }
    // if(m_ball == IncomingBalls.BLUE){
    //   m_subsystem.reverse();
    // }

    // if(m_status == BeltStatus.NONE){
    //   m_shootSubsystem.runBelt();
    // }
    // if(m_status == BeltStatus.ONE){
    //   m_shootSubsystem.runBelt();
    // }
    // if(m_status == BeltStatus.TWO){
    //   m_shootSubsystem.dontRun();
    // }
    // if(m_status == BeltStatus.ATTOP &&  m_ball == IncomingBalls.RED){
    //   m_subsystem.start();
    //   } else if(m_status == BeltStatus.ATTOP){
    //     m_shootSubsystem.dontRun(); 
    //   }
      m_ball = m_subsystem.ballColorToEnum();
      switch(m_status){
        case NONE: 
          m_shootSubsystem.runBelt();
          break;
        case ONE:
          m_shootSubsystem.runBelt();
          break;
        case TWO:
          m_shootSubsystem.dontRun();
          break;
        case ATTOP:
          m_shootSubsystem.dontRun();
          break;
      }
      switch(m_ball){
        case NONE:
          m_subsystem.stop();
          break;
        case RED:
          m_subsystem.start();
          break;
        case BLUE:
          m_subsystem.reverse();
          break;
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
