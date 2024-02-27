package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class MotorSubsystem extends SubsystemBase {
  private GenericEntry m_voltageOutputGenericEntry;
  private GenericEntry m_relativePositionOutputGenericEntry;
  private GenericEntry m_relativePositionRawGenericEntry;
  private GenericEntry m_areMotorsRunningGenericEntry;
  private GenericEntry m_pidKPGenericEntry;
  private GenericEntry m_pidKIGenericEntry;
  private GenericEntry m_pidKDGenericEntry;
  private GenericEntry m_goalPositionGenericEntry;
  private GenericEntry m_pidOutputGenericEntry;
  private CANSparkMax m_motor;
  private ArmFeedforward m_armFeedforward;
  private ProfiledPIDController m_armPIDController;
  private Command m_setVoltageToFeedForwardAndPIDCommand;
  private static final double EPSILON = 0.001;

  public MotorSubsystem() {
    this.m_voltageOutputGenericEntry = Shuffleboard.getTab(getName()).add("Output Voltage", 0.0).withPosition(0, 0)
        .withSize(2, 1).getEntry();
    this.m_relativePositionOutputGenericEntry = Shuffleboard.getTab(getName()).add("Output Rel Enc Position", 0.0)
        .withPosition(0, 1).withSize(2, 1).getEntry();
    this.m_relativePositionRawGenericEntry = Shuffleboard.getTab(getName()).add("Raw Rel Enc Position", 0.0)
        .withPosition(0, 2).withSize(2, 1).getEntry();
    this.m_areMotorsRunningGenericEntry = Shuffleboard.getTab(getName()).add("Are motors running?", false)
        .withPosition(0, 3).withSize(2, 1).withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    this.m_pidKPGenericEntry = Shuffleboard.getTab(getName()).add("PID kP", 0.0).withPosition(4, 0)
        .withSize(1, 1).getEntry();

    this.m_pidKIGenericEntry = Shuffleboard.getTab(getName()).add("PID kI", 0.0).withPosition(5, 0)
        .withSize(1, 1).getEntry();

    this.m_pidKDGenericEntry = Shuffleboard.getTab(getName()).add("PID kD", 0.0).withPosition(6, 0)
        .withSize(1, 1).getEntry();

    this.m_goalPositionGenericEntry = Shuffleboard.getTab(getName()).add("Goal Position (Deg)", 0.0).withPosition(5, 1)
        .withSize(1, 1).getEntry();

    this.m_pidOutputGenericEntry = Shuffleboard.getTab(getName()).add("PID Output", 0.0).withPosition(6, 1)
        .withSize(1, 1).getEntry();

    this.m_motor = new CANSparkMax(Constants.Motor.kCANID, MotorType.kBrushless);
    this.m_motor.setIdleMode(IdleMode.kCoast);
    this.m_motor.getEncoder().setPositionConversionFactor(Math.PI * 2 * 1 /
        12.75);
    this.m_motor.getEncoder().setVelocityConversionFactor(Math.PI * 2 * 1.0 /
        12.75 * 1.0 / 60.0);
    this.m_motor.getEncoder().setPosition(Math.toRadians(270));

    this.m_armFeedforward = new ArmFeedforward(0.1, 0.135, 0.001);
    this.m_armPIDController = new ProfiledPIDController(0.0, 0.0, 0.0, new TrapezoidProfile.Constraints(4.0, 8.0));
    this.m_armPIDController.enableContinuousInput(0.0, 2 * Math.PI);

    this.m_setVoltageToFeedForwardAndPIDCommand = run(() -> {
      this.goToPosition(this.getAndClampGoalPosition());
    });

    new Trigger(() -> {
      return Math.abs(this.getMotorVoltage()) < EPSILON;
    }).onTrue(Commands.runOnce(() -> {
      this.m_areMotorsRunningGenericEntry.setBoolean(false);
    }).ignoringDisable(true)).onFalse(Commands.runOnce(() -> {
      this.m_areMotorsRunningGenericEntry.setBoolean(true);
    }).ignoringDisable(true));

    this.setDefaultCommand(this.m_setVoltageToFeedForwardAndPIDCommand);
  }

  public double getAndClampGoalPosition() {
    double goalPositionDeg = this.m_goalPositionGenericEntry.getDouble(0.0);
    double goalPositionRad = Math.toRadians(goalPositionDeg);
    double clampedGoalPosition = MathUtil.clamp(goalPositionRad, 0, 2 * Math.PI);
    this.m_goalPositionGenericEntry.setDouble(Math.toDegrees(clampedGoalPosition));
    return clampedGoalPosition;
  }

  public void updatePID() {
    this.m_armPIDController.setP(this.m_pidKPGenericEntry.getDouble(0.0));
    this.m_armPIDController.setI(this.m_pidKIGenericEntry.getDouble(0.0));
    this.m_armPIDController.setD(this.m_pidKDGenericEntry.getDouble(0.0));
  }

  public void goToPosition(double goalRadians) {
    this.updatePID();
    double pid = m_armPIDController.calculate(this.getArmPosition(), goalRadians);
    TrapezoidProfile.State setpoint = m_armPIDController.getSetpoint();
    double feedforwardOutput = m_armFeedforward.calculate(
        setpoint.position,
        setpoint.velocity);
    m_motor.setVoltage(pid + feedforwardOutput);
  }

  public double getArmPosition() {
    return MathUtil.inputModulus(
        m_motor.getEncoder().getPosition(),
        0.0,
        2 * Math.PI);
  }

  public double getMotorVoltage() {
    return this.m_motor.getAppliedOutput();
  }

  public void setMotorVoltage(double voltage) {
    this.m_motor.setVoltage(voltage);
  }

  @Override
  public void periodic() {
    this.m_voltageOutputGenericEntry.setDouble(this.getMotorVoltage());
    this.m_relativePositionOutputGenericEntry.setDouble(this.getArmPosition());
    this.m_relativePositionRawGenericEntry.setDouble(this.m_motor.getEncoder().getPosition());
  }
}
