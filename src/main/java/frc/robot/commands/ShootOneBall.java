// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;


public class ShootOneBall extends CommandBase {
  /** Creates a new ShootOneBall. */
  public double speed = 75;
  private final ShooterSubsystem _shooterSubsystem;
  private final IntakeSubsystem _intakeSubsystem;
  private final ClimberSubsystem _climberSubsystem;
  Timer myTimer = new Timer();
  private boolean ballReady;
  private double warmupTime;
  public ShootOneBall(ShooterSubsystem shooter, IntakeSubsystem intake, ClimberSubsystem climber) {
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
    return Math.abs(speed - _shooterSubsystem.getShooterSpeed()) < 2.5d;
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    myTimer.reset();
    myTimer.start();
    ballReady = false;
    warmupTime = 0.75;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      //constant speed of shooter set to the speed
    _shooterSubsystem.setShooterSpeed(speed);
    //if the shooter is with in range of the speed to shoot
    if(shooterUpToSpeed()){
        //if the ball is at the limit switch, the ball is ready and turn off belt+intake
        if(_intakeSubsystem.ballInBelt()){
            _intakeSubsystem.beltOff();
            _intakeSubsystem.intakeOff();
            ballReady = true;
        //default behavior: run the intake and belt normally
        } else {
            _intakeSubsystem.runBeltForwardShooting();
            _intakeSubsystem.runIntakeForward();
        }
        //if the ball is ready to be shot and the warmup time has passed, shoot the ball
        if(ballReady && _intakeSubsystem.ballInBelt() && myTimer.hasElapsed(warmupTime)){
            _intakeSubsystem.runBeltForwardShooting();
            _intakeSubsystem.runIntakeForward();
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

    return myTimer.hasElapsed(2);
  }
}