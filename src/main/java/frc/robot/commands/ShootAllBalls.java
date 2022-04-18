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
  Timer secondBallWarmpupTimer = new Timer();

  // Time for shooter to warm up to speed
  // static double warmupTime = 1.5;
  // Time for shooter to recover after shooting.
  static double recoveryTime = 0.9;

  boolean firstBallInBelt = false;
  boolean firstBallExited = false;
  boolean secondBallExited = false;
  boolean secondBallInBelt = false;
  boolean hasLoggedB1 = false;
  boolean hasLoggedB2 = false;

  public ShootAllBalls(ShooterSubsystem shooter, IntakeSubsystem intake, ClimberSubsystem climber) {
    // Use addRequirements() here to declare subsystem dependencies.
    _shooterSubsystem = shooter;
    addRequirements(intake);
    _climberSubsystem = climber;
    addRequirements(shooter);
    _intakeSubsystem = intake;
    // addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    myTimer.reset();
    myTimer.start();
    secondBallWarmpupTimer.reset();
    secondBallWarmpupTimer.stop();
    firstBallInBelt = false;
    firstBallExited = false;
    secondBallExited = false;
    secondBallInBelt = false;
    hasLoggedB1 = false;
    hasLoggedB2 = false;
  }

  public boolean shooterUpToSpeed() {
    return Math.abs(75 - _shooterSubsystem.getShooterSpeed()) < 2d;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Always run the shooter during the duration of this command.
    // Wait for the inital warm up time before feeding the first ball through
    _shooterSubsystem.setShooterSpeed(75);
    if (shooterUpToSpeed()) {
      if (_intakeSubsystem.ballInBelt()) {
        firstBallInBelt = true;
      } else {
        _intakeSubsystem.runBeltForwardShooting();
        _intakeSubsystem.runIntakeForward();
      }
      if (firstBallInBelt) {
        _intakeSubsystem.runBeltForwardShooting();
        if (!_intakeSubsystem.ballInBelt()) {
          firstBallExited = true;
          if(!hasLoggedB1){
            System.out.println("Ball 1 shot at " + (double) Math.round(myTimer.get() * 1000d) / 1000d);
            hasLoggedB1 = true;
          }
        }
      }

      if (firstBallExited) {
        secondBallWarmpupTimer.start();
      }
      if (secondBallWarmpupTimer.hasElapsed(recoveryTime) && shooterUpToSpeed()) {
        // System.out.println("ready to shoot second ball");
        if (_intakeSubsystem.ballInBelt()) {
          secondBallInBelt = true;
          // System.out.println("second ball in belt");
        } else {
          _intakeSubsystem.runBeltForwardShooting();
          _intakeSubsystem.runIntakeForward();
        }

        if (secondBallInBelt) {
          // System.out.println("shooting second ball");
          _intakeSubsystem.runBeltForwardShooting();
          if (!_intakeSubsystem.ballInBelt()) {
            secondBallExited = true;
            if(!hasLoggedB2){
              System.out.println("Ball 2 shot at " + (double) Math.round(myTimer.get() * 1000d) / 1000d);
              hasLoggedB2 = true;
            }
          }
        }
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
    if (_climberSubsystem.getShortArms()) { // if true
      // Do NOT run the shoot command at all, if the climber subsystem is down.
      return true;
    }
    return myTimer.hasElapsed(2.25);
  }
}
