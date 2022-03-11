// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.enums.ClimberDirection;
import frc.robot.subsystems.ClimberSubsystem;


public class ClimberControl extends CommandBase {
  private final ClimberSubsystem _system;
  private final Timer timer;
  private final double _time;
  private final ClimberDirection _direction;


  /** Creates a new ClimberControl. */
  public ClimberControl(ClimberSubsystem subsystem, double time, ClimberDirection direction){
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    _system = subsystem;
    timer = new Timer();
    _time = time;
    _direction = direction;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //System.out.println("Climber command init");
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(_direction == ClimberDirection.DOWN) {
      _system.goDown();
    } else if (_direction == ClimberDirection.UP) {
      _system.goUp();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //System.out.println("Climber command ended");

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(_time);
  }
}
