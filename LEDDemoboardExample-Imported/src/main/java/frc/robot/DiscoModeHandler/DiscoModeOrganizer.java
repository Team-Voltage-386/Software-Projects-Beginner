package frc.robot.DiscoModeHandler;

import edu.wpi.first.wpilibj2.command.Command;

public class DiscoModeOrganizer extends Command { //Decides what to do

    
    DiscoModeState modeState;
    DiscoLightState lightState;

    public DiscoModeOrganizer(){
        this.modeState = new DiscoModeState(ModeState.INIT);
        this.lightState = new DiscoLightState(LightState.INIT);
    }

    public void setStates(int switchState /* whether to use Sequential (1) or Collective (2), provide 0 for Initial */, 
    int switchLights/* Which color to switch to, 0 is White (Init), 1 is Red, 2 is Blue, 3 is Green */){
        switch (switchState){
            case 0: {
                modeState.set(ModeState.INIT);
                break;
            }
            case 1: {
                modeState.set(ModeState.SEQUENTIAL);
                break;
            }
            case 2: {
                modeState.set(ModeState.COLLECTIVE);
                break;
            }
            default: {
                modeState.set(ModeState.INIT);
                break;
            }
        }
        switch (switchLights){
            case 0: {
                lightState.set(LightState.INIT);
                break;
            }
            case 1: {
                lightState.set(LightState.RED);
                break;
            }
            case 2:{
                lightState.set(LightState.GREEN);
                break;
            }
            case 3: {
                lightState.set(LightState.BLUE);
                break;
            }
        }
        return;
    }



}
