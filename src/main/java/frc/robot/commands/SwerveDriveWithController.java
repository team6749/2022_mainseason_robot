// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.



// Take your joystick (x,y) value
// convert to (r, theta)
// speed = r
// rotate wheels until angle = theta
//https://www.chiefdelphi.com/uploads/default/original/3X/e/f/ef10db45f7d65f6d4da874cd26db294c7ad469bb.pdf

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrive;
public class SwerveDriveWithController extends CommandBase {
  /** Creates a new SwerveDriveWithController. */
  private final SwerveDrive _drive;
  private final XboxController _controller;

  public SwerveDriveWithController(XboxController contr, SwerveDrive drv) {
    // Use addRequirements() here to declare subsystem dependencies.
    _drive = drv;
    _controller = contr;
    addRequirements(_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double rotation = _controller.getRightX();
    double xSpd = _controller.getLeftX();
    double ySpd = _controller.getLeftY();


    _drive.defaultDrive(xSpd, ySpd, rotation);
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
