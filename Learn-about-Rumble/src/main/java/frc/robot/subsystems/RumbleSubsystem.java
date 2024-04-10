// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.RumblePulseCommand;

public class RumbleSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private CommandXboxController m_controller;
  private GenericEntry m_rumbleRight;
  private GenericEntry m_rumbleLeft;
  private GenericEntry m_timeBetweenPulses;
  private double m_rumbleRightLastSet;
  private double m_rumbleLeftLastSet;

  public RumbleSubsystem(CommandXboxController controller, int maxTimeBetweenPulses) {
    super("RumbleSubsystem");
    this.m_controller = controller;
    this.m_rumbleRight = Shuffleboard.getTab(this.getName())
      .add("Rumble Right", 0.5)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1))
      .getEntry();
    this.m_rumbleLeft = Shuffleboard.getTab(this.getName())
      .add("Rumble Left", 0.5)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1))
      .getEntry();
    this.m_timeBetweenPulses = Shuffleboard.getTab(this.getName())
      .add("Time Between Pulses", 0.5)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", maxTimeBetweenPulses))
      .getEntry();
    this.m_rumbleRightLastSet = 0.0;
    this.m_rumbleLeftLastSet = 0.0;
  }

  public void setRumble(RumbleType type, double value) {
    m_controller.getHID().setRumble(type, value);
    this.updateLastRumble(type, value);
  }

  public void updateLastRumble(RumbleType type, double value) {
    switch (type) {
      case kBothRumble:
        this.updateLastRumble(RumbleType.kLeftRumble, value);
        this.updateLastRumble(RumbleType.kRightRumble, value);
        break;
      case kLeftRumble:
        this.m_rumbleLeftLastSet = value;
        break;
      case kRightRumble:
        this.m_rumbleRightLastSet = value;
        break;
      default:
        assert(false);
        break;
    }
  }

   public Command setRumbleCommand() {
    return new RumblePulseCommand(this);
   }

   public double getRumbleLeft() {
    return this.m_rumbleLeft.getDouble(0.5);
   }

   public double getRumbleRight() {
    return this.m_rumbleRight.getDouble(0.5);
   }

   public double getTimeBetweenPulses() {
    return this.m_timeBetweenPulses.getDouble(0.5);
   }

   public boolean isRumbling(RumbleType type) {
    switch (type) {
      case kBothRumble:
        return this.isRumbling(RumbleType.kLeftRumble) && this.isRumbling(RumbleType.kRightRumble);
      case kLeftRumble:
        return this.m_rumbleLeftLastSet > 0.0;
      case kRightRumble:
        return this.m_rumbleRightLastSet > 0.0;
      default:
        assert(false);
        return false;
    }
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
