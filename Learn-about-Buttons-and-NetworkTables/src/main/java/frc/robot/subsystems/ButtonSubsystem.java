// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.IntegerEntry;
import edu.wpi.first.networktables.StringEntry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ButtonSubsystem extends SubsystemBase {
  final private String [] stringArray = {"A", "B", "X", "Y"};
  private int arrayIndex = 0;
  private IntegerEntry [] entryArray;
  private StringEntry stringEntry;
  private long tempValue = 0;

  /** Creates a new ButtonSubsystem. */
  public ButtonSubsystem(NetworkTableInstance nt) {
    entryArray = new IntegerEntry[stringArray.length];

    // Initialize the Network Table Entries
    stringEntry = nt.getTable("Button Counters").getStringTopic("Info String").getEntry("NA");

    for (int i = 0; i < stringArray.length; i++) {
      entryArray[i] = nt.getTable("Button Counters").getIntegerTopic("Count " + stringArray[i]).getEntry(0);
      entryArray[i].set(0);
    }
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command pressButtonCommand(NetworkTableInstance nt, int buttonIndex) {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          // Set the array index
          arrayIndex = buttonIndex;

          // Guard against numbers that are too high or too low
          if (buttonIndex > stringArray.length) arrayIndex = stringArray.length;
          if (buttonIndex < 0) arrayIndex = 0;
          
          // Output information to the terminal
          System.out.println("Pressed " + stringArray[arrayIndex]);

          // Output to the NetworkTable
          tempValue = entryArray[arrayIndex].get();
          tempValue++;
          entryArray[arrayIndex].set(tempValue);
          stringEntry.set("Incremented count for: " + stringArray[arrayIndex]);
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
