package frc.robot.DiscoModeHandler;
import frc.robot.subsystems.LightSubsystem;
import edu.wpi.first.wpilibj.Timer;

public class DiscoCollective {

    DiscoModeState modeState;
    DiscoLightState lightState;
    Timer timer;

    private DiscoCollective(){ //Contstructor
        Timer timer = new Timer();
        timer.start();
    }

    public void discoCollective(DiscoModeState modeState,DiscoLightState lightState, LightSubsystem m_LightSubsystem){
        //read modeState
        if (modeState.get() == ModeState.COLLECTIVE){
            //read lightState
            switch (lightState.get()){
                //do something about it
                case INIT: {
                    m_LightSubsystem.changeAllLEDColor(255, 255, 255).schedule();
                    if (timer.get() > 0.1){
                        timer.reset();
                        lightState.set(LightState.RED);
                        break;
                    }
                }
                case RED: {
                    m_LightSubsystem.changeAllLEDColor(255, 0, 0).schedule();
                    if (timer.get() >0.1){
                        timer.reset();
                        lightState.set(LightState.GREEN);
                        break;
                    }
                }
                case GREEN:{
                    m_LightSubsystem.changeAllLEDColor(0, 255, 0).schedule();
                    if (timer.get() > 0.1){
                        timer.reset();
                        lightState.set(LightState.BLUE);
                        break;
                    }
                }
                case BLUE:{
                    m_LightSubsystem.changeAllLEDColor(0, 0, 255).schedule();
                    if (timer.get() > 0.1){
                        timer.reset();
                        lightState.set(LightState.RED);
                        break;

                    }

                }

            }
            

        }
    }
}
