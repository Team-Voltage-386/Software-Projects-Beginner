// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.DiscoModeHandler.DiscoModeOrganizer;
import frc.robot.subsystems.LightSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
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
    private final LightSubsystem m_lightSubsystem = new LightSubsystem();
    private final DiscoModeOrganizer m_organizer = new DiscoModeOrganizer(m_lightSubsystem);
    private int i = 0;

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);



    public static int runs = 0; //The number of times that the current switch statement has repeated the same case. Should always stay 1 or 0.
    public static boolean modeSwitch = false; // Variable to switch the LED Modes
    

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();
    }

    public void turnOnRed()
    {
        //System.out.println("turning on RED!!");
        //m_lightSubsystem.changeAllLEDColor(255,0,0);
        //System.out.println("Purple");
      //  m_lightSubsystem.allPurple();
        m_lightSubsystem.setToColor(0, 255, 0, 0);
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
        // Schedule `changeAllLEDColor` when the Xbox controller's B button is pressed, change LED color to Blue.
        // cancelling on release.
        m_driverController.b().whileTrue(m_lightSubsystem.changeAllLEDColor(0,0,255));
        // Schedule `changeAllLEDColor` to clear when the Xbox controller's A button is pressed, turn off LEDs.
        // cancelling on release.
        m_driverController.a().whileTrue(m_lightSubsystem.changeAllLEDColor(0, 0, 0));
        // Schedule `changeAllLEDColor` to clear when the Xbox controller's X button is pressed, turn LEDs pink.
        // cancelling on release.
        m_driverController.x().whileTrue(m_lightSubsystem.changeAllLEDColor(128, 0, 128));
        //Run Disoc Mode when the Xbox controller's Y button is pressed. This fucntion will schedule the 'changeLEDCollor' function.
        //Cycles from White, to Red, to Green, to Blue, then back to Red, then Green, and so on.
        m_driverController.y().whileTrue(m_organizer.runDiscoMode());
        //Cycles through the Disco modes as the left bumper is pressed.
        m_driverController.leftBumper().onTrue(Commands.runOnce(() -> {
            i++;
            if (i >=3) {
                i  = 0;
            }
            m_organizer.setMode(i);
            System.out.println("Mode switched to: " + i);
        }));
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        // return Autos.exampleAuto(m_exampleSubsystem);
        return null;
    }

    public Command turnLightsOffCommand() {
        return m_lightSubsystem.changeAllLEDColor(0, 0, 0);
    }

    public boolean areLightsOn() {
        return m_lightSubsystem.areLightsOn();
    }
}
