package frc.robot.DiscoModeHandler;


public class DiscoModeHandler {
    public static enum LightState { // Stores the state of the light
        INIT,
        RED,
        GREEN,
        BLUE
    }

    public static enum ModeState {
        INIT,
        COLLECTIVE,
        SEQUENTIAL
    }
    ModeState state = ModeState.INIT;
    public void changeDiscoModeState(int switchState /* whether to use Sequential (1) or Collective (2), provide 0 for Initial */){
        switch (switchState){
            case 0: {
                state = ModeState.INIT;
                break;
            }
            case 1: {
                state = ModeState.SEQUENTIAL;
                break;
            }
            case 2: {
                state = ModeState.COLLECTIVE;
            }
            default: {
                state = ModeState.INIT;
            }
        }
    }
}
