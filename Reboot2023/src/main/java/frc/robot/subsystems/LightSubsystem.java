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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.SetAllLEDAdjustableCommand;
import frc.robot.commands.SetAllLEDCommand;
import frc.robot.commands.SetLEDAdjustableCommand;
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

    m_setLEDR = Shuffleboard.getTab(getName())
      .add("Adjustable LED R", 0)
      .withPosition(3, 0)
      .withSize(2, 1)
      .withProperties(Map.of("min", 0, "max", 255))
      .getEntry();

    m_setLEDG = Shuffleboard.getTab(getName())
      .add("Adjustable LED G", 0)
      .withPosition(3, 1)
      .withSize(2, 1)
      .withProperties(Map.of("min", 0, "max", 255))
      .getEntry();

    m_setLEDB = Shuffleboard.getTab(getName())
      .add("Adjustable LED B", 0)
      .withPosition(3, 2)
      .withSize(2, 1)
      .withProperties(Map.of("min", 0, "max", 255))
      .getEntry();
  }

  public void setLED(int index, int r, int g, int b) {
    /*
     * Set LED 'index' to the color (r, g, b)
     */
    if (index < this.m_ledBuffer.getLength()) {
      this.m_ledBuffer.setRGB(index, r, g, b);
      this.m_led.setData(this.m_ledBuffer);
    }
    return;
  }

  public void setAllLED(int r, int g, int b) {
    for (int i = 0; i < this.getLEDBufferLength(); i++) {
      this.setLED(i, r, g, b);
    }
    return;
  }

  public int getLEDBufferLength() {
    return this.m_ledBuffer.getLength();
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
    long r = this.m_setLEDR.getInteger(0);
    if (r >= 0 && r <= 255) {
      return (int)r;
    } else {
      return 0;
    }
  }

  public int getAdjustableLEDG() {
    long g = this.m_setLEDG.getInteger(0);
    if (g >= 0 && g <= 255) {
      return (int)g;
    } else {
      return 0;
    }
  }

  public int getAdjustableLEDB() {
    long b = this.m_setLEDB.getInteger(0);
    if (b >= 0 && b <= 255) {
      return (int)b;
    } else {
      return 0;
    }
  }

  public Command setSingleLEDCommand(int r, int g, int b) {
    return new SetLEDCommand(this, r, g, b);
  }

  public Command setAllLEDCommand(int r, int g, int b) {
    return new SetAllLEDCommand(this, r, g, b);
  }

  public Command setSingleLEDAdjustableCommand() {
    return new SetLEDAdjustableCommand(this);
  }

  public Command setAllLEDAdjustableCommand() {
    return new SetAllLEDAdjustableCommand(this);
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
