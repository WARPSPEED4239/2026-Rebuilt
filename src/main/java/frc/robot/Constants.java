// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final int
    //Input devices
    XBOX_CONTROLLER = 0,
    JOYSTICK = 1,

    SHOOTER_MOTOR = 1,
    CLIMBER_MOTOR = 2,
    CLIMBER_MOTOR_1 = 3,
    LOADER_MOTOR = 4,
    INTAKE_MOTOR = 5,
    INTAKE_EXTENSION_MOTOR = 6,
    UNDER_SHOOTER_MOTOR = 7,

    //PDH
    PDH = 8,

    //the pigeon
    PIGEON_ID = 13;

  public static final double
    CLIMBER_TOP_LIMIT = 100;

  public static final String
    CANIVORE_BUS = "DriveTrain",
    LIMELIGHT_NAME = "limelight-climber";
  
}