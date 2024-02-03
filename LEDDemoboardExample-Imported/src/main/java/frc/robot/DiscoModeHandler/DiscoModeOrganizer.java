package frc.robot.DiscoModeHandler;


public class DiscoModeOrganizer { //Decides what to do

    
    DiscoModeState modeState;

    public DiscoModeOrganizer(){
        this.modeState = new DiscoModeState(ModeState.COLLECTIVE);
    }

    public void setMode(int switchMode /* whether to use Collective (0) Sequential (1) or Rainbow (2) */){
        if ((switchMode > 2) || (switchMode < 0)){
            System.out.println("Please provide a whole integer between ");
        } else {
            switch (switchMode){
                case 0: {
                    modeState.set(ModeState.COLLECTIVE);
                    break;
                }
                case 1: {
                        modeState.set(ModeState.SEQUENTIAL);
                    break;
                }
                case 2: {
                    modeState.set(ModeState.RAINBOW);
                    break;
                }
                default: {
                    modeState.set(ModeState.COLLECTIVE);
                    break;
                }
            }
        }
        return;
    }



}
