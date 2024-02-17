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
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class LightSubsystem {
    // What's the PWN port?
    public static final int kPwnPort = 0;
    // How many LEDs are there?
    public static final int kLEDBufferLen = 32;

    // What's the start LED number of the Front Left LED?
    public static final int kLEDFrontLeftStart = 0;

    // What's the end LED number of the Front Left LED?
    public static final int kLEDFrontLeftEnd = kLEDFrontLeftStart + 31;
    
    // What's the start LED number of the Back Left LED?
    public static final int kLEDBackLeftStart = 0;

    // What's the end LED number of the Back Left LED?
    public static final int kLEDBackLeftEnd = 0;

    // What's the start LED number of the Back Right LED?
    public static final int kLEDBackRightStart = 0;

    // What's the end LED number of the Back Right LED?
    public static final int kLEDBackRightEnd = 0;

    // What's the start LED number of the Front Right LED?
    public static final int kLEDFrontRightStart = 0;

    // What's the end LED number of the Front Right LED?
    public static final int kLEDFrontRightEnd = 0;
  }
}
