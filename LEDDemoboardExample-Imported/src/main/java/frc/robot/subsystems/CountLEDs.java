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
        for (int i = 0; i <= 80; i++){
            
        }
    }
    
}
