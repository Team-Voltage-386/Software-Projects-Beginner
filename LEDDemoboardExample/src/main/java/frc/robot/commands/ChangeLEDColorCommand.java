// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.LightSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class ChangeLEDColorCommand extends Command {
    static enum PresetColorEnum {
        RED,
        BLUE,
        GREEN,
    }

    private final LightSubsystem m_subsystem;
    private final int m_index;
    private int m_r;
    private int m_g;
    private int m_b;

    /**
     * Creates a new ChangeLEDColorCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ChangeLEDColorCommand(LightSubsystem subsystem, int index, int r, int g, int b) {
        m_subsystem = subsystem;
        m_index = index;
        m_r = r;
        m_g = g;
        m_b = b;

        // Use addRequirements() here to declare subsystem dependencies.
        // addRequirements(subsystem);
    }

    public ChangeLEDColorCommand(LightSubsystem subsystem, int index, PresetColorEnum presetColor) {
        m_subsystem = subsystem;
        m_index = index;
        this.setToPreset(presetColor);

        // Use addRequirements() here to declare subsystem dependencies.
        // addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        this.m_subsystem.setToColor(m_index, m_r, m_g, m_b);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }

    public void setToPreset(PresetColorEnum presetColor) {
        this.m_r = 0;
        this.m_g = 0;
        this.m_b = 0;
        switch (presetColor) {
            case RED:
                this.m_r = 255;
                break;
            case GREEN:
                this.m_g = 255;
                break;
            case BLUE:
                this.m_b = 255;
                break;
        }
        return;
    }
}
