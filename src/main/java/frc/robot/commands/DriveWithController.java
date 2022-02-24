// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;

public class DriveWithController extends CommandBase {
  private final DrivebaseSubsystem _drive;
  private final XboxController _controller;

  
  /** Creates a new DriveWithController. */
  public DriveWithController(XboxController controller, DrivebaseSubsystem subsystem) {
    _controller = controller;
    _drive = subsystem;
    addRequirements(_drive);
    // addRequirements(m_controller);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    _drive.setBreakMode(NeutralMode.Brake);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = _controller.getLeftY(); 
    double rotation = _controller.getRightX();
    _drive.arcadeDrive(speed, rotation);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //if some condition is met, the drive can be interrupted
    _drive.setBreakMode(NeutralMode.Coast);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    //some finishing condition? just end only maybe; command never ends fully
  }
}
