package frc.robot.Subsystems;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;

import frc.robot.Constants.LEDs;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
    private AddressableLED m_led = new AddressableLED(LEDs.kLEDPort);
    private AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(LEDs.kLEDLength);

    // Create an LED pattern that will display a rainbow across
    // all hues at maximum saturation and half brightness
    private final LEDPattern m_rainbow = LEDPattern.rainbow(255, 128);

    // Our LED strip has a density of 120 LEDs per meter
    private static final Distance kLedSpacing = Meters.of(1 / 120.0);

    // Create a new pattern that scrolls the rainbow pattern across the LED strip,
    // moving at a speed
    // of 1 meter per second.
    private final LEDPattern m_scrollingRainbow = m_rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(1), kLedSpacing);

    public LEDSubsystem() {

        m_led.setLength(m_ledBuffer.getLength());
        m_led.setData(m_ledBuffer);
        m_led.start();
    }

    public void updateLEDs() {
        // Update the buffer with the rainbow animation
        m_scrollingRainbow.applyTo(m_ledBuffer);
        // Set the LEDs
        m_led.setData(m_ledBuffer);

    }

    public void clearLEDs() {
        for (int i = 0; i < LEDs.kLEDLength; i++) {

            m_ledBuffer.setRGB(i, 0, 0, 0);

            m_led.setData(m_ledBuffer);

        }
    }
}