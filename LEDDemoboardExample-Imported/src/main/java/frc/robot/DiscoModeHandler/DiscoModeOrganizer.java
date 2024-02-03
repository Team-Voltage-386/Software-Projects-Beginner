package frc.robot.DiscoModeHandler;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.LightSubsystem;



public class DiscoModeOrganizer { //Decides what to do

    DiscoModeState modeState;
    DiscoLightState lightState;
    DiscoCollective discoCollective;
    DiscoSequential discoSequential;
    LightSubsystem m_lightSubsystem;


    public DiscoModeOrganizer(LightSubsystem lightSubsystem){
        this.modeState = new DiscoModeState(ModeState.COLLECTIVE);
        this.lightState = new DiscoLightState(LightState.INIT);
        this.discoCollective = new DiscoCollective(modeState, lightState);
        this.discoSequential = new DiscoSequential(modeState, lightState);
        this.m_lightSubsystem = lightSubsystem;
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
                    System.out.println("Mode switched via Deafault path");
                    break;
                }
            }
        }
    }

    public Command runDiscoMode(){ //Run Disco Modes
        System.out.println("Run disco mode");
        switch (modeState.get()){
            case COLLECTIVE:{
                return discoCollective.discoCollective(modeState, lightState, m_lightSubsystem).andThen(Commands.runOnce(() -> {
                    System.out.println("Disco Collective successfully ran.");
                }));
            }
            case SEQUENTIAL:{
                 return discoSequential.discoSequentialMode(m_lightSubsystem).andThen(Commands.runOnce(() -> {
                    System.out.println("Disco Sequential successfully ran.");
                }));
            }
            case RAINBOW:{
                return Commands.runOnce(() -> {
                    System.out.println("Rainbow mode is still a work in progress, and is currently unavailable.");
                });
            }
            default:{
                return Commands.runOnce(() -> {
                    System.out.println("Error: Deafault Path");
                });
            }
        }
    }

}
