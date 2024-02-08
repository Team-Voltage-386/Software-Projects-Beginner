package frc.robot.DiscoModeHandler;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.LightSubsystem;

public class DiscoModeOrganizer { // Decides what to do

    DiscoModeState modeState;
    DiscoLightState lightState;
    DiscoCollective discoCollective;
    LightSubsystem m_lightSubsystem;
    private Command m_DiscoCommand;
    private int i = 0;

    public DiscoModeOrganizer(LightSubsystem lightSubsystem) {
        this.modeState = new DiscoModeState(ModeState.COLLECTIVE);
        this.lightState = new DiscoLightState(LightState.INIT);
        this.discoCollective = new DiscoCollective(modeState, lightState);
        this.m_lightSubsystem = lightSubsystem;
        this.m_DiscoCommand = null;
    }

    private void setMode(int switchMode /* whether to use Collective (0) Sequential (1) or Rainbow (2) */) {
        if ((switchMode > 2) || (switchMode < 0)) {
            System.out.println("setMode() requires a number no smaller than 0 and no bigger than 2.");
        } else if ((switchMode % 1) != 0) {
            System.out.println("setMode() requires a whole number.");
        } else {
            switch (switchMode) {
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

    public void toggleDiscoModes(){
        i++;
        if (i > 2){
            i = 0;
            System.out.println("'i' reset to 0");
        }
        setMode(i);
    }

    public Command runDiscoMode() { // Run Disco Modes
        return Commands.runEnd(() -> {
            if (m_DiscoCommand == null) {
                switch (modeState.get()) {
                    case COLLECTIVE: {
                        m_DiscoCommand = discoCollective.discoCollective(modeState, lightState, m_lightSubsystem)
                                .andThen(Commands.runOnce(() -> {
                                    System.out.println("Disco Collective successfully ran.");
                                })).finallyDo(() -> m_DiscoCommand = null);
                        break;
                    }
                    case SEQUENTIAL: {
                        
                        break;
                    }
                    case RAINBOW: {
                        m_DiscoCommand = Commands.runOnce(() -> {
                            System.out.println("Rainbow mode is still a work in progress, and is currently unavailable.");
                        });
                        break;
                    }
                    default: {
                        m_DiscoCommand = null;
                        break;
                    }
                }
                if (m_DiscoCommand != null) {
                    m_DiscoCommand.schedule();
                }
            }

        }, () -> {
            if (m_DiscoCommand != null){
                m_DiscoCommand.cancel();
                m_lightSubsystem.changeAllLEDColor(0, 0, 0).schedule();
            }
        });

    }

}
