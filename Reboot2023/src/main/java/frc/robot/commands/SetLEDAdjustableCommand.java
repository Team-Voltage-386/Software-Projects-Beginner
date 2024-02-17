package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LightSubsystem;

public class SetLEDAdjustableCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final LightSubsystem m_subsystem;

  public SetLEDAdjustableCommand(LightSubsystem subsystem) {
    this.m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    int index = this.m_subsystem.getSingleLEDIndex();
    int r = this.m_subsystem.getAdjustableLEDR();
    int g = this.m_subsystem.getAdjustableLEDG();
    int b = this.m_subsystem.getAdjustableLEDB();
    this.m_subsystem.setLED(index, r, g, b);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
