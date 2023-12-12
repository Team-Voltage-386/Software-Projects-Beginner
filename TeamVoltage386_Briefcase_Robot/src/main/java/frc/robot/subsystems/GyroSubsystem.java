package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GyroSubsystem extends SubsystemBase {

    public ADXRS450_Gyro gyro = new ADXRS450_Gyro();

    public GyroSubsystem() {
        gyro.calibrate();
    }

    public void reset() {
        gyro.reset();
    }
    @Override
    public void periodic() {
        System.out.println("Gyro Angle: " + gyro.getAngle());
        System.out.println("Gyro Rate: " + gyro.getRate());
    }
    
}
