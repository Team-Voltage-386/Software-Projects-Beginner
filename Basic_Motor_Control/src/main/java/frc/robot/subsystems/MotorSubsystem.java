package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.commands.AddMotorVoltageCommand;
import frc.robot.commands.MotorRunCommand;
import frc.robot.commands.RemoveMotorVoltageCommand;

public class MotorSubsystem extends SubsystemBase {
  private GenericEntry m_voltageGenericEntry;
  private GenericEntry m_incDecAmountGenericEntry;
  private GenericEntry m_incrementVoltageGenericEntry;
  private GenericEntry m_decrementVoltageGenericEntry;
  private GenericEntry m_doRunMotorsGenericEntry;
  private GenericEntry m_areMotorsRunningGenericEntry;
  private CANSparkMax m_motor;
  private boolean m_askedToRunMotors;
  private Command m_motorRunCommand;
  private static final double EPSILON = 0.001;

  public MotorSubsystem() {
    this.m_voltageGenericEntry = Shuffleboard.getTab(getName()).add("Voltage", 0.0).withPosition(3, 0).withSize(2, 1)
        .getEntry();
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
    this.m_motor = new CANSparkMax(Constants.Motor.kCANID, MotorType.kBrushless);
    this.m_askedToRunMotors = false;
    this.m_motorRunCommand = new MotorRunCommand(this);

    new Trigger(() -> this.m_askedToRunMotors).and(this::doRunMotor)
        .whileTrue(m_motorRunCommand).onFalse(runOnce(() -> {
          this.setMotorVoltage(0.0);
        }).ignoringDisable(true));

    new Trigger(() -> {
      return Math.abs(this.getMotorVoltage()) < EPSILON;
    }).onTrue(Commands.runOnce(() -> {
      this.m_areMotorsRunningGenericEntry.setBoolean(false);
    }).ignoringDisable(true)).onFalse(Commands.runOnce(() -> {
      this.m_areMotorsRunningGenericEntry.setBoolean(true);
    }).ignoringDisable(true));

    new Trigger(() -> {
      return this.m_incrementVoltageGenericEntry.getBoolean(false);
    }).onTrue(new AddMotorVoltageCommand(this).andThen(Commands.runOnce(() -> {
      this.m_incrementVoltageGenericEntry.setBoolean(false);
    })).ignoringDisable(true));

    new Trigger(() -> {
      return this.m_decrementVoltageGenericEntry.getBoolean(false);
    }).onTrue(new RemoveMotorVoltageCommand(this).andThen(Commands.runOnce(() -> {
      this.m_decrementVoltageGenericEntry.setBoolean(false);
    })).ignoringDisable(true));
  }

  public double getVoltage() {
    double res = this.m_voltageGenericEntry.getDouble(0.0);
    if (Math.abs(res) < EPSILON) {
      return 0.0;
    } else {
      return res;
    }
  }

  public boolean doRunMotor() {
    return this.m_doRunMotorsGenericEntry.getBoolean(false);
  }

  public double getMotorVoltage() {
    return this.m_motor.getAppliedOutput();
  }

  public void setMotorVoltage(double voltage) {
    this.m_motor.setVoltage(voltage);
  }

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
}
