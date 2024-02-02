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
    
    public void changeLEDCOllorSequentially(int ledLength /* The length of all the LEDs, starts with 0. */,
     int r, int g, int b, double pauseForSeconds /* how long to Wait */, 
     LightSubsystem subsystem /* in order to work, this needs to be m_lightSubsystem */)  
     { //Change the LEDs individually
        for (int i = 0; i <= ledLength;){
            subsystem.changeLEDColor(i, r, g, b).schedule();
            if (timer.get() > pauseForSeconds){
                i++;
                timer.reset();
            }
        }
    }

    public void sequentiallyChangeLEDs(DiscoModeState modeState, DiscoLightState lightState){ // Figure out when the LEDs need to change.
        if (modeState.get() == ModeState.SEQUENTIAL){
            switch (lightState.get()){
                case INIT:{
            
                }
                case RED:{

                }
                case GREEN:{

                }
                case BLUE:{
                    
                }

            }
        }  
    }
    
}

