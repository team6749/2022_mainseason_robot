// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command _autonomousCommand;
  // private final ClimberSubsystem m_system = new ClimberSubsystem();
  private RobotContainer _robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    _robotContainer = new RobotContainer();
    
    CommandScheduler.getInstance()
      .onCommandInitialize(
        command ->
          SmartDashboard.putString(
            "Command initialized", command.getName()));

    CommandScheduler.getInstance()
      .onCommandInterrupt(
        command ->
          SmartDashboard.putString(
            "Command executed", command.getName()));

    CommandScheduler.getInstance()
      .onCommandInterrupt(
        command ->
          SmartDashboard.putString(
            "Command interrupted", command.getName()));

    CommandScheduler.getInstance()
      .onCommandFinish(
        command ->
          SmartDashboard.putString(
            "Command finished", command.getName()));
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    _robotContainer.periodic();

  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    _robotContainer._DrivebaseSubsystem.setBreakMode(NeutralMode.Coast);
    //DO NOT ADD CLIMBER HERE - FUTURE SELF NOTE
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    _robotContainer._lights.setAllianceColors();
    _robotContainer._ClimberSubsystem.armMoveBackward();
    _robotContainer._DrivebaseSubsystem.setBreakMode(NeutralMode.Brake);
    _autonomousCommand = _robotContainer.getAutonomousCommand();
    // schedule the autonomous command (example)
    if (_autonomousCommand != null) {
      _autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (_autonomousCommand != null) {
      _autonomousCommand.cancel();
    }
    _robotContainer._IntakeSubsystem.intakeEnabled = true;
    _robotContainer._lights.setAllianceColors();
    _robotContainer._ClimberSubsystem.armMoveBackward();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    _robotContainer._ClimberSubsystem.armMoveBackward(); //set arm to start pos in init
    _robotContainer._DrivebaseSubsystem.setBreakMode(NeutralMode.Brake);
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}