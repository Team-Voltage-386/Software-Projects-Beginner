// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.LightSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
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

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    private Timer time;

    public static enum LightState {
        INIT,
        RED,
        GREEN,
        BLUE
    }

    private LightState state;
    public static int runs = 0; //The number of times that the current switch statement has repeated the same case. Should always stay 1 or 0.
    public static boolean modeSwitch = false; // Variable to switch the LED Modes
    

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        int i = 0;
        time = new Timer();
        state = LightState.INIT;
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
    // function to change colors timingly when y is pressed.
    synchronized void disco(boolean lightsOn) throws InterruptedException{
        
            //Set lights to Red
            m_lightSubsystem.changeAllLEDColor(255,0,0);
            
            //set lights to green
            m_lightSubsystem.changeAllLEDColor(0, 255, 0);
            
            //set lights to Blue
            m_lightSubsystem.changeAllLEDColor(0, 0, 255);
            
        }
        public void discoMode(int color /* 1 is red, 2 is green, 3 is blue, 0 is white */, int index, Timer time, int pauseInterval, boolean version){
            if (!version){
                switch (color){
                    case 0: {
                        m_lightSubsystem.changeLEDColor(index, 255,255,255).schedule();
                    }
                    case 1: {
                        m_lightSubsystem.changeLEDColor(index, 255, 0, 0).schedule();
                    }
                    case 2: m_driverController.changeLEDColor(index, 0,255,0).schedule();
                }
            }
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

        m_driverController.y().whileTrue(Commands.run(() -> {
            SmartDashboard.putNumber("Time", time.get()); //Puts the time between the shifts on the shuffleboard, resets every collor switch
            if (!modeSwitch){
            switch(state){
                case INIT: { //Arms the disco mode.
                    state = LightState.BLUE;
                    time.start(); //Starts the timer that shifts between colors.
                    SmartDashboard.putString("Light State", "InitIalized");
                    break;
                }
                case BLUE: { //Changes the LEDs to Blue for half a second.
                    
                }
                case GREEN: {
                    if(runs == 0){
                    m_lightSubsystem.changeAllLEDColor(0, 255, 0).schedule();
                    SmartDashboard.putString("Light State", "Green");
                    time.reset();
                    time.start();
                    runs++;
                    } else {
                        if (time.get() > 0.5) {
                            state = LightState.RED;
                            time.stop();
                            runs--;
                        }
                    }
                }
                case RED: {
                    if (runs == 0){
                    m_lightSubsystem.changeAllLEDColor(255,0,0).schedule();
                    SmartDashboard.putString("Light State", "Red");
                    time.reset();
                    time.start();
                    runs++;
                    } else {
                        if (time.get() > 0.5) {
                            state = LightState.BLUE;
                            time.stop();
                            runs--;
                        }
                    }
                }
            }
        } else {
            switch (state) {
                case INIT: {
                    state = LightState.BLUE;
                    time.start();
                    SmartDashboard.putString("Light State", "Initialized V2");
                    runs = 0;
                    break;
                }
                case BLUE: {
                    if (runs < 10) {
                        m_lightSubsystem.changeLEDColor(runs, 0, 0, 255).schedule();
                        SmartDashboard.putString("Light State", "Changed LED " + runs + " to Blue");
                        if (time.get() > 0.1) {
                            time.reset();
                            runs++;
                        }
                    } else {
                        if (time.get() > 0.1) {
                            runs = 0;
                            state = LightState.GREEN;
                            time.reset();
                        }
                    }
                }
                case GREEN: {
                    if (runs < 10){
                        m_lightSubsystem.changeLEDColor(runs, 0, 255, 0).schedule();
                        SmartDashboard.putString("Light State", "Changed LED " + runs + " to Green");
                        if (time.get() > 0.1) {
                            time.reset();
                            runs++;
                        }
                    } else {
                        if (time.get() > 0.1){
                            runs = 0;
                            time.reset();
                            state = LightState.RED;
                        }
                    }
                }
                case RED:  {
                    if (runs < 10){
                        m_lightSubsystem.changeLEDColor(runs, 255,0,0).schedule();
                        SmartDashboard.putString("Light State", "Changed LED " + runs + " to Red");
                        if (time.get() > 0.1){
                            time.reset();
                            runs++;
                        }
                    } else {
                        if (time.get() > 0.1){
                            runs = 0;
                            time.reset();
                            state = LightState.BLUE;
                        }
                    }
                }
                }
            }
        }));
    m_driverController.y().whileFalse(Commands.runOnce( () -> {
       SmartDashboard.putString("Light State", "Off.");
        m_lightSubsystem.changeAllLEDColor(0, 0, 0).schedule();
    }));
    m_driverController.leftBumper().whileTrue(Commands.runOnce( () -> {
        modeSwitch = !modeSwitch;
        state = LightState.INIT;
        runs = 0;
        SmartDashboard.putBoolean("LED Mode v2", modeSwitch);
        SmartDashboard.putString("Light State", "Waiting with White...");
        m_lightSubsystem.changeAllLEDColor(255, 255, 255).schedule();
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
