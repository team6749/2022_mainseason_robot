// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.WPI_CANCoder;

public class SwerveDrive extends SubsystemBase {
  /** Creates a new SwerveDrive. */
  // private final WPI_TalonFX frontLeftSpeed = new WPI_TalonFX(Constants.SwerveFLSpd);
  // private final WPI_TalonFX frontRightSpeed = new WPI_TalonFX(Constants.SwerveFRSpd);
  // private final WPI_TalonFX backLeftSpeed = new WPI_TalonFX(Constants.SwerveBLSpd);
  // private final WPI_TalonFX backRightSpeed = new WPI_TalonFX(Constants.SwerveBRSpd);

  // private final WPI_TalonFX frontLeftAngle = new WPI_TalonFX(Constants.SwerveFLAngle);
  // private final WPI_TalonFX frontRightAngle = new WPI_TalonFX(Constants.SwerveFRAngle);
  // private final WPI_TalonFX backLeftAngle = new WPI_TalonFX(Constants.SwerveBLAngle);
  // private final WPI_TalonFX backRightAngle = new WPI_TalonFX(Constants.SwerveBRAngle);

  // Locations for the swerve drive modules relative to the robot center.
private final Translation2d _frontLeftSwerve = new Translation2d(0.2794, 0.2794);
private final Translation2d _frontRightSwerve = new Translation2d(0.2794, -0.2794);
private final Translation2d _backLeftSwerve = new Translation2d(-0.2794, 0.2794);
private final Translation2d _backRightSwerve = new Translation2d(-0.2794, -0.2794);

private final CANCoder _frontLeftEncoder = new CANCoder(Constants.CanCoderfl);
private final CANCoder _frontRightEncoder = new CANCoder(Constants.CanCoderfr);
private final CANCoder _BackLeftEncoder = new CANCoder(Constants.CanCoderbl);
private final CANCoder _BackRightEncoder = new CANCoder(Constants.CanCoderbr);


SwerveDriveKinematics kinematics = new SwerveDriveKinematics(_frontLeftSwerve, _frontRightSwerve, _backLeftSwerve, _backRightSwerve);



// Creating my kinematics object using the module locations

  
  public SwerveDrive() {
    
  }

  public void defaultDrive(double xSpd, double ySpd, double theta) {
    //convert spd to 4 motors speed, 4 motors at <theta> angle
    ChassisSpeeds speeds = new ChassisSpeeds(xSpd, ySpd, theta);

    // Convert to module states
    SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(speeds);

    // Front left module state
    SwerveModuleState frontLeft = SwerveModuleState.optimize(moduleStates[0], new Rotation2d(_frontLeftEncoder.getPosition()));

    // Front right module state
    SwerveModuleState frontRight = SwerveModuleState.optimize(moduleStates[1], new Rotation2d(_frontRightEncoder.getPosition()));

    // Back left module state
    SwerveModuleState backLeft = SwerveModuleState.optimize(moduleStates[2], new Rotation2d(_BackLeftEncoder.getPosition()));

    // Back right module state
    SwerveModuleState backRight = SwerveModuleState.optimize(moduleStates[3], new Rotation2d(_BackRightEncoder.getPosition()));

    // double forward = chassisSpeeds.vxMetersPerSecond;
    // double sideways = chassisSpeeds.vyMetersPerSecond;
    // double angular = chassisSpeeds.omegaRadiansPerSecond;

  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
