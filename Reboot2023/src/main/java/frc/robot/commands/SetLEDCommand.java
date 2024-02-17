package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LightSubsystem;

public class SetLEDCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final LightSubsystem m_subsystem;
  private final int m_r;
  private final int m_g;
  private final int m_b;

  public SetLEDCommand(LightSubsystem subsystem, int r, int g, int b) {
    this.m_subsystem = subsystem;
    this.m_r = r;
    this.m_g = g;
    this.m_b = b;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    int index = this.m_subsystem.getSingleLEDIndex();
    this.m_subsystem.setLED(index, this.m_r, this.m_g, this.m_b);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
