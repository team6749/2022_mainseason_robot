// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ClimberSubsystem;


public class ShootAllBalls extends CommandBase {
  /** Creates a new ShootAllBalls. */
  private final ShooterSubsystem _shooterSubsystem;
  private final IntakeSubsystem _intakeSubsystem;
  private final ClimberSubsystem _climberSubsystem;
  Timer myTimer = new Timer();
  public ShootAllBalls(ShooterSubsystem shooter, IntakeSubsystem intake, ClimberSubsystem climber) {
    // Use addRequirements() here to declare subsystem dependencies.
     _shooterSubsystem = shooter;
     addRequirements(shooter);
     _intakeSubsystem = intake;
     addRequirements(intake);
    _climberSubsystem = climber;
    // addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    myTimer.reset();
    myTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    _shooterSubsystem.setShooterSpeed(75.0);
    if(myTimer.hasElapsed(0.75)){
      _shooterSubsystem.runBeltForward();
      _intakeSubsystem.runIntakeForward();
      if(_shooterSubsystem.ballInBelt()){
        _shooterSubsystem.beltOff();
        _intakeSubsystem.intakeOff();
      }
      if(_shooterSubsystem.ballInBelt() && myTimer.hasElapsed(1.5)){
        _shooterSubsystem.runBeltForward();
        _intakeSubsystem.runIntakeForward();
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(_climberSubsystem.getBool()) { // if true
      //Do NOT run the shoot command at all, if the climber subsystem is down.
      return true;
    }
    return myTimer.hasElapsed(2);
  }
}
