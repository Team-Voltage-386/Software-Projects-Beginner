package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.*;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  private GenericEntry m_voltageOutputGenericEntry;
  private GenericEntry m_relativePositionOutputGenericEntry;
  private GenericEntry m_relativePositionRawGenericEntry;
  private GenericEntry m_areMotorsRunningGenericEntry;
  private GenericEntry m_feedForwardKSGenericEntry;
  private GenericEntry m_feedForwardKGGenericEntry;
  private GenericEntry m_feedForwardKVGenericEntry;
  private GenericEntry m_feedForwardOutputGenericEntry;
  private GenericEntry m_feedForwardActualOutputGenericEntry;
  private GenericEntry m_feedForwardInputPositionGenericEntry;
  private GenericEntry m_feedForwardInputVelocityGenericEntry;
  private SparkMax m_motor;
  private ArmFeedforward m_armFeedforward;
  private Command m_setVoltageToFeedForwardCommand;
  private static final double EPSILON = 0.001;

  public ArmSubsystem() {
    // Shuffleboard objects
    // Informational objects
    this.m_voltageOutputGenericEntry = Shuffleboard.getTab(getName()).add("Output Voltage", 0.0).withPosition(0, 0)
        .withSize(2, 1).getEntry();
    this.m_relativePositionOutputGenericEntry = Shuffleboard.getTab(getName()).add("Output Rel Enc Position", 0.0)
        .withPosition(0, 1).withSize(2, 1).getEntry();
    this.m_relativePositionRawGenericEntry = Shuffleboard.getTab(getName()).add("Raw Rel Enc Position", 0.0)
        .withPosition(0, 2).withSize(2, 1).getEntry();
    this.m_areMotorsRunningGenericEntry = Shuffleboard.getTab(getName()).add("Are motors running?", false)
        .withPosition(0, 3).withSize(2, 1).withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    // Objects to be changed by the user
    this.m_feedForwardKSGenericEntry = Shuffleboard.getTab(getName()).add("FF KS", 0.0).withPosition(4, 0)
        .withSize(1, 1).getEntry();

    this.m_feedForwardKGGenericEntry = Shuffleboard.getTab(getName()).add("FF KG", 0.0).withPosition(5, 0)
        .withSize(1, 1).getEntry();

    this.m_feedForwardKVGenericEntry = Shuffleboard.getTab(getName()).add("FF KV", 0.0).withPosition(6, 0)
        .withSize(1, 1).getEntry();

    // Actual output
    this.m_feedForwardActualOutputGenericEntry = Shuffleboard.getTab(getName()).add("FF Actual Output", 0.0)
        .withPosition(6, 2)
        .withSize(1, 1).getEntry();

    // User provides position and speed and the FF controller will calculate the FF
    // Output that it would supply
    this.m_feedForwardInputPositionGenericEntry = Shuffleboard.getTab(getName()).add("FF Input Pos Rad", 0.0)
        .withPosition(4, 1).withSize(1, 1)
        .getEntry();
    this.m_feedForwardInputVelocityGenericEntry = Shuffleboard.getTab(getName()).add("FF Input Vel Rad_s", 0.0)
        .withPosition(5, 1).withSize(1, 1)
        .getEntry();
    this.m_feedForwardOutputGenericEntry = Shuffleboard.getTab(getName()).add("FF Output", 0.0).withPosition(6, 1)
        .withSize(1, 1).getEntry();

    // There is one motor
    this.m_motor = new SparkMax(Constants.Motor.kCANID, MotorType.kBrushless);
    // coast mode means we have to fight gravity (the motor won't help hold the
    // position)
    
    // The encoder is a relative encoder (not absolute)

    // The gear ratio is 12.75. One entire rotation of the arm is 12.75 rotations of
    // the motor shaft. Multiplying by 2Pi and 1/12.75 transforms the
    // motor position reading (in rotations) to the position of the arm in the range
    // 0 to 2Pi
    
    // multiplying by 1/60 gives us radians/second
    
    SparkMaxConfig config = new SparkMaxConfig();
    config
        .idleMode(IdleMode.kCoast);
    config.encoder
        .positionConversionFactor(Math.PI * 2 * 1 /
            12.75)
        .velocityConversionFactor(Math.PI * 2 * 1.0 /
            12.75 * 1.0 / 60.0);

    this.m_motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Assumes that postion 0 is with the arm straight out parallel to the floor
    // Arm FF depends on this.
    // Therefore the position of the arm when hanging straight down is 270 degrees.
    // But we're using radians here :)
    this.m_motor.getEncoder().setPosition(Math.toRadians(270));

    this.m_armFeedforward = new ArmFeedforward(0.0, 0.0, 0.0);
    // My final values: 0.1, 0.135, 0.001

    this.m_setVoltageToFeedForwardCommand = run(() -> {
      this.updateFeedForward();
      double desiredVelocity;
      // Assume setpoint of position = 0.0 i.e. arm straight out
      // Set speed > 0 when not yet at 0.0 and otherwise set desired speed = 0.0
      if (this.getArmPosition() > EPSILON && (2 * Math.PI - this.getArmPosition()) > EPSILON) {
        // Radians / second
        desiredVelocity = 0.5;
      } else {
        desiredVelocity = 0.0;
      }
      double ffOut = this.m_armFeedforward.calculate(0.0, desiredVelocity);
      this.m_feedForwardActualOutputGenericEntry.setDouble(ffOut);
      this.m_motor.setVoltage(ffOut);
    });

    // if the motor voltage is very small then shuffleboad boolean box turns red
    // (motors are off) otherwise it turns green (motors are on)

    new Trigger(
        () -> {
          return Math.abs(this.getMotorVoltage()) < EPSILON;
        }).onTrue(
            Commands.runOnce(
                () -> {
                  this.m_areMotorsRunningGenericEntry.setBoolean(false);
                }).ignoringDisable(true))
        .onFalse(
            Commands.runOnce(
                () -> {
                  this.m_areMotorsRunningGenericEntry.setBoolean(true);
                }).ignoringDisable(true));

    this.setDefaultCommand(this.m_setVoltageToFeedForwardCommand);
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

  public double getFFKS() {
    return this.m_feedForwardKSGenericEntry.getDouble(0.0);
  }

  public double getFFKG() {
    return this.m_feedForwardKGGenericEntry.getDouble(0.0);
  }

  public double getFFKV() {
    return this.m_feedForwardKVGenericEntry.getDouble(0.0);
  }

  public void updateFeedForward() {
    this.m_armFeedforward = new ArmFeedforward(this.getFFKS(), this.getFFKG(), this.getFFKV());
  }

  @Override
  public void periodic() {
    // Update shuffleboard
    this.m_voltageOutputGenericEntry.setDouble(this.getMotorVoltage());
    this.m_relativePositionOutputGenericEntry.setDouble(this.getArmPosition());
    this.m_relativePositionRawGenericEntry.setDouble(this.m_motor.getEncoder().getPosition());
    this.m_feedForwardOutputGenericEntry
        .setDouble(this.m_armFeedforward.calculate(this.m_feedForwardInputPositionGenericEntry.getDouble(0.0),
            this.m_feedForwardInputVelocityGenericEntry.getDouble(0.0)));
  }
}
