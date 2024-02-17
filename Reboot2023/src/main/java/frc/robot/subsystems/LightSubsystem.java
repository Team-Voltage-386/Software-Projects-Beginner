// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.SetLEDCommand;

public class LightSubsystem extends SubsystemBase {
  private AddressableLED m_led;
  private AddressableLEDBuffer m_ledBuffer;
  private GenericEntry m_setLEDIndex;
  private GenericEntry m_setLEDR;
  private GenericEntry m_setLEDG;
  private GenericEntry m_setLEDB;

  public LightSubsystem() {
    m_led = new AddressableLED(Constants.LightSubsystem.kPwnPort);
    m_ledBuffer = new AddressableLEDBuffer(Constants.LightSubsystem.kLEDBufferLen);
    m_led.setLength(m_ledBuffer.getLength());
    m_led.setData(m_ledBuffer);
    m_led.start();

    m_setLEDIndex = Shuffleboard.getTab(getName())
      .add("Set Single LED Index", 0)
      .withPosition(0, 0)
      .withSize(2, 1)
      .withProperties(Map.of("min", 0, "max", this.getLEDBufferLength()))
      .getEntry();

    // Add "Adjustable LED R" to Shuffleboard
    // m_setLEDR = ;

    // Add "Adjustable LED G" to Shuffleboard
    // m_setLEDG = ;

    // Add "Adjustable LED B" to Shuffleboard
    // m_setLEDB = ;
  }

  public void setLED(int index, int r, int g, int b) {
    /*
     * Set LED 'index' to the color (r, g, b)
     */
    
    return;
  }

  public void setAllLED(int r, int g, int b) {
    /*
     * Set all LEDs to the color (r, g, b)
     */

    return;
  }

  public void setRangeLED(int startIndex, int endIndex, int r, int g, int b) {
    /*
     * Set all LEDs in the range [startIndex, endIndex) to the color (r, g, b)
     * For example, if the startIndex = 1 and the endIndex = 3, then you would set indexes (1, 2)
     */

    return;
  }

  public int getLEDBufferLength() {
    // Return the length of the LED Buffer
    return 0;
  }

  public int getSingleLEDIndex() {
    long index = this.m_setLEDIndex.getInteger(0);
    if (index > Integer.MIN_VALUE && index < Integer.MAX_VALUE) {
      return (int)index;
    } else {
      return 0;
    }
  }

  public int getAdjustableLEDR() {
    /*
     * Get the R value from the m_setLEDR Generic Entry
     */
    return 0;
  }

  public int getAdjustableLEDG() {
    /*
     * Get the G value from the m_setLEDR Generic Entry
     */
    return 0;
  }

  public int getAdjustableLEDB() {
    /*
     * Get the B value from the m_setLEDR Generic Entry
     */
    return 0;
  }

  public Command setSingleLEDCommand(int r, int g, int b) {
    return new SetLEDCommand(this, r, g, b);
  }

  public Command setAllLEDCommand(int r, int g, int b) {
    /*
     * Return a new SetAllLEDCommand
     */
    return Commands.runOnce(() -> { System.out.println ("NOT IMPLEMENTED"); });
  }

  public Command setFrontLeftLEDCommand(int r, int g, int b) {
    /*
     * Return a new FrontLeftLEDCommand
     */
    return Commands.runOnce(() -> { System.out.println ("NOT IMPLEMENTED"); });
  }

  public Command setBackLeftLEDCommand(int r, int g, int b) {
    /*
     * Return a new SetBackLeftLEDCommand
     */
    return Commands.runOnce(() -> { System.out.println ("NOT IMPLEMENTED"); });
  }

  public Command setFrontRightLEDCommand(int r, int g, int b) {
    /*
     * Return a new SetFrontRightLEDCommand
     */
    return Commands.runOnce(() -> { System.out.println ("NOT IMPLEMENTED"); });
  }

  public Command setBackRightLEDCommand(int r, int g, int b) {
    /*
     * Return a new SetBackRightLEDCommand
     */
    return Commands.runOnce(() -> { System.out.println ("NOT IMPLEMENTED"); });
  }

  public Command setSingleLEDAdjustableCommand() {
    /*
     * Return a new SetLEDAdjustableCommand
     */
    return Commands.runOnce(() -> { System.out.println ("NOT IMPLEMENTED"); });
  }

  public Command setAllLEDAdjustableCommand() {
    /*
     * Return a new SetAllLEDAdjustableCommand
     */
    return Commands.runOnce(() -> { System.out.println ("NOT IMPLEMENTED"); });
  }
}
