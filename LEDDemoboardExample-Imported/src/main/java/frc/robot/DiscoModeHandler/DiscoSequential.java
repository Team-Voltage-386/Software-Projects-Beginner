package frc.robot.DiscoModeHandler;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.LightSubsystem;


public class DiscoSequential {
    private Timer timer;
    private DiscoModeState modeState;
    private DiscoLightState lightState;


    public DiscoSequential(DiscoModeState modeState, DiscoLightState lightState){ //This is the constructor!!
        timer = new Timer();
        timer.start();
        this.modeState = modeState;
        this.lightState = lightState;
    }
    
    public void changeLEDCOllorSequentially(int ledLength, int r, int g, int b, double pauseForSeconds, LightSubsystem subsystem){ //Change the LEDs individually
        for (int i = 0; i <= ledLength;){
            subsystem.changeLEDColor(i, r, g, b).schedule();
            if (timer.get() > pauseForSeconds){
                i++;
                timer.reset();
            }
        }
    }

    public void sequentiallyChangeLEDs(DiscoModeState modeState){ // Figure out when the LEDs need to change.
        if (modeState.get() == ModeState.SEQUENTIAL){
            
        }
    }
}

