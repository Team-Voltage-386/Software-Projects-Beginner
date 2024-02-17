package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LightSubsystem;

/** An example command that uses an example subsystem. */
public class SetAllLEDAdjustableCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final LightSubsystem m_subsystem;

  public SetAllLEDAdjustableCommand(LightSubsystem subsystem) {
    this.m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    // Get the R, G, B from Light Subystem by calling:
    // getAdjustableLEDR
    // getAdjustableLEDG
    // getAdjustableLEDB
    // Then, call setAllLED(r, g, b)
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
