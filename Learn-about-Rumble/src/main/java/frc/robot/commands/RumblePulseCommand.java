// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.RumbleSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class RumblePulseCommand extends Command {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final RumbleSubsystem m_subsystem;

  private Timer m_timer;

  public RumblePulseCommand(RumbleSubsystem subsystem) {
    m_subsystem = subsystem;
    m_timer = new Timer();

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_timer.hasElapsed(this.m_subsystem.getTimeBetweenPulses())) {
      if (m_subsystem.isRumbling(RumbleType.kLeftRumble)) {
        m_subsystem.setRumble(RumbleType.kLeftRumble, 0);
      } else {
        m_subsystem.setRumble(RumbleType.kLeftRumble, m_subsystem.getRumbleLeft());
      }

      if (m_subsystem.isRumbling(RumbleType.kRightRumble)) {
        m_subsystem.setRumble(RumbleType.kRightRumble, 0);
      } else {
        m_subsystem.setRumble(RumbleType.kRightRumble, m_subsystem.getRumbleRight());
      }
      m_timer.reset();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.setRumble(RumbleType.kBothRumble, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
