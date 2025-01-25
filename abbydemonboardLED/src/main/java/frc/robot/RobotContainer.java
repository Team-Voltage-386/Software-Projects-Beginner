package frc.robot;

import frc.robot.Subsystems.LEDSubsystem;

public class RobotContainer {

    public final LEDSubsystem m_LedSubsystem;

    public RobotContainer() {
        this.m_LedSubsystem = new LEDSubsystem();

    }

    public LEDSubsystem getLedSubsystem() {
        return m_LedSubsystem;
    }
}