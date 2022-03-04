// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Joystick;



public class driveWithJoystick extends CommandBase {
  private final Joystick _rJoystick;
  private final Joystick _lJoystick;
  private final DrivebaseSubsystem _subsystem;
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
    double speed = _lJoystick.getY();
    double rotation = _rJoystick.getX();

    _subsystem.arcadeDrive(speed, rotation);
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
