// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

  //Time for shooter to warm up to speed
  // static double warmupTime = 1.5;
  //Time for shooter to recover after shooting.
  static double recoveryTime = 0.75;

  boolean firstBallExited = false;


  boolean secondBallExited = false;
  boolean secondBallReady = false;


  boolean hasLogged = false;

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
    firstBallExited = false;
    secondBallExited = false;
    secondBallReady = false;
    hasLogged = false;
  }
  public boolean shooterUpToSpeed(){
    return Math.abs(75 - _shooterSubsystem.getShooterSpeed()) < 2.5d;
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Always run the shooter during the duration of this command.
    _shooterSubsystem.setShooterSpeed(75.0);
    // Wait for the inital warm up time before feeding the first ball through
    if (shooterUpToSpeed()) {
      // Runs balls through by default
      _intakeSubsystem.runBeltForwardShooting();
      _intakeSubsystem.runIntakeForward();
      // Check to see if the first ball has been released from the switch.
      if (_intakeSubsystem.ballInBelt() == false && firstBallExited == false) {
        firstBallExited = true;
      }

      //
      if(secondBallReady) {
        if(_intakeSubsystem.ballInBelt() == false) {
          //The second ball has left the robot
          secondBallExited = true;
        }
      }
      
      //Start the second ball warmup timer if there is a ball loaded into the top belt
      // and the first ball has been shot
      if(firstBallExited && _intakeSubsystem.ballInBelt()) {
        secondBallWarmpupTimer.start(); // No-op if timer is already running
        // Stop the belt from moving if the second ball is loaded into the switch
        // unless the second ball warmpup timer has elapsed.
        // The second ball cannot be loaded in without the first ball having been shot.
        if (shooterUpToSpeed() == false || secondBallWarmpupTimer.hasElapsed(recoveryTime) == false) {
          _intakeSubsystem.beltOff();
          _intakeSubsystem.intakeOff();
        } else {
          //We aare shooting the next blall
          // System.out.println("Shooting next ball");
          secondBallReady = true;
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
    if(secondBallExited) {
      if(hasLogged == false) {
        System.out.println(myTimer.get());
        SmartDashboard.putNumber("shooting time", myTimer.get());
        hasLogged = true;
      } 
    }
    return myTimer.hasElapsed(2);
  }
}
