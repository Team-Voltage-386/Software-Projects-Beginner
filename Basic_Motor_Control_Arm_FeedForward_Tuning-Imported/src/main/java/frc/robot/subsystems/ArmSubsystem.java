package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.*;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PubSubOption;
//import edu.wpi.first.networktables.PubSubOptions;
//import edu.wpi.first.networktables.StringPublisher;
//import edu.wpi.first.networktables.DoubleSubscriber;
//import edu.wpi.first.networktables.IntegerPublisher;
//import edu.wpi.first.networktables.BooleanEntry;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {

  private final NetworkTable table;
  private DoublePublisher m_displayVoltage;
  private DoublePublisher m_displayRelativePosition;
  private DoublePublisher m_displayRelativePositionRaw;
  private BooleanPublisher m_areMotorsRunningBoolean;
  private DoubleEntry m_feedForwardKSEntry;
  private DoubleEntry m_feedForwardKGEntry;
  private DoubleEntry m_feedForwardKVEntry;
  private DoublePublisher m_displayFeedForwardOutput;
  private DoublePublisher m_displayFeedForwardActualOutput;
  private DoubleEntry m_feedForwardInputPositionEntry;
  private DoubleEntry m_feedForwardInputVelocityEntry;

  private SparkMax m_motor;
  private ArmFeedforward m_armFeedforward;
  private Command m_setVoltageToFeedForwardCommand;
  private static final double EPSILON = 0.001;

  public ArmSubsystem(NetworkTableInstance nt) {
    table = nt.getTable(getName());
    // Dashboard objects
    // Informational objects
    m_displayVoltage = table.getDoubleTopic("Output Voltage").publish();
    m_displayRelativePosition = table.getDoubleTopic("Output Rel Enc Position").publish();
    m_displayRelativePositionRaw = table.getDoubleTopic("Raw Rel Enc Position").publish();
    m_areMotorsRunningBoolean = table.getBooleanTopic("Are motors running?").publish();

    // Objects to be changed by the user
    m_feedForwardKSEntry = table.getDoubleTopic("FF KS").getEntry(0.0, PubSubOption.sendAll(true));
    m_feedForwardKSEntry.set(0.0);
    m_feedForwardKGEntry = table.getDoubleTopic("FF KG").getEntry(0.0, PubSubOption.sendAll(true));
    m_feedForwardKGEntry.set(0.0);
    m_feedForwardKVEntry = table.getDoubleTopic("FF KV").getEntry(0.0, PubSubOption.sendAll(true));
    m_feedForwardKVEntry.set(0.0);

    // Actual output
    m_displayFeedForwardActualOutput = table.getDoubleTopic("FF Actual Output").publish();

    // User provides position and speed and the FF controller will calculate the FF
    // Output that it would supply
    m_feedForwardInputPositionEntry = table.getDoubleTopic("FF Input Pos Rad").getEntry(0.0, PubSubOption.sendAll(true));
    m_feedForwardInputPositionEntry.set(0.0);
    m_feedForwardInputVelocityEntry = table.getDoubleTopic("FF Input Vel Rad_s").getEntry(0.0, PubSubOption.sendAll(true));
    m_feedForwardInputVelocityEntry.set(0.0);
    m_displayFeedForwardOutput = table.getDoubleTopic("FF Output").getEntry(0.0, PubSubOption.sendAll(true));
    m_displayFeedForwardOutput.set(0.0);

    // There is one motor
    this.m_motor = new SparkMax(Constants.Motor.kCANID, MotorType.kBrushless);
    // coast mode means we have to fight gravity (the motor won't help hold the position)
    
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
      this.m_displayFeedForwardActualOutput.set(ffOut);
      this.m_motor.setVoltage(ffOut);
    });

    // if the motor voltage is very small then dashboard boolean box turns red
    // (motors are off) otherwise it turns green (motors are on)

    new Trigger(
        () -> {
          return Math.abs(this.getMotorVoltage()) < EPSILON;
        }).onTrue(
            Commands.runOnce(
                () -> {
                  this.m_areMotorsRunningBoolean.set(false);
                }).ignoringDisable(true))
        .onFalse(
            Commands.runOnce(
                () -> {
                  this.m_areMotorsRunningBoolean.set(true);
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
    return this.m_feedForwardKSEntry.get(0.0);
  }

  public double getFFKG() {
    return this.m_feedForwardKGEntry.get(0.0);
  }

  public double getFFKV() {
    return this.m_feedForwardKVEntry.get(0.0);
  }

  public void updateFeedForward() {
    this.m_armFeedforward = new ArmFeedforward(this.getFFKS(), this.getFFKG(), this.getFFKV());
  }

  @Override
  public void periodic() {
    // Update dashboard
    this.m_displayVoltage.set(this.getMotorVoltage());
    this.m_displayRelativePosition.set(this.getArmPosition());
    this.m_displayRelativePositionRaw.set(this.m_motor.getEncoder().getPosition());
    this.m_displayFeedForwardOutput
        .set(this.m_armFeedforward.calculate(this.m_feedForwardInputPositionEntry.get(0.0),
            this.m_feedForwardInputVelocityEntry.get(0.0)));
  }
}
