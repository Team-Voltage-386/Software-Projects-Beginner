// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LinearActuator extends SubsystemBase {
   Servo actuator;
   double currentPosition;

  /** Creates a new LinearActuator. */
  public LinearActuator() {
    actuator = new Servo(6);
    currentPosition = 0.0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setPosition(double position) {
    if (position > 1.0) {
      System.err.println("Error: Servo position must be <= 1");
    }
    double clampedValue = MathUtil.clamp(position, 0.0, 1.0);
    System.out.println("Setting actauator to " + clampedValue);
    currentPosition = clampedValue;
    // Scale pulse to 1 to 2 milliseconds
    double pulseWidthMicrosec = 1000.0 + 1000*clampedValue;
    actuator.setPulseTimeMicroseconds((int)pulseWidthMicrosec);
  }

  public double getPosition() {
    return currentPosition;
  }
}
