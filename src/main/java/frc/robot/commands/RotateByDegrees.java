// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;

public class RotateByDegrees extends CommandBase {
  /** Creates a new RotateByDegrees. */
  private final DrivebaseSubsystem _drivebase;
  private double _degrees;
  Command drive;

  public RotateByDegrees(DrivebaseSubsystem drivebase, double degrees) {
    _drivebase = drivebase;
    addRequirements(drivebase);
    _degrees = degrees;
    //converts the degrees to turn to distance for the wheels
    double distance = DegreesToDistance(_degrees);
    //drives each set of wheels the distance in oppisite directions 
    drive = new DriveForwardAutonomously(_drivebase, distance, -distance);

    // Use addRequirements() here to declare subsystem dependencies.
  }

   

  public double DegreesToDistance(double x){
    return (Math.PI * 0.5842d * (x / 360d));
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.initialize();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.execute();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drive.isFinished();
  }
}
