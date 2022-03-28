// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;



public class driveWithJoystick extends CommandBase {
  private final Joystick _rJoystick;
  private final Joystick _lJoystick;
  private final DrivebaseSubsystem _subsystem;

  
  private SlewRateLimiter limitSpeed = new SlewRateLimiter(3);
  private SlewRateLimiter limitRotation = new SlewRateLimiter(5);
  /** Creates a new driveWithJoystick. */
  public driveWithJoystick(Joystick rjoystick, Joystick lJoystick, DrivebaseSubsystem subsystem) {
    _subsystem = subsystem;
    addRequirements(subsystem);
    _lJoystick = lJoystick;
    _rJoystick = rjoystick;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    _subsystem.setBreakMode(NeutralMode.Brake);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //arcade drive variables + drive
    double speed = -_lJoystick.getY();
    double rotation = _rJoystick.getX();

    
    //acceleration curve
    _subsystem.arcadeDrive(limitSpeed.calculate(speed), limitRotation.calculate(rotation * 0.7));

  
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
