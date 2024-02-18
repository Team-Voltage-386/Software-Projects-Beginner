// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.LightSubsystem;
import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final LightSubsystem m_lightSubystem;

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    this.m_lightSubystem = new LightSubsystem();
    this.m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    m_driverController.a().onTrue(m_lightSubystem.setSingleLEDCommand(255, 0, 0))
        .onFalse(m_lightSubystem.setSingleLEDCommand(0, 0, 0));
    m_driverController.b().onTrue(m_lightSubystem.setAllLEDCommand(255, 0, 0))
        .onFalse(m_lightSubystem.setAllLEDCommand(0, 0, 0));
    m_driverController.x().onTrue(m_lightSubystem.setSingleLEDAdjustableCommand())
        .onFalse(m_lightSubystem.setSingleLEDCommand(0, 0, 0));
    m_driverController.y().onTrue(m_lightSubystem.setAllLEDAdjustableCommand())
        .onFalse(m_lightSubystem.setAllLEDCommand(0, 0, 0));
    m_driverController.leftBumper().onTrue(m_lightSubystem.setBackLeftLEDCommand(255, 255, 0))
        .onFalse(m_lightSubystem.setBackLeftLEDCommand(0, 0, 0));
    m_driverController.leftTrigger(0.5).onTrue(m_lightSubystem.setFrontLeftLEDCommand(255, 255, 0))
        .onFalse(m_lightSubystem.setFrontLeftLEDCommand(0, 0, 0));
    m_driverController.rightBumper().onTrue(m_lightSubystem.setBackRightLEDCommand(255, 255, 0))
        .onFalse(m_lightSubystem.setBackRightLEDCommand(0, 0, 0));
    m_driverController.rightTrigger(0.5).onTrue(m_lightSubystem.setFrontRightLEDCommand(255, 255, 0))
        .onFalse(m_lightSubystem.setFrontRightLEDCommand(0, 0, 0));

  }

  public Command turnLightsOffCommand() {
    // Normally, commands can't run when the robot is disabled
    // In this case, we want to be able to turn the lights off when the robot is
    // disabled
    // This is how you can allow commands to run when disabled
    return this.m_lightSubystem.setAllLEDCommand(0, 0, 0).ignoringDisable(true);
  }
}
