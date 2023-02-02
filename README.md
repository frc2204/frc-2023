# frc-2023
[![CI](https://github.com/FRC2204-Rambots/frc-2023/actions/workflows/main.yml/badge.svg)](https://github.com/FRC2204-Rambots/frc-2023/actions/workflows/main.yml)

This is the code for the 2023 season of the FIRST Robotics Competition.
It is written in Kotlin with Java interoperability and uses the WPILib library.

# Robot Features
## Drivetrain
The drivetrain this year features 4 West Coast Products Swerve X modules, in the flipped Gears Below configuration.
Each module is controlled with 2 Talon 500 motors along with a CTRE Mag Encoder (CANCoder) for swerve module angle.


## Requirements
In order to build and deploy the code, you must have the WPILib toolchain installed. You can find instructions for installing the toolchain [here](https://docs.wpilib.org/en/latest/docs/zero-to-robot/step-2/wpilib-setup.html).

### IntelliJ IDEA
Intellisense within the native WPI VSCode is limited, and therefore our team has opted to use IntelliJ IDEA, featuring
better support for Kotlin and WPILib. You can download it [here](https://www.jetbrains.com/idea/download/) along with
the FRC plugin [here](https://plugins.jetbrains.com/plugin/9405-frc).

## Build and Deploy
In the `.run` directory, there are two run configurations for IntelliJ IDEA. One is for building the code and the other is for deploying the code to the robot. You can run these configurations by clicking the green arrow next to the configuration name.

For WPI VS Code, you can build the code by running `./gradlew build` in the terminal. You can deploy the code by running `./gradlew deploy` in the terminal.