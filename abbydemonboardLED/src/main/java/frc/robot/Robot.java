// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private final RobotContainer m_container = new RobotContainer();
  private final XboxController m_controller = new XboxController(Constants.Controller.kPort);

  /** Called once at the beginning of the robot program. */
  public Robot() {
    m_container.getUltrasonicSubsystem().enablePing();
  }

  @Override
  // public void robotPeriodic() {
  public void teleopPeriodic() {

    if (m_controller.getRawButton(Constants.Controller.kButtonLED)) {
      m_container.getLedSubsystem().updateLEDs();
    } else if (!m_controller.getRawButton(Constants.Controller.kButtonLED)) {
      m_container.getLedSubsystem().clearLEDs();
    }
    SmartDashboard.putNumber("Joystick Left X", m_controller.getLeftX());
    SmartDashboard.putNumber("Joystick Left Y", m_controller.getLeftY());
    SmartDashboard.putNumber("Joystick Right X", m_controller.getRightX());
    SmartDashboard.putNumber("Joystick Right Y", m_controller.getRightY());

    m_container.getUltrasonicSubsystem().updateDistance();
  }

  @Override
  public void disabledPeriodic() {
    m_container.getLedSubsystem().clearLEDs();
  }

}
