// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.ChangeLEDColorCommand;

public class LightSubsystem extends SubsystemBase {
    // IMPORTANT! 
    // Modify the port number based on which PWM the LEDs are plugged into
    private static final int kLedPort = 7;

    /**
     * Length of the LED strip.
     * This should be 10 for the briefcase bot.
     * This should be 76 for the Demoboard.
     */
    private static final int kLedLength = 10;

    AddressableLED led = new AddressableLED(kLedPort);

    AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(kLedLength);

    /** Creates a new LightSubsystem. */
    public LightSubsystem() {
        // Start the LEDs
        // Length is expensive to set, so only set it once, then just update data
        led.setLength(kLedLength);
        led.setData(ledBuffer);
        led.start();
    }

    public void setToColor(int index, int r, int g, int b) {
        ledBuffer.setRGB(index, r, g, b);
        return;
    }

    public boolean areLightsOn() {
        // Lights are on if at least one LED has either red, green, or blue that is not
        // 0.
        boolean lightsAreOn = false;
        for (int i = 0; i < kLedLength && !lightsAreOn; i++) {
            Color c = ledBuffer.getLED(i);
            lightsAreOn |= c.blue > 0.1;
            lightsAreOn |= c.green > 0.1;
            lightsAreOn |= c.red > 0.1;
        }
        return lightsAreOn;
    }

    public Command changeLEDColor(int index, int r, int g, int b) {
        // Inline construction of command goes here.
        // Subsystem::RunOnce implicitly requires `this` subsystem.
        return runOnce(() -> new ChangeLEDColorCommand(this, index, r, g, b).ignoringDisable(true));
    }

    public Command changeAllLEDColor(int r, int g, int b) {
        ParallelCommandGroup parallelCommandGroup = new ParallelCommandGroup();
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            // ledBuffer.setRGB(i, 255 ,0, 0);
            parallelCommandGroup.addCommands(new ChangeLEDColorCommand(this, i, r, g, b).ignoringDisable(true));
        }
        return parallelCommandGroup;
    }

    public Command setAllBlue() {
        return runOnce(
                () -> {
                    for (int i = 0; i < ledBuffer.getLength(); i++) {
                        ledBuffer.setRGB(i, 0, 0, 255);
                    }
                });
    }

    public void allPurple() {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, 100, 0, 200);
        }
    }

    /**
     * An example method querying a boolean state of the subsystem (for example, a
     * digital sensor).
     *
     * @return value of some boolean subsystem state, such as a digital sensor.
     */
    public boolean exampleCondition() {
        // Query some boolean state, such as a digital sensor.
        return false;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // Send the current values to the LED strip
        led.setData(ledBuffer);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
