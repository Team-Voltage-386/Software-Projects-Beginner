package frc.robot.DiscoModeHandler;

public class DiscoModeState { //Saves the Mode State
    private ModeState modeState;
    public DiscoModeState(ModeState modeState){
        this.modeState = modeState;
    }
    public ModeState get(){
        return this.modeState;
    }
    public ModeState set(ModeState modeState){
        this.modeState = modeState;
        return this.modeState;
    }
}
