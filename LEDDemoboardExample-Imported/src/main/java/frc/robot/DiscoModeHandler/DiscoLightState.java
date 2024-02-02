package frc.robot.DiscoModeHandler;

public class DiscoLightState { //saves the Light State
    private LightState lightState;
    public DiscoLightState(LightState lightState){
        this.lightState = lightState;
    }
    public LightState get(){
        return this.lightState;
    }
    public LightState set(LightState lightState){
        this.lightState = lightState;
        return this.lightState;
    }
}
