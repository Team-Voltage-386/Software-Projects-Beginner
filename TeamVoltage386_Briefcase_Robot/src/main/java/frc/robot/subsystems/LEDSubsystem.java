package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
    /**Creates a new LED Subsystem
     * kLEDPort is the pwm port (right side of the roboRIO)
     */
    private static final int kLEDPort = 0;
    private static final int kLEDLength = 10;

    AddressableLED led = new AddressableLED(kLEDPort);

    //The buffer is what actually sends the new values and changes the LEDs
    AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(kLEDLength);

    public LEDSubsystem() {
        led.setLength(kLEDLength);
        led.setData(ledBuffer);
        led.start();
    }
    
    public void allPurple() {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, 120, 0, 200);
        }
    }

    public void allOff() {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, 0, 0, 0);
        }
    }

    //This method will be called once per scheduler run
    @Override
    public void periodic() {
        allPurple();
        led.setData(ledBuffer);
    }
}
