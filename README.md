# Hello beginners! Let's get started!

Note: when you want to open a sub-project folder that contains java code, open a copy of VS Code and open just the specific folder you are interested in (not the entire software-projects-beginner folder)

1. [Take the codecademy beginner java course.](./Codecademy-Course-Learn-Java/)
2. [Solve your first programming challenge at leetcode.](./Leetcode-2Sum-Easy)
3. [Compile Java locally and use the JShell.](./Compile-Locally-And-Use-JShell)
4. [Learn HashMap, references, and solve 2Sum faster.](./Learn-Java-Hashmap)
5. [Get VSCode+WPILib and write "Hello, world!" program.](./Get-VSCode-And-Run-Hello-World)
6. [Run the LED demo board example](./Run-The-LED-Demo-Board-Example)
7. See the [projects overview](#projects-overview) below for more projects!


# Projects Overview 
* `Basic_Motor_Control`
    * This is really a demo for students to get a feel for how a motor reacts to changes in voltage
    * Goal is for users can get an intuitive feel of how changes in voltage affect the speed of a motor.
    * Shuffleboard includes display of what the motor is currently doing as well as allowing the user to change the voltage.
* `Basic_Motor_Control_Arm_Feed_Forward_With_PID`
    * This is a continuation project of `Basic_Motor_Control_Arm_FeedForward_Tuning`
    * In this project, we build on the feed forward tuning we worked on in `Basic_Motor_Control_Arm_FeedForward_Tuning` and add in PID tuning
    * This project was created for the arm on a stick connected to the Demo board
    * Don't forget to update the CAN ID in the code to the SparkMax you connect the arm to!
    * The goal is to get the arm to move to a provided position using feed forward to overcome known disturbances like gravity and PID to quickly reduce the error in current position to target position to 0 without over or under shooting.
* `Basic_Motor_Control_Arm_FeedForward_Tuning`
    * This project was created for the arm on a stick connected to the Demo board
    * The goal is to get the arm to move from hanging down to parallel to the floor using Feed Forward to compensate for friction in the system and gravity.
* `Codecademy-Course-Learn-Java`
    * Beginner code academy course in Java
* `Compile-Locally-And-Use-JShell`
    * Compile java locally and use the JShell
* `Get-VSCode-And-Run-Hello-World`
    * Basic example project for downloading vscode and run hello world in Java
* `Learn-about-Cameras`
    * This project was an exploration in using cameras. Specifically, we explored how to subscribe to multiple cameras mounted on the demo-board and switch between them as seamlessly as possible.
    * There's also documentation about tuning cameras to optimize for Frames-per-second (FPS)
* `Learn-about-Rumble`
    * A demo project for exploring how to rumble the controllers and how that rumble feels while holding the controller.
    * This project includes controls on Shuffleboard to quickly try out different rumble intensity in the controllers and includes some demo code that students can use to learn about rumbling controllers.
* `Learn-Java-Hashmap`
    * Learn about Java's Hashmap
* `LEDDemoboardExample`
    * An example project about how to use LEDs on the demo-board.
* `Leetcode-2Sum-Easy`
    * Solve your first programming challenge at leetcode.
* `Reboot2023`
    * A beginner project where we started to rebuild the code for the 2023 robot. Students should branch off of the `implement_Reboot_2023` branch to try implementing this code themselves. The code is here in main as the "answer".
    * Currently, this only includes the LED subystem and includes some examples for setting ranges of LEDs to highlight different parts of the robot. One possible continuation of the project is take to make the front light up when the robot is driving forward and the back light up when the robot is driving backward.
    * This code was tested on the 2023 robot.
* `Run-The-LED-Demo-Board-Example`
    * Run the LED demo board example.  This code should run on either the demo board or the briefcase bot with the only difference being the number of LEDs.
* `TeamVoltage386_Briefcase_Robot`
    * Project used when working with the Briefcase Robot. It seems to be in its very early stages.
