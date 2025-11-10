package frc.sim;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.sim.Pigeon2SimState;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Drivetrain;

public class SimDrivetrain extends Drivetrain {

    protected float desiredXSpeedMps = 0;
    protected float desiredYSpeedMps = 0;
    protected float desiredRotationSpeedDps = 0;
    protected float currentXSpeedMps = 0;
    protected float currentYSPeedMps = 0;
    protected float currentRotSpeedDps = 0;

    protected Pose3d currentPose = new Pose3d();
    protected Pigeon2SimState gyroSimState;

    protected final double loopTime = 0.02;
    
    public SimDrivetrain(Pigeon2 gyro)
    {
        super(gyro);
        gyroSimState = gyro.getSimState();
    }

    public Alliance getAlliance() {
        return Alliance.Blue;
    }

    @Override
    public void simulationPeriodic() {
        // Update position and velocity
        this.currentXSpeedMps = (float)commandedChassisSpeeds.vxMetersPerSecond;
        this.currentYSPeedMps = (float)commandedChassisSpeeds.vyMetersPerSecond;
        this.currentRotSpeedDps = (float)Math.toDegrees(commandedChassisSpeeds.omegaRadiansPerSecond);

        this.currentPose = new Pose3d(currentPose.getX() + currentXSpeedMps*loopTime,
            currentPose.getY() + currentYSPeedMps*loopTime, 0.0,
            new Rotation3d(0.0, 0.0, currentPose.getRotation().getZ() + Math.toRadians(currentRotSpeedDps*loopTime)));  
        gyroSimState.setRawYaw(this.currentPose.getRotation().getZ());
    }

    public Pose3d getSimPose() {
        return this.currentPose;
    }

    public void setSimPose(Pose3d newPose) {
        this.currentPose = newPose;
    }

    @Override
    public void updateOdometry() {
        // For the odometry to the simulated robot position since we are
        // not simulating the actual swerve drive dynamics yet
        m_odometry.resetPose(getSimPose().toPose2d());
        m_odometry.resetRotation(getSimPose().getRotation().toRotation2d());

        // Set the chassis speeds to the true simulated speeds
        m_chassisSpeeds = new ChassisSpeeds(this.currentXSpeedMps, 
            this.currentYSPeedMps, this.currentRotSpeedDps);

        field.setRobotPose(getRoboPose2d());
    }
}
