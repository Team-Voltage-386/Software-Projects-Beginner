// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.LightSubsystem;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class WaitThenRunCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "unused"})
  private final Runnable m_somethingToRun;
  private final Supplier<Boolean> m_waitCondition;
  private boolean m_haveRun;
  private boolean m_isDone;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public WaitThenRunCommand(Runnable somethingToRun, Supplier<Boolean> waitCondition) {
    this.m_somethingToRun = somethingToRun;
    this.m_waitCondition = waitCondition;
    this.m_haveRun = false;
    this.m_isDone = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_haveRun && m_waitCondition.get()) {
        m_haveRun = true;
        m_somethingToRun.run();
        m_isDone = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_isDone;
  }
}
