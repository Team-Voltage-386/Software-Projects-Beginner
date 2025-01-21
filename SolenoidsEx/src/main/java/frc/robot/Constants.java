// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class Solenoid {
    //public static final PneumaticsModuleType kPneumaticsModuleType = PneumaticsModuleType.REVPH;
    public static final PneumaticsModuleType kModuleType = PneumaticsModuleType.CTREPCM;
    public static final int kChannel = 0;
    // Using channels 1 and 2 because code uses Channel 0 for Single Solenoid
    public static final int kFwdChannel = 1;
    public static final int kREvChannel = 2;
  }

  public static class Joystick {
    public static final int kPort = 0;
    public static final int kSolenoidButton = 1;
    public static final int kDoubleSolenoidForwardButton = 2;
    public static final int kDoubleSolenoidReverseButton = 3;
    public static final int kCompressorButton = 4;
    }
}
