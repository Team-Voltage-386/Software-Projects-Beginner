package frc.robot.DiscoModeHandler;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.LightSubsystem;
import frc.robot.commands.WaitThenRunCommand;
import frc.robot.subsystems.LightSubsystem;

public class DiscoSequential {
    Timer timer;
    DiscoModeState modeState;
    DiscoLightState lightState;
    LightSubsystem m_lightSubsystem;
    private int i = 0;

    public DiscoSequential(DiscoModeState modeState, DiscoLightState lightState){
        timer = new Timer();
        this.modeState = modeState;
        this.lightState = lightState;
    }


    public Command discoSequential(){
        if (modeState.get() == ModeState.COLLECTIVE){
            switch (lightState.get()){
                case INIT:{
                    return m_lightSubsystem.changeAllLEDColor(255, 255, 255).andThen(
                            new WaitThenRunCommand(() -> {
                                timer.reset();
                                lightState.set(LightState.RED);
                            }, () -> {
                                return timer.get() > 0.5;
                            }));
                }
                case RED:{
                    return m_lightSubsystem.changeLEDColor(i, 255, 0,0).andThen(
                        new WaitThenRunCommand(() -> {
                            
                        }, null)
                    );
                }
                case GREEN:{
                    return m_lightSubsystem.changeLEDColor(i, 0,255, 0);
                }
                case BLUE:{
                    return m_lightSubsystem.changeLEDColor(i, 0, 0, 255);
                }
                default:{
                    return m_lightSubsystem.changeAllLEDColor(0, 0, 0);
                }
            }
        }
        return null;
    }



}