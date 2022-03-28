// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ClimberControl;
import frc.robot.commands.DriveForwardAutonomously;
import frc.robot.commands.SetSmallArmState;
import frc.robot.enums.ClimberDirection;
import frc.robot.enums.SmallArmState;
import frc.robot.commands.AutoIntakeBalls;
import frc.robot.commands.ShootAllBalls;
import frc.robot.commands.driveWithJoystick;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  // public static XboxController controller = new XboxController(0);
  public static Joystick leftJoystick = new Joystick(0);
  public static Joystick rightJoystick = new Joystick(1);



  final JoystickButton climberUpButton = new JoystickButton(leftJoystick, 3); // climber up
  final JoystickButton climberDownButton = new JoystickButton(rightJoystick, 3); // climber down
  final JoystickButton shootBallsButton = new JoystickButton(rightJoystick, 2); // shoot ballz

  final JoystickButton smallArmsForward = new JoystickButton(leftJoystick, 5);
  final JoystickButton smallArmsBackward = new JoystickButton(leftJoystick, 4);
  final JoystickButton smallArmsOff = new JoystickButton(leftJoystick, 2);

  // final JoystickButton l3 = new JoystickButton(leftJoystick, 3);

  // The robot's subsystems and commands are defined here
  public final ClimberSubsystem _ClimberSubsystem = new ClimberSubsystem();
  public final DrivebaseSubsystem _DrivebaseSubsystem = new DrivebaseSubsystem();
  public final IntakeSubsystem _IntakeSubsystem = new IntakeSubsystem();
  public final ShooterSubsystem _ShooterSubsystem = new ShooterSubsystem();
  // commands - usually not put here

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    UsbCamera camera1 = CameraServer.startAutomaticCapture(0);
    // camera1.setResolution(1280, 720);
    UsbCamera camera2 = CameraServer.startAutomaticCapture(1);
    // camera1.setResolution(320, 240); 
    // Configure the button bindings
    configureButtonBindings();
    // _IntakeSubsystem.setDefaultCommand(new AutoIntakeBalls(_IntakeSubsystem, true));
    // _DrivebaseSubsystem.setDefaultCommand(new DriveWithController(controller,
    // _DrivebaseSubsystem));
    _DrivebaseSubsystem.setDefaultCommand(new driveWithJoystick(rightJoystick, leftJoystick, _DrivebaseSubsystem));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // new JoystickButton(controller,
    // XboxController.Button.kBack.value).whileHeld(new
    // ClimberControl(_ClimberSubsystem, 0.1, ClimberDirection.UP));
    // new JoystickButton(controller,
    // XboxController.Button.kStart.value).whileHeld(new
    // ClimberControl(_ClimberSubsystem, 0.1, ClimberDirection.DOWN));

    // new JoystickButton(controller,
    // XboxController.Button.kA.value).whenPressed(new
    // SetSmallArmState(_ClimberSubsystem, SmallArmState.OFF));
    // new JoystickButton(controller,
    // XboxController.Button.kY.value).whenPressed(new
    // SetSmallArmState(_ClimberSubsystem, SmallArmState.FORWARD));
    // new JoystickButton(controller,
    // XboxController.Button.kX.value).whenPressed(new
    // SetSmallArmState(_ClimberSubsystem, SmallArmState.BACKWARD));
    // // new JoystickButton(controller, XboxController.Button.kBack.value).(new
    // ClimberControl(climberSubsystem, 0.5, ClimberDirection.UP));
    // new JoystickButton(controller,
    // XboxController.Button.kRightBumper.value).whenPressed(new
    // ShootAllBalls(_ShooterSubsystem, _IntakeSubsystem));
    climberUpButton.whileHeld(new ClimberControl(_ClimberSubsystem, 0.1, ClimberDirection.UP));
    climberDownButton.whileHeld(new ClimberControl(_ClimberSubsystem, 0.1, ClimberDirection.DOWN));
    shootBallsButton.whenPressed(new ShootAllBalls(_ShooterSubsystem, _IntakeSubsystem, _ClimberSubsystem));

    smallArmsForward.whenPressed(new SetSmallArmState(_ClimberSubsystem, SmallArmState.FORWARD));
    smallArmsBackward.whenPressed(new SetSmallArmState(_ClimberSubsystem, SmallArmState.BACKWARD));
    smallArmsOff.whenPressed(new SetSmallArmState(_ClimberSubsystem, SmallArmState.OFF));

    // controler code bindings

    // new JoystickButton(controller,
    // XboxController.Button.kBack.value).whileHeld(new
    // ClimberControl(_ClimberSubsystem, 0.1, ClimberDirection.UP));
    // new JoystickButton(controller,
    // XboxController.Button.kStart.value).whileHeld(new
    // ClimberControl(_ClimberSubsystem, 0.1, ClimberDirection.DOWN));
    // new JoystickButton(controller,
    // XboxController.Button.kRightBumper.value).whenPressed(new
    // ShootAllBalls(_ShooterSubsystem, _IntakeSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous

    return new SequentialCommandGroup(
      new AutoIntakeBalls(_IntakeSubsystem, true),
      new DriveForwardAutonomously(_DrivebaseSubsystem, 1.4224, 1.4224), //goes direction of intake // use edge of hood as rp
      new WaitCommand(0.5),
      new DriveForwardAutonomously(_DrivebaseSubsystem, -1.95, -1.95),
      new AutoIntakeBalls(_IntakeSubsystem, false),
      new WaitCommand(1.0),
      new ShootAllBalls(_ShooterSubsystem, _IntakeSubsystem, _ClimberSubsystem)
    );
  }
}