// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
  private final RobotContainer m_container = new RobotContainer();
  private final Joystick m_stick = new Joystick(Constants.Joystick.kPort);

  /** Called once at the beginning of the robot program. */
  public Robot() {

  }

  @Override
  // public void robotPeriodic() {
  public void teleopPeriodic() {

    if (m_stick.getRawButton(Constants.Joystick.kButtonLED)) {
      m_container.getLedSubsystem().updateLEDs();
    } else if (!m_stick.getRawButton(Constants.Joystick.kButtonLED)) {
      m_container.getLedSubsystem().clearLEDs();
    }

  }

  @Override
  public void disabledPeriodic() {
    m_container.getLedSubsystem().clearLEDs();
  }

}
