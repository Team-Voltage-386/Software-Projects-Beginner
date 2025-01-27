package frc.robot;

import frc.robot.Subsystems.UltraSonicSubsystem;
import frc.robot.Subsystems.LEDSubsystem;

public class RobotContainer {

    public final LEDSubsystem m_LedSubsystem;
    public final UltraSonicSubsystem m_UltraSonicSubsystem;

    public RobotContainer() {
        this.m_LedSubsystem = new LEDSubsystem();
        this.m_UltraSonicSubsystem = new UltraSonicSubsystem();
    }

    public LEDSubsystem getLedSubsystem() {
        return m_LedSubsystem;

    }

    public UltraSonicSubsystem getUltrasonicSubsystem() {
        return m_UltraSonicSubsystem;

    }
}