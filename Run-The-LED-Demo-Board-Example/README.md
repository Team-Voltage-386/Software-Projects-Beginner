---
typora-copy-images-to: ./assets
---

In this tutorial, we're going to send something to the "briefcase bot", which is a RoboRio (the brain our robots use), some peripherals and specifically, the strip of LED lights!

We can also use this same process to control the LEDs on the Demo Board.  Talk to a mentor regarding which "robot" you'll be using for this exercise.

## Step 1: Get The Code!

If you have not already done so, install Git on your PC:  https://git-scm.com/downloads

Visit the Repository where the code resides on GitHub https://github.com/Team-Voltage-386/Software-Projects-Beginner

You have several options for how to get the code on your PC. The concepts are the same. A copy of the code from GitHub will be put on your PC. For example, you can use the GitHub Desktop option which will prompt you for a location on your PC.

Here's how it can be done from within VS Code. 
Click the Code button and then Copy the URL

![image](https://github.com/user-attachments/assets/68164645-f90c-4cd8-83dc-b792e33b1e49)


Open VS Code. 

Press Ctrl-Shift-P (or CMD-Shift-P on a Mac)


![image](https://github.com/user-attachments/assets/9d54718a-d342-4194-a7f4-5fbad698e86d)


Paste in the URL you just copied.
It will ask you to choose a folder on your PC to put your local copy of the repo you are cloning.
Regardless of whether you use VS Code, GitHub Desktop or a downloaded Zip file to clone the repo, be aware of the following issue!
If you are using Windows, the "Public Documents" folder is a good choice.

![image](https://github.com/user-attachments/assets/0d7e3e9e-448f-4cc4-a64d-c671bd8511fb)


Ask a mentor if you need help.

## Step 2: Open the project in VSCode!

In VSCode.

File -> Open Folder and navigate to the `LEDDemoBoardExample` folder within the `Software-Projects-Beginner-main` folder which you just cloned to your PC

![image](https://github.com/user-attachments/assets/2f3ef6cc-f5a1-4419-a21f-b746feaf4757)

Choose to trust the authors:

![image-20240118234456326](./assets/image-20240118234456326.png)

If prompted, choose to update the project with the current version of the extension:

![image-20240118234530923](./assets/image-20240118234530923.png)

Now navigate, using the filesystem explorer view on the left, to the source files:

![image-20240118235226656](./assets/image-20240118235226656.png)

## Step 3: Build The Project!

Open the Command Pallette (Ctrl-Shif-P or Cmd-Shif-P)  or Click the WPI icon.

Select the option "WPILIB: Build Robot Code"

Note - sometimes VS Code will automatically build the code when you open the folder

![image-20240118235235482](./assets/image-20240118235235482.png)

Hopefully you get this:

![image-20240118235250990](./assets/image-20240118235250990.png)

If not, ask a mentor for help.

## Step 4: Connect to the Briefcase Bot

Plug it in, give it a moment to broadcast its SSID, then associate your laptop:

![image-20240119003938325](./assets/image-20240119003938325.png)

Now use the WPILib menu to deploy robot code:

![image-20240119004021907](./assets/image-20240119004021907.png)

...TO BE CONTINUED!
