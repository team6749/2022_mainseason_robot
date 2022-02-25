// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;

public class DriveForwardAutonomously extends CommandBase {

  DrivebaseSubsystem m_subsystem;
  double m_leftDistance;
  double m_rightDistance;

  double m_leftStarting;
  double m_rightStarting;
  boolean atTarget = false;

  /** Creates a new DriveForwardAutotonomously. */
  public DriveForwardAutonomously(DrivebaseSubsystem subsystem, double left, double right) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
    m_leftDistance = left;
    m_rightDistance = right;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Command Started");
    m_leftStarting = m_subsystem.getLeftEncoder();
    m_rightDistance = m_subsystem.getRightEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //variables for distance from where the robot started
    double m_leftFromStart = (m_subsystem.getLeftEncoder() - m_leftStarting);
    double m_rightFromStart = (m_subsystem.getRightEncoder() - m_rightStarting);


    double leftMotorSpeed = calculateMotorOutput(m_leftFromStart, m_leftDistance);
    double rightMotorSpeed = calculateMotorOutput(m_rightFromStart, m_rightDistance);

    System.out.println(rightMotorSpeed);

    m_subsystem.driveRobotRaw(leftMotorSpeed, rightMotorSpeed);

    if(Math.abs(m_leftFromStart - m_leftDistance) < 0.20 && Math.abs(m_rightFromStart - m_rightDistance) < 0.20){
      atTarget = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Command finished");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return atTarget;
  }


  public double calculateMotorOutput(double value, double targetposition){

    double delta = targetposition - value;

    System.out.println(delta);
  
    if(delta > 0) {
      return 0.5d;
    } else {
      return -0.5d;
    }

  }
}
