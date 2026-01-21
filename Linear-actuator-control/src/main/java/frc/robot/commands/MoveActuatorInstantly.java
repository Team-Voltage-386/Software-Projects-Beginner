// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.LinearActuator;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class MoveActuatorInstantly extends InstantCommand {

  LinearActuator actuator;
  protected double position;

  public MoveActuatorInstantly(LinearActuator actuator, double position) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(actuator);
    this.actuator = actuator;
    this.position = position;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    actuator.setPosition(position);
  }
}
