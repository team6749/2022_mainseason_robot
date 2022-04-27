// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;

public class DriveForwardAutonomously extends CommandBase {

  DrivebaseSubsystem _subsystem;
  double _leftDistance;
  double _rightDistance;

  double _leftStarting;
  double _rightStarting;
  boolean atTarget = false;
  
  double _speed;
  /** Creates a new DriveForwardAutotonomously command. 
   * @param subsystem the drivebase subsystem to drive the robot
   * @param left the distance to drive the left side of the drive train in meters
   * @param right the distance to drive the right side of the drive train in meters
   * drives the robot at with positive speed in the direction of the intake
  */
  public DriveForwardAutonomously(DrivebaseSubsystem subsystem, double left, double right) {
    _subsystem = subsystem;
    addRequirements(subsystem);
    _leftDistance = left;
    _rightDistance = right;
    _speed = 0.575;
  }
  /** Creates a new DriveForwardAutotonomously command. 
   * @param subsystem the drivebase subsystem to drive the robot
   * @param left the distance to drive the left side of the drive train in meters
   * @param right the distance to drive the right side of the drive train in meters
   * @param speed the speed to drive the robot, **  must be between 0 and 1 **
   * drives the robot at with positive speed in the direction of the intake
   * @throws Exception
  */
  public DriveForwardAutonomously(DrivebaseSubsystem subsystem, double left, double right, double speed) {
    _subsystem = subsystem;
    addRequirements(subsystem);
    _leftDistance = left;
    _rightDistance = right;
    _speed = speed;

    // if(_speed <= 0 || _speed > 1){
    //   throw new Exception("Speed cannot be equal to or less than zero, or greater than one");
    // }
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Command Started");
    _leftStarting = _subsystem.getLeftEncoder();
    _rightStarting = _subsystem.getRightEncoder();
    _subsystem.setBreakMode(NeutralMode.Brake);
    atTarget = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //variables for distance from where the robot started
    double _leftFromStart = (_subsystem.getLeftEncoder() - _leftStarting);
    double _rightFromStart = (_subsystem.getRightEncoder() - _rightStarting);
    // SmartDashboard.putNumber("right1", _rightFromStart);


    double leftMotorSpeed = calculateMotorOutput(_leftFromStart, _leftDistance);
    double rightMotorSpeed = calculateMotorOutput(_rightFromStart, _rightDistance);
    // SmartDashboard.putNumber("speed left", leftMotorSpeed);
    // SmartDashboard.putNumber("right2", rightMotorSpeed);


    _subsystem.driveRobotRaw(leftMotorSpeed, rightMotorSpeed);

    if(Math.abs(_leftFromStart - _leftDistance) < 0.02 && Math.abs(_rightFromStart - _rightDistance) < 0.02){
      atTarget = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Command finished");
    // _subsystem.setBreakMode(NeutralMode.Coast);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return atTarget;
  }

  // 
  public double calculateMotorOutput(double value, double targetposition){

    double delta = targetposition - value;
    if(Math.abs(delta) < 0.02) return 0;
    if(delta > 0) {
      return _speed;
    } else {
      return -_speed;
    }

  }
}
