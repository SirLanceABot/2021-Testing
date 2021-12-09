package frc.robot;

// --------------------------------------------------------------------------- //
//     ONLY IMPORT ONE OF THE FOLLOWING PACKAGES                               //
//     DO NOT MAKE ANY OTHER CHANGES TO THIS FILE                              //
// --------------------------------------------------------------------------- //

//import frc.darren.*;
import frc.elliot.*;
//import frc.ishaan.*;
//import frc.jwood.*;


// --------------------------------------------------------------------------- //

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot
{
    private MyRobot myRobot = new MyRobot();

    public Robot()
    {
      
    }
    
    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit()
    {
        myRobot.robotInit();
    }

    /**
     * This function is called periodically.
     */
    @Override
    public void robotPeriodic()
    {
        myRobot.robotPeriodic();
    }

    /**
     * This function is run once each time the robot enters autonomous mode.
     */
    @Override
    public void autonomousInit()
    {
        myRobot.autonomousInit();
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic()
    {
        myRobot.autonomousPeriodic();
    }

    /**
     * This function is called once each time the robot enters teleoperated mode.
     */
    @Override
    public void teleopInit()
    {
        myRobot.teleopInit();
    }

    /**
     * This function is called periodically during teleoperated mode.
     */
    @Override
    public void teleopPeriodic()
    {
        myRobot.teleopPeriodic();
    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    @Override
    public void testInit()
    {
        myRobot.testInit();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic()
    {
        myRobot.testPeriodic();
    }

        /**
     * This function is called once each time the robot is disabled.
     */
    @Override
    public void disabledInit()
    {
        myRobot.disabledInit();
    }

    /**
     * This function is called periodically when the robot is disabled.
     */
    @Override
    public void disabledPeriodic()
    {
        myRobot.disabledPeriodic();
    }
}
