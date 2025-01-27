package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class UltraSonicSubsystem {
    private Ultrasonic m_ultrasonic = new Ultrasonic(Constants.Ultrasonic.kPingChannel,
            Constants.Ultrasonic.kEchoChannel);
    private double distanceMM;
    private double distanceIn;

    public UltraSonicSubsystem() {

    }

    public void updateDistance() {
        distanceMM = m_ultrasonic.getRangeMM();
        distanceIn = m_ultrasonic.getRangeInches();
        SmartDashboard.putNumber("DistanceMM", distanceMM);
        SmartDashboard.putNumber("DistanceIN", distanceIn);
    }

    public void enablePing() {
        m_ultrasonic.ping();
    }
}
