package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;

public class CountLEDs {
    
    
    LightSubsystem m_lightSubsystem;
    Timer timer;
    private int i = 0;

    public CountLEDs(LightSubsystem m_lightSubsystem){
        this.m_lightSubsystem = m_lightSubsystem;
        timer = new Timer();
        timer.start();
    }

    public void countLEDs(){
        if (i < 58){
            m_lightSubsystem.changeLEDColor(i, 255, 255, 255).schedule();
            if (timer.get() > 0.5){
                i++;
                timer.restart();
            }
        }
    }
    
}
