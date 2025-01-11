package frc.robot.subsystems;

// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.commands.MotorRunCommand;

public class MotorSubsystem extends SubsystemBase {
  private GenericEntry m_voltageGenericEntry;
  private GenericEntry m_voltageOutputGenericEntry;
  private GenericEntry m_incDecAmountGenericEntry;
  private GenericEntry m_incrementVoltageGenericEntry;
  private GenericEntry m_decrementVoltageGenericEntry;
  private GenericEntry m_doRunMotorsGenericEntry;
  private GenericEntry m_areMotorsRunningGenericEntry;
  private SparkMax m_motor;
  private boolean m_askedToRunMotors;
  private Command m_motorRunCommand;
  private static final double EPSILON = 0.001;

  public MotorSubsystem() {
    // Set up Shuffleboard
    this.m_voltageGenericEntry = Shuffleboard.getTab(getName()).add("Input Voltage", 0.0).withPosition(3, 0)
        .withSize(2, 1)
        .getEntry();
    this.m_voltageOutputGenericEntry = Shuffleboard.getTab(getName()).add("Output Voltage", 0.0).withPosition(0, 0)
        .withSize(2, 1).getEntry();
    this.m_incDecAmountGenericEntry = Shuffleboard.getTab(getName()).add("Inc_Dec Amount", 0.1).withPosition(6, 1)
        .withSize(1, 1).getEntry();
    this.m_incrementVoltageGenericEntry = Shuffleboard.getTab(getName()).add("Inc", false).withPosition(4, 1)
        .withSize(1, 1).withWidget(BuiltInWidgets.kToggleButton).getEntry();
    this.m_decrementVoltageGenericEntry = Shuffleboard.getTab(getName()).add("Dec", false).withPosition(3, 1)
        .withSize(1, 1).withWidget(BuiltInWidgets.kToggleButton).getEntry();
    this.m_doRunMotorsGenericEntry = Shuffleboard.getTab(getName()).add("Run Motors?", false).withPosition(0, 2)
        .withSize(2, 1)
        .withWidget(BuiltInWidgets.kToggleButton).getEntry();
    this.m_areMotorsRunningGenericEntry = Shuffleboard.getTab(getName()).add("Are motors running?", false)
        .withPosition(0, 3).withSize(2, 1).withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    // Set up motor state
    this.m_motor = new SparkMax(Constants.Motor.kCANID, MotorType.kBrushless);
    this.m_askedToRunMotors = false;
    this.m_motorRunCommand = new MotorRunCommand(this);

    // when the robot is not disabled and you've clicked the button on shuffleboard
    // to run the motors, the motor voltage is set based on the value on
    // shuffleboard
    // otherwise, the voltage is set to zero

    // Note that the voltage is set to zero when the robot is disabled
    new Trigger(() -> this.m_askedToRunMotors).and(this::doRunMotor)
        .whileTrue(m_motorRunCommand).onFalse(
            runOnce(
                () -> {
                  this.setMotorVoltage(0.0);
                }).ignoringDisable(true));

    // sets a visual indicator on shuffleboard when the motor voltage is greater
    // than EPSILON
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

    // When the increment voltage button is pressed, the voltage is incremented
    //
    // Note: In shuffleboard buttons are toggles, i.e press once and it turns on.
    // Press again to turn it off. This trigger makes it so that press once it turns
    // on (and increments) and then it turns it off automatically so the next time
    // you press the button it'll increment again.

    new Trigger(() -> {
      return this.m_incrementVoltageGenericEntry.getBoolean(false);
    }).onTrue(Commands.runOnce(() -> this.incrementVoltageGenericEntry()).andThen(Commands.runOnce(() -> {
      this.m_incrementVoltageGenericEntry.setBoolean(false);
    })).ignoringDisable(true));

    // Decrement the voltage (see comment above)
    new Trigger(() -> {
      return this.m_decrementVoltageGenericEntry.getBoolean(false);
    }).onTrue(Commands.runOnce(() -> this.decrementVoltageGenericEntry()).andThen(Commands.runOnce(() -> {
      this.m_decrementVoltageGenericEntry.setBoolean(false);
    })).ignoringDisable(true));
  }

  /**
   * reads the voltage entered by the user
   * 
   * @return the voltage. If the entered voltage is less than EPSILON it returns
   *         zero
   */
  public double getVoltage() {
    double res = this.m_voltageGenericEntry.getDouble(0.0);
    if (Math.abs(res) < EPSILON) {
      return 0.0;
    } else {
      return res;
    }
  }

  /**
   * Checks shuffleboard button to start the motor
   * 
   * @return the state of the button
   */
  public boolean doRunMotor() {
    return this.m_doRunMotorsGenericEntry.getBoolean(false);
  }

  /**
   * this will read the actual voltage that the motor reports which may be
   * slightly different from the voltage requested.
   * 
   * @return
   */
  public double getMotorVoltage() {
    return this.m_motor.getAppliedOutput();

  }

  /**
   * Sets the voltage of the motor
   * 
   * @param voltage
   */
  public void setMotorVoltage(double voltage) {
    this.m_motor.setVoltage(voltage);
  }

  /**
   * reads the value from shuffleboard and ensures it's between 0-1 (in order to
   * limit how much the user can increment the voltage)
   * 
   * @return the clamped value
   */
  public double getAndUpdateIncDecAmount() {
    double val = this.m_incDecAmountGenericEntry.getDouble(0.0);
    double clampedVal = MathUtil.clamp(val, 0.0, 1.0);
    this.m_incDecAmountGenericEntry.setDouble(clampedVal);
    return clampedVal;
  }

  public void incrementVoltageGenericEntry() {
    this.m_voltageGenericEntry.setDouble(this.getVoltage() + this.getAndUpdateIncDecAmount());
  }

  public void decrementVoltageGenericEntry() {
    this.m_voltageGenericEntry.setDouble(this.getVoltage() - this.getAndUpdateIncDecAmount());
  }

  public void startMotor() {
    this.m_askedToRunMotors = true;
  }

  public void stopMotor() {
    this.m_askedToRunMotors = false;
  }

  /**
   * updates the shuffleboard object with the motor voltage
   */
  @Override
  public void periodic() {
    m_voltageOutputGenericEntry.setDouble(this.getMotorVoltage());
  }
}
