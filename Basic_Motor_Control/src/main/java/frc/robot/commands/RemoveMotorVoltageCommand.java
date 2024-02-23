package frc.robot.commands;

import frc.robot.subsystems.MotorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class RemoveMotorVoltageCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final MotorSubsystem m_subsystem;

    public RemoveMotorVoltageCommand(MotorSubsystem subsystem) {
        m_subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        this.m_subsystem.decrementVoltageGenericEntry();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}