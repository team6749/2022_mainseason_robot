// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;


public class ShootTwoBalls extends CommandBase {
  /** Creates a new ShootOneBall. */
  public double speed = 80;
  private final ShooterSubsystem _shooterSubsystem;
  private final IntakeSubsystem _intakeSubsystem;
  private final ClimberSubsystem _climberSubsystem;
  Timer myTimer = new Timer();
  Timer secondBallWarmpupTimer = new Timer();
  private boolean firstBallReady;
  private boolean firstIsShooting;
  private boolean secondBallReady;
  private boolean firstBallLogged;
  private boolean secondBallLogged;
  private boolean secondBallShot;
  private double firstWarmupTime;
  private double secondWarmupTime;
  public ShootTwoBalls(ShooterSubsystem shooter, IntakeSubsystem intake, ClimberSubsystem climber) {
    // Use addRequirements() here to declare subsystem dependencies.
    _shooterSubsystem = shooter;
    addRequirements(intake);
    _climberSubsystem = climber;
    addRequirements(shooter);
    _intakeSubsystem = intake;
    // addRequirements(climber);
  }

  //returns true when the shooter is up to speed
  public boolean shooterUpToSpeed(){
    return Math.abs(speed - _shooterSubsystem.getShooterSpeed()) < 3d;
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    myTimer.reset();
    myTimer.start();
    firstBallReady = false;
    firstIsShooting = false;
    secondBallReady = false;
    firstBallLogged = false;
    secondBallLogged = false;
    secondBallShot = false;
    secondBallWarmpupTimer.reset();
    secondBallWarmpupTimer.stop();
    firstWarmupTime = 0.75;
    secondWarmupTime = 1.15;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      //constant speed of shooter set to the speed
    _shooterSubsystem.setShooterSpeed(speed);
    //if the shooter is with in range of the speed to shoot
    if(shooterUpToSpeed()){
      if(_intakeSubsystem.ballInBelt() && myTimer.hasElapsed(firstWarmupTime)){
        _intakeSubsystem.beltOff();
        _intakeSubsystem.intakeOff();
        firstBallReady = true;
      } else {
        _intakeSubsystem.runBeltForwardShooting();
        _intakeSubsystem.runIntakeForward();
      }
      if(firstBallReady || myTimer.hasElapsed(firstWarmupTime)){
        _intakeSubsystem.runBeltForwardShooting();
        _intakeSubsystem.runIntakeForward();
        firstIsShooting = true;
        if(!firstBallLogged){
          System.out.println("Ball 1 : " + myTimer.get());
          firstBallLogged = true;
        }
      }
      if((firstIsShooting && _intakeSubsystem.ballInBelt()) || myTimer.hasElapsed(1.3)){
        _intakeSubsystem.beltOff();
        _intakeSubsystem.intakeOff();
        secondBallReady = true;
        secondBallWarmpupTimer.start();
      }
      if(firstIsShooting && secondBallReady && secondBallWarmpupTimer.hasElapsed(secondWarmupTime)){
        _intakeSubsystem.runIntakeForward();
        _intakeSubsystem.runBeltForwardShooting();
        secondBallShot = true;
        if(!secondBallLogged){
          System.out.println("Ball 2 : " + myTimer.get());
          secondBallLogged = true;
        }
      }  
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (_climberSubsystem.getShortArms()) { // if true
        // Do NOT run the shoot command at all, if the climber subsystem is down.
        return true;
    }
    if(secondBallShot){
      return true;
    }
    return myTimer.hasElapsed(5);
  }
}