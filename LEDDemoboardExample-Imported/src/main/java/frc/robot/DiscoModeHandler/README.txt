This is the read me for the Disco Mode.

    Contents;
    
        Variables;

            LightState;
                LightState lightState
                    The enum variable that determines which color to switch to.
                        Consists of INIT, RED, GREEN, and BLUE

                lightstate.get()
                    Retrieves which state the enum is in.

                lightState.set()
                    Sets the enum variable. You shouldn't have to use this unless you want a specific color to start with. The Organizer handles this.

            ModeState
                ModeState modeState
                    The enum variable that determines which mode runs.
                        Consists of COLLECTIVE, SEQUENTIAL, and RAINBOW
            
                modeState.get()
                    Retrieves the mode currently enabled.

                modeState.set()
                    Sets the enum variable. You shouldn't have to use this at all, really.


Contact Jake for more info.

TODO: 
    *Write INIT for all modes
    *Write COLLECTIVE modeState
    *Write SEQUENTIAL modeState
    *Write RAINBOW modeState
    *Finish Organizer