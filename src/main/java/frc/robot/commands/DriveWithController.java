// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;

public class DriveWithController extends CommandBase {
  private final DrivebaseSubsystem m_drive;
  private final XboxController m_controller;

  
  /** Creates a new DriveWithController. */
  public DriveWithController(DrivebaseSubsystem subsystem, XboxController controller) {
    m_controller = controller;
    m_drive = subsystem;
    addRequirements(m_drive);
    addRequirements(m_controller);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = m_controller.getLeftX(); 
    double rotation = m_controller.getRightY();
    m_drive.arcadeDrive(speed, rotation);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //if some condition is met, the drive can be interrupted
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    //some finishing condition? just end only maybe; command never ends fully
  }
}
