package frc.robot.DiscoModeHandler;


import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;


public class DiscoSequential {
    private Timer timer;
    private DiscoModeState modeState;
    public DiscoSequential(DiscoModeState modeState){ //This is the constructor!!
        timer = new Timer();
        timer.start();
        this.modeState = modeState;
    }
    
    public void changeLEDCOllorSequentially(int ledLength, int r, int g, int b, double pauseForSeconds, LightSubsystem subsystem){
        for (int i = 0; i <= ledLength;){
            subsystem.changeLEDColor(i, r, g, b).schedule();
            if (timer.get() > pauseForSeconds){
                i++;
            }
        }
    }

    public void sequentiallyChangeLEDs(DiscoModeState modeState){
        if (modeState.get() == ModeState.SEQUENTIAL){
            
        }
    }
    
}
