// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LightSubsystem;

public class SetRainbowCommand extends Command {
  /** Creates a new SetRainbowCommand. */
  private final LightSubsystem m_subsystem;

  public SetRainbowCommand(LightSubsystem subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("initializing rainbow");
    // this.m_subsystem.rainbow();

  }

  // called continuously
  @Override
  public void execute() {
    this.m_subsystem.rainbow();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
