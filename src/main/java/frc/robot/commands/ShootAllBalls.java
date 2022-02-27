// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IntakeSubsystem;


public class ShootAllBalls extends CommandBase {
  /** Creates a new ShootAllBalls. */
  private final ShooterSubsystem _shooterSubsystem;
  private final IntakeSubsystem _intakeSubsystem;
  Timer myTimer = new Timer();
  public ShootAllBalls(ShooterSubsystem shooter, IntakeSubsystem intake) {
    // Use addRequirements() here to declare subsystem dependencies.
     _shooterSubsystem = shooter;
     addRequirements(shooter);
     _intakeSubsystem = intake;
     addRequirements(intake);
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
    _shooterSubsystem.setShooterSpeed(70.0);
    if(myTimer.hasElapsed(0.5)){
      _shooterSubsystem.runBeltForward();
      _intakeSubsystem.runIntakeForward();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return myTimer.hasElapsed(0.75);
  }
}
