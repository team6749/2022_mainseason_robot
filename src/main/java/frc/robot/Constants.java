// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.swing.text.AttributeSet.ColorAttribute;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static int flMotor = 10;
    public static int frMotor = 12;
    public static int blMotor = 11;
    public static int brMotor = 13;
    public static int shooterMotor = 01;
    public static int intakeMotor = 03;
    public static int beltMotor = 05;
    public static int climber1 = 0;
    public static int climber2 = 0;
    // public static Port colorSensorNumber;
    public static I2C.Port colorSensorPort = I2C.Port.kOnboard;
    
}
