// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
  private final RobotContainer m_container = new RobotContainer();

  /** Called once at the beginning of the robot program. */
  public Robot() {

  }

  @Override
  // public void robotPeriodic() {
  public void teleopPeriodic() {
    m_container.getLedSubsystem().updateLEDs();
  }

  @Override
  public void disabledPeriodic() {
    m_container.getLedSubsystem().clearLEDs();
  }

}
