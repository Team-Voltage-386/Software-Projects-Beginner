This is the read me for the Disco Mode.

    Contents;
    
        Variables;

            LightState;
                LightState lightState
                    The enum variable that determines which color to switch to.

                lightstate.get()
                    Retrieves which state the enum is in.

                lightState.set()
                    Sets the enum variable. You shouldn't have to use this unless you want a specific color to start with. The Organizer handles this.

            ModeState
                ModeState modeState
                    The enum variable that determines which mode runs.
            
                modeState.get()
                    Retrieves the mode currently enabled.

                modeState.set()
                    Sets the enum variable. You shouldn't have to use this at all, really.

        