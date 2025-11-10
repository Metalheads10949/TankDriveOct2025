// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the manifest file in the resource directory.
 */
public class Robot extends TimedRobot {
  private final PWMTalonSRX m_leftLeaderDrive = new PWMTalonSRX(0);
  private final PWMTalonSRX m_rightLeaderDrive = new PWMTalonSRX(1);
  private final PWMTalonSRX m_leftFollowerDrive = new PWMTalonSRX(2);
  private final PWMTalonSRX m_rightFollowerDrive = new PWMTalonSRX(3);

  private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(m_leftLeaderDrive::set, m_rightLeaderDrive::set);

  private final Joystick m_controller = new Joystick(0);

  private final Timer m_timer = new Timer();

  /** Called once at the beginning of the robot program. */
  public Robot() {
    SendableRegistry.addChild(m_robotDrive, m_leftLeaderDrive);
    SendableRegistry.addChild(m_robotDrive, m_rightLeaderDrive);

    m_leftLeaderDrive.addFollower(m_leftFollowerDrive);
    m_rightLeaderDrive.addFollower(m_rightFollowerDrive);

    m_leftLeaderDrive.setInverted(false);
    m_rightLeaderDrive.setInverted(true);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    //m_robotDrive.arcadeDrive(-m_controller.getRawAxis(1), -m_controller.getRawAxis(0));
    double speed = -m_controller.getRawAxis(1);
    double turn = m_controller.getRawAxis(0);

    double left = speed + turn;
    double right = speed - turn;

    m_leftLeaderDrive.set(left);
    m_rightLeaderDrive.set(right);
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called perioinvedically during test mode. */
  @Override
  public void testPeriodic() {}
}
