package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LightSubsystem;

public class SetBackLeftLEDCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  /*
   * Add member variables
   */

  public SetBackLeftLEDCommand(LightSubsystem subsystem, int r, int g, int b) {
    /*
     * Initialize member variables
     */

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    // Call setRangeLED with the range of LEDs that correspond to the Front Left LED strip
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
