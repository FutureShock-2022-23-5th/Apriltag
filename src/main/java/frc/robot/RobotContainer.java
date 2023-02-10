// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.JoystickConstants;
import frc.robot.commands.ApriltagCommand;
import frc.robot.subsystems.ApriltagSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

private final UsbCamera cam1 = CameraServer.startAutomaticCapture(DriveConstants.kCamera1Port);
 
private ApriltagSubsystem m_apriltagSub = new ApriltagSubsystem();
private DriveSubsystem m_driveSub = new DriveSubsystem();

private ApriltagCommand m_apriltagCmd = new ApriltagCommand(m_apriltagSub, m_driveSub, 1);

// Joystick
private final Joystick driverJoystick = new Joystick(0);

/** The container for the robot. Contains subsystems, OI devices, and commands. */
public RobotContainer() {
  // Configure the trigger bindings
  configureBindings();
}

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    m_driveSub.setDefaultCommand(new RunCommand(() -> {
      m_driveSub.arcadeDrive(
        -driverJoystick.getRawAxis(JoystickConstants.leftStick_Y), 
        driverJoystick.getRawAxis(JoystickConstants.rightStick_X));
    }
    , m_driveSub));

    new JoystickButton(driverJoystick, JoystickConstants.btn_A)
      .onTrue( new RunCommand( () -> { m_driveSub.resetEncoder();}, m_driveSub));

    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ApriltagCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_apriltagCmd;
  }
}
