package frc.robot.DiscoModeHandler;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.LightSubsystem;


public class DiscoSequential {
    private Timer timer;
    private DiscoModeState modeState;
    private DiscoLightState lightState;
    private int i = 0;


    public DiscoSequential(DiscoModeState modeState, DiscoLightState lightState){ //This is the constructor!!
        timer = new Timer();
        timer.start();
        this.modeState = modeState;
        this.lightState = lightState;
    }
    // Helper function for DiscoSequential
    private void changeLEDCOllorSequentially(int ledLength /* The length of all the LEDs, starts with 0. */,
     int r, int g, int b, double pauseForSeconds /* how long to Wait */, 
     LightSubsystem subsystem /* in order to work, this needs to be m_lightSubsystem */)  
     { //Change the LEDs individually
        if (i <= ledLength){
            subsystem.changeLEDColor(i, r, g, b).schedule();
            if (timer.get() > pauseForSeconds){
                i++;
                timer.reset();
            }
        }
    }

    public Command discoSequentialMode(LightSubsystem m_lightSubsystem){ // Figure out when the LEDs need to change.
        if (modeState.get() == ModeState.SEQUENTIAL){
            switch (lightState.get()){
                case INIT:{
                    return m_lightSubsystem.changeAllLEDColor(255, 255, 255).andThen(Commands.runOnce(() -> {
                        if (timer.get() > 0.1){
                        lightState.set(LightState.RED);
                        timer.reset();
                    }
                    }));
                    
                }
                case RED:{
                    return Commands.runOnce(() ->{
                        if (i != 9) {
                        changeLEDCOllorSequentially(9, 255, 0, 0, 0.1, m_lightSubsystem);
                        } else {
                            i = 0;
                            lightState.set(LightState.GREEN);
                        }
                    });
                }
                case GREEN:{
                    return Commands.runOnce(() -> {
                        if (i != 9){
                        changeLEDCOllorSequentially(9, 0, 255, 0, 0.1, m_lightSubsystem);
                    } else {
                        i = 0;
                        lightState.set(LightState.BLUE);
                    }
                    });
                }
                case BLUE:{
                    return Commands.runOnce(() -> {
                        if (i != 9) {
                        changeLEDCOllorSequentially(9, 0, 0, 255, 0.1, m_lightSubsystem);
                    } else {
                        i = 0;
                        lightState.set(LightState.RED);
                    }
                    });
                }
                default:{
                    return Commands.run(() -> {});
                }
            }
        }
        return null;
    }
}

