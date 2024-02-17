package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LightSubsystem;

/** An example command that uses an example subsystem. */
public class SetAllLEDCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final LightSubsystem m_subsystem;
  private final int m_r;
  private final int m_g;
  private final int m_b;

  public SetAllLEDCommand(LightSubsystem subsystem, int r, int g, int b) {
    this.m_subsystem = subsystem;
    this.m_r = r;
    this.m_g = g;
    this.m_b = b;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    this.m_subsystem.setAllLED(m_r, m_g, m_b);
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
