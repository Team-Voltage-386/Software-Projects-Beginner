# LED Demo Example

## Required Constants
* `subsystems/LightSubsystem.java`: kLedPort
    * Set it = 1 for the briefcase bot
    * Set it = 4 for the Demoboard
* `subsystems/LightSubsystem.java`: kLedLength
    * Set it = 10 for the briefcase bot
    * Set it = 76 for the Demoboard
* `Constants.java`: kDriverControllerPort
    * DriverStation check what USB Order for what number it should be. Usually either 0 or 1.

## What it does
First, enable the robot:
* When you press the `B` button, the light strip is set to Red.
* When you press the `A` button, the light strip is turned off.

If disabled:
* The light strip is turned off.
