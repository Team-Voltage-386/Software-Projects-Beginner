package frc.robot.DiscoModeHandler;

import frc.robot.commands.WaitThenRunCommand;
import frc.robot.subsystems.LightSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class DiscoCollective {

    DiscoModeState modeState;
    DiscoLightState lightState;
    Timer timer;

    public DiscoCollective(DiscoModeState modeState, DiscoLightState lightState) { // Contstructor
        this.modeState = modeState;
        this.lightState = lightState;
        this.timer = new Timer();
        timer.start();
    }

    public Command discoCollective(DiscoModeState modeState, DiscoLightState lightState,
            LightSubsystem m_LightSubsystem) {
        // read modeState
        if (modeState.get() == ModeState.COLLECTIVE) {
            System.out.printf("Light State: %s\n", lightState.get().toString());
            // read lightState
            switch (lightState.get()) {
                // do something about it
                case INIT: {
                    return m_LightSubsystem.changeAllLEDColor(255, 255, 255).andThen(
                            new WaitThenRunCommand(() -> {
                                timer.reset();
                                lightState.set(LightState.RED);
                            }, () -> {
                                return timer.get() > 0.5;
                            }));
                }
                case RED: {
                    return m_LightSubsystem.changeAllLEDColor(255, 0, 0).andThen(
                            new WaitThenRunCommand(() -> {
                                timer.reset();
                                lightState.set(LightState.GREEN);
                            }, () -> {
                                return timer.get() > 0.5;
                            }));
                }
                case GREEN: {
                    return m_LightSubsystem.changeAllLEDColor(0, 255, 0).andThen(
                        new WaitThenRunCommand(() -> {
                            timer.reset();
                            lightState.set(LightState.BLUE);
                        }, () -> {
                            return timer.get() > 0.5;
                        }));
                }
                case BLUE: {
                    return m_LightSubsystem.changeAllLEDColor(0, 0, 255).andThen(
                            new WaitThenRunCommand(() -> {
                                timer.reset();
                                lightState.set(LightState.RED);
                            }, () -> {
                                return timer.get() > 0.5;
                            }));
                }
                default: {
                    return Commands.run(() -> {
                    });
                }
            }
        } else {
            return m_LightSubsystem.changeAllLEDColor(0, 0, 0);
        }

    }
}
