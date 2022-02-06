// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.ClimberSubsystem;

enum ClimberDirection { UP, DOWN };

public class ClimberControl extends CommandBase {
  private final ClimberSubsystem m_system;
  private final Timer timer;
  private final double m_time;
  private final ClimberDirection m_direction;

  /** Creates a new ClimberControl. */
  public ClimberControl(ClimberSubsystem subsystem, double time, ClimberDirection direction) {
    // Use addRequirements() here to declare subsystem dependencies.
   m_system = subsystem;
   timer = new Timer();
   m_direction = direction;
   m_time = time;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_direction == ClimberDirection.DOWN) {
      m_system.goDown();
    } else if (m_direction == ClimberDirection.UP) {
      m_system.goUp();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(m_time);
  }
}
