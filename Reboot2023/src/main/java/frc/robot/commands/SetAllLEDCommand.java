package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LightSubsystem;

/** An example command that uses an example subsystem. */
public class SetAllLEDCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  /*
   * Add member variables here. You should include:
   *  The Light Subystem
   *  The r color
   *  The g color
   *  The b color
   * 
   */
  private final LightSubsystem m_subsystem;

  /*
   * Add arguments to the constructor
   */
  public SetAllLEDCommand(LightSubsystem subsystem) {
    /*
     * Initialize all variables
     */
    this.m_subsystem = subsystem;
    
    // Add requirement to the Light Subystem
    addRequirements(this.m_subsystem);
  }

  @Override
  public void initialize() {
    /*
     * Call the setAllLEDCommand from LightSubystem
     */
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
