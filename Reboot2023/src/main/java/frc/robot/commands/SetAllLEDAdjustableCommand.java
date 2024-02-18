package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LightSubsystem;

/** An example command that uses an example subsystem. */
public class SetAllLEDAdjustableCommand extends Command {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final LightSubsystem m_subsystem;

  public SetAllLEDAdjustableCommand(LightSubsystem subsystem) {
    this.m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    int r = this.m_subsystem.getAdjustableLEDR();
    int g = this.m_subsystem.getAdjustableLEDG();
    int b = this.m_subsystem.getAdjustableLEDB();
    this.m_subsystem.setAllLED(r, g, b);
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
