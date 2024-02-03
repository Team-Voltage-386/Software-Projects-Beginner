package frc.robot.DiscoModeHandler;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.LightSubsystem;



public class DiscoModeOrganizer { //Decides what to do

    DiscoModeState modeState;
    DiscoCollective discoCollective;
    DiscoSequential discoSequential;
    DiscoLightState lightState;
    LightSubsystem m_lightSubsystem;


    public DiscoModeOrganizer(){
        this.modeState = new DiscoModeState(ModeState.COLLECTIVE);
        this.lightState = new DiscoLightState(LightState.INIT);
    }

    public void setMode(int switchMode /* whether to use Collective (0) Sequential (1) or Rainbow (2) */){
        if ((switchMode > 2) || (switchMode < 0)){
            System.out.println("setMode() requires a number no smaller than 0 and no bigger than 2.");
        } else if((switchMode % 1) != 0){
            System.out.println("setMode() requires a whole number.");
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

    public Command runDiscoMode(){ //Run Disco Modes
        switch (modeState.get()){
            case COLLECTIVE:{
                return discoCollective.discoCollective(modeState, lightState, m_lightSubsystem);
            }
            case SEQUENTIAL:{
                 return discoSequential.discoSequentialMode(m_lightSubsystem);
            }
            case RAINBOW:{
                return Commands.runOnce(() -> {
                    System.out.println("Rainbow mode is still a work in progress, and is currently unavailable.");
                });
            }
        }
        return null;
    }

}
