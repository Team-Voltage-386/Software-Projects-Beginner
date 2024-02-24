package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Timer;
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
  private GenericEntry m_motorAccelGenericEntry;
  private GenericEntry m_goalAccelMet;
  private CANSparkMax m_motor;
  private boolean m_askedToRunMotors;
  private Command m_motorRunCommand;
  private SlewRateLimiter m_slewRateLimiter;
  private static final double EPSILON = 0.001;

  public MotorSubsystem() {
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
    this.m_motorAccelGenericEntry = Shuffleboard.getTab(getName()).add("Motor Accel", 0.0).withPosition(3, 3)
        .withSize(2, 1).getEntry();
    this.m_goalAccelMet = Shuffleboard.getTab(getName()).add("has met accel", false).withPosition(3, 4).withSize(2, 1)
        .getEntry();
    this.m_motor = new CANSparkMax(Constants.Motor.kCANID, MotorType.kBrushless);
    this.m_motor.setIdleMode(IdleMode.kBrake);
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
    }).onTrue(Commands.runOnce(() -> this.incrementVoltageGenericEntry()).andThen(Commands.runOnce(() -> {
      this.m_incrementVoltageGenericEntry.setBoolean(false);
    })).ignoringDisable(true));

    new Trigger(() -> {
      return this.m_decrementVoltageGenericEntry.getBoolean(false);
    }).onTrue(Commands.runOnce(() -> this.decrementVoltageGenericEntry()).andThen(Commands.runOnce(() -> {
      this.m_decrementVoltageGenericEntry.setBoolean(false);
    })).ignoringDisable(true));
    m_slewRateLimiter = new SlewRateLimiter(40);
  }

  public double getVoltage() {
    double res = this.m_voltageGenericEntry.getDouble(0.0);
    if (Math.abs(res) < EPSILON) {
      return 0.0;
    } else {
      return res;
    }
  }

  // time, velo, accel
  double previousMotorData[] = { Timer.getFPGATimestamp(), 0, 0 };

  /**
   * @return returns acceleration of bottom motor
   */
  public void updateShooterAcceleration() {
    double[] now = { Timer.getFPGATimestamp(), m_motor.getEncoder().getVelocity(), 0 };
    if (now[1] != previousMotorData[1]) {
      double accel = (now[1] - previousMotorData[1]) / (now[0] -
          previousMotorData[0]);
      now[2] = m_slewRateLimiter.calculate(accel);
      previousMotorData = now;
    }
  }

  /**
   * @return returns if we have shot the note
   */
  public boolean hasShotNote() {
    // if (Flags.pieceState.equals(Flags.subsystemsStates.loadedPiece) && shoot
    if (previousMotorData[2] < -3) {
      return true;
    } else
      return false;
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

  @Override
  public void periodic() {
    updateShooterAcceleration();
    m_goalAccelMet.setBoolean(previousMotorData[2] < -3);
    m_voltageOutputGenericEntry.setDouble(this.getMotorVoltage());
    m_motorAccelGenericEntry.setDouble(this.previousMotorData[2]);
  }
}
