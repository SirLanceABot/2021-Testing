package frc.elliot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

public class MyRobot
{
    private static Xbox xbox = new Xbox(0);

    private static final int frontLeftDrivePort = 7;
    private static final int frontLeftEncoderPort = 8;
    private static final int frontLeftTurnPort = 9;
    private static final int frontRightDrivePort = 10;
    private static final int frontRightEncoderPort = 11;
    private static final int frontRightTurnPort = 12;
    private static final int backLeftDrivePort = 4;
    private static final int backLeftEncoderPort = 5;
    private static final int backLeftTurnPort = 6;
    private static final int backRightDrivePort = 1;
    private static final int backRightEncoderPort = 2;
    private static final int backRightTurnPort = 3;

    private final WPI_TalonFX frontLeftDrive = new WPI_TalonFX(frontLeftDrivePort);
    private final WPI_TalonFX frontLeftTurn = new WPI_TalonFX(frontLeftTurnPort);
    private final WPI_TalonFX frontRightDrive = new WPI_TalonFX(frontRightDrivePort);
    private final WPI_TalonFX frontRightTurn = new WPI_TalonFX(frontRightTurnPort);
    private final WPI_TalonFX backLeftDrive = new WPI_TalonFX(backLeftDrivePort);
    private final WPI_TalonFX backLeftTurn = new WPI_TalonFX(backLeftTurnPort);
    private final WPI_TalonFX backRightDrive = new WPI_TalonFX(backRightDrivePort);
    private final WPI_TalonFX backRightTurn = new WPI_TalonFX(backRightTurnPort);

    private final CANCoder frontLeftEncoder = new CANCoder(frontLeftEncoderPort);
    private final CANCoder frontRightEncoder = new CANCoder(frontRightEncoderPort);
    private final CANCoder backLeftEncoder = new CANCoder(backLeftEncoderPort);
    private final CANCoder backRightEncoder = new CANCoder(backRightEncoderPort);

    private final NavX gyro = NavX.getInstance();

    // private final double frontLeftEncoderZero = 168.57421875;
    // private final double frontRightEncoderZero = 304.541015625;
    // private final double backLeftEncoderZero = 102.392578125;
    // private final double backRightEncoderZero = 349.27734375;

    //moves zero to the right side of the robot so trigonometric bearings can be used
    private final double frontLeftEncoderZero = 166.904296875 - 90.0;
    private final double frontRightEncoderZero = 305.244140625 - 90.0;
    private final double backLeftEncoderZero = 349.453125 - 90.0;
    private final double backRightEncoderZero = 103.447265625 - 90.0;

    private final double encoderZeroThreshold = 0.5;

    private final double kP = 0.05;

    public MyRobot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** ELLIOT's Test Code ***");

        frontLeftDrive.setInverted(true);
        backLeftDrive.setInverted(true);

        xbox.setAxisIsFlipped(Xbox.Axis.kLeftY, true);
        xbox.setAxisIsFlipped(Xbox.Axis.kRightY, true);

        System.out.println(this.getClass().getName() + " : Finished Constructor");
    }

    public void robotInit()
    {

    }

    public void robotPeriodic()
    {
        
    }

    public void autonomousInit()
    {

    }

    public void autonomousPeriodic()
    {

    }

    public void teleopInit()
    {
        
    }

    public void teleopPeriodic()
    {
        //only outputs encoder data once per button press
        boolean xButtonFlag = true;

        boolean frontLeftIsInverted = false;
        boolean frontRightIsInverted = false;
        boolean backLeftIsInverted = false;
        boolean backRightIsInverted = false;

        double maxDriveSpeedVoltage = 5.0;
        double maxTurnSpeedVoltage = 3.0;

        double maxDriveSpeed = 0.6;
        double maxTurnSpeed = 0.8;

        double minDriveSpeed = 0.1;
        double minTurnSpeed = 0.06;

        //when you let the joystick go suddenly and it snaps to the center, don't want it to overshoot and give wrong values
        double snapBackRadius = 0.1;

        //values on the xbox controller for the left stick
        double leftXValue = xbox.getRawAxis(Xbox.Axis.kLeftX);
        double leftYValue = xbox.getRawAxis(Xbox.Axis.kLeftY);

        // quick fix for when code needs to be tested bu tyou don't want the robot to move
        // leftXValue = 0.0;
        // leftYValue = 0.0;

        //hypotenuse of left stick x and y values, should be a double between -1.0 and 1.0
        double desiredDriveSpeed = Math.sqrt(Math.pow(leftXValue, 2) + Math.pow(leftYValue, 2));

        //inverse tangent of left stick x and y values, value between 0.0 and 360.0, not including 360.0
        double desiredTurnAngle = Math.atan(leftYValue / leftXValue) * 180 / Math.PI; //trigonometric bearing

        //rules of arctangent
        if (leftXValue < 0.0)
        {
            desiredTurnAngle += 180;
        }

        if (desiredTurnAngle < 0.0)
        {
            desiredTurnAngle += 360.0;
        }

        // System.out.println(desiredTurnAngle);

        double frontLeftTurnPosition = frontLeftEncoder.getAbsolutePosition() - frontLeftEncoderZero;
        double frontRightTurnPosition = frontRightEncoder.getAbsolutePosition() - frontRightEncoderZero;
        double backLeftTurnPosition = backLeftEncoder.getAbsolutePosition() - backLeftEncoderZero;
        double backRightTurnPosition = backRightEncoder.getAbsolutePosition() - backRightEncoderZero;

        //if angles are negative, find the first positive coterminal angle
        //these angles are the current position of each wheel on the robot
        //angles are in trigonometric form, as on a unit circle (right is 0 degrees, forward is 90 degrees)
        if (frontLeftTurnPosition < 0.0)
        {
            frontLeftTurnPosition += 360.0;
        }
        if (frontRightTurnPosition < 0.0)
        {
            frontRightTurnPosition += 360.0;
        }
        if (backLeftTurnPosition < 0.0)
        {
            backLeftTurnPosition += 360.0;
        }
        if (backRightTurnPosition < 0.0)
        {
            backRightTurnPosition += 360.0;
        }

        double frontLeftDifference = desiredTurnAngle - frontLeftTurnPosition;
        double frontRightDifference = desiredTurnAngle - frontRightTurnPosition;
        double backLeftDifference = desiredTurnAngle - backLeftTurnPosition;
        double backRightDifference = desiredTurnAngle - backRightTurnPosition;

        //System.out.println("Angle Difference: " + frontLeftDifference);

        //never try darren's code because this code works
        if (Math.abs(frontLeftDifference) > 180.0)
        {
            frontLeftDifference = (360.0 - Math.abs(frontLeftDifference)) * -Math.signum(frontLeftDifference);
        }
        if (Math.abs(frontRightDifference) > 180.0)
        {
            frontRightDifference = (360.0 - Math.abs(frontRightDifference)) * -Math.signum(frontRightDifference);
        }
        if (Math.abs(backLeftDifference) > 180.0)
        {
            backLeftDifference = (360.0 - Math.abs(backLeftDifference)) * -Math.signum(backLeftDifference);
        }
        if (Math.abs(backRightDifference) > 180.0)
        {
            backRightDifference = (360.0 - Math.abs(backRightDifference)) * -Math.signum(backRightDifference);
        }

        //System.out.println("Optimized Angle Difference (front left wheel): " + frontLeftDifference);

        double frontLeftInvertedDifference = (Math.abs(frontLeftDifference) - 180.0) * Math.signum(frontLeftDifference);
        double frontRightInvertedDifference = (Math.abs(frontRightDifference) - 180.0) * Math.signum(frontRightDifference);
        double backLeftInvertedDifference = (Math.abs(backLeftDifference) - 180.0) * Math.signum(backLeftDifference);
        double backRightInvertedDifference = (Math.abs(backRightDifference) - 180.0) * Math.signum(backRightDifference);

        double frontLeftTurnPath = frontLeftDifference;
        double frontRightTurnPath = frontRightDifference;
        double backLeftTurnPath = backLeftDifference;
        double backRightTurnPath = backRightDifference;

        if (Math.abs(frontLeftInvertedDifference) < Math.abs(frontLeftDifference))
        {
            frontLeftTurnPath = frontLeftInvertedDifference;

            frontLeftIsInverted = true;
        }
        else
        {
            frontLeftIsInverted = false;
        }
        if (Math.abs(frontRightInvertedDifference) < Math.abs(frontRightDifference))
        {
            frontRightTurnPath = frontRightInvertedDifference;

            frontRightIsInverted = true;
        }
        else
        {
            frontRightIsInverted = false;
        }
        if (Math.abs(backLeftInvertedDifference) < Math.abs(backLeftDifference))
        {
            backLeftTurnPath = backLeftInvertedDifference;

            backLeftIsInverted = true;
        }
        else
        {
            backLeftIsInverted = false;
        }
        if (Math.abs(backRightInvertedDifference) < Math.abs(backRightDifference))
        {
            backRightTurnPath = backRightInvertedDifference;

            backRightIsInverted = true;
        }
        else
        {
            backRightIsInverted = false;
        }

        double frontLeftTurnSpeed = (Math.abs((maxTurnSpeed - minTurnSpeed) * maxTurnSpeed * frontLeftTurnPath / 180.0) + minTurnSpeed) * Math.signum(frontLeftTurnPath);
        double frontRightTurnSpeed = (Math.abs((maxTurnSpeed - minTurnSpeed) * maxTurnSpeed * frontRightTurnPath / 180.0) + minTurnSpeed) * Math.signum(frontRightTurnPath);
        double backLeftTurnSpeed = (Math.abs((maxTurnSpeed - minTurnSpeed) * maxTurnSpeed * backLeftTurnPath / 180.0) + minTurnSpeed) * Math.signum(backLeftTurnPath);
        double backRightTurnSpeed = (Math.abs((maxTurnSpeed - minTurnSpeed) * maxTurnSpeed * backRightTurnPath / 180.0) + minTurnSpeed) * Math.signum(backRightTurnPath);

        if (Math.abs(frontLeftTurnSpeed) < minTurnSpeed)
        {
            frontLeftTurnSpeed = minTurnSpeed * Math.signum(frontLeftTurnSpeed);
        }
        if (Math.abs(frontRightTurnSpeed) < minTurnSpeed)
        {
            frontRightTurnSpeed = minTurnSpeed * Math.signum(frontRightTurnSpeed);
        }
        if (Math.abs(backLeftTurnSpeed) < minTurnSpeed)
        {
            backLeftTurnSpeed = minTurnSpeed * Math.signum(backLeftTurnSpeed);
        }
        if (Math.abs(backRightTurnSpeed) < minTurnSpeed)
        {
            backRightTurnSpeed = minTurnSpeed * Math.signum(backRightTurnSpeed);
        }

        //System.out.println("Drive Speed (front left): " + frontLeftTurnSpeed);

        if (Math.abs(frontLeftDifference) > encoderZeroThreshold && desiredDriveSpeed >= snapBackRadius)
        {
            frontLeftTurn.set(frontLeftTurnSpeed);
        }
        else
        {
            frontLeftTurn.set(0.0);
        }
        if (Math.abs(frontRightDifference) > encoderZeroThreshold && desiredDriveSpeed >= snapBackRadius)
        {
            frontRightTurn.set(frontRightTurnSpeed);
        }
        else
        {
            frontRightTurn.set(0.0);
        }
        if (Math.abs(backLeftDifference) > encoderZeroThreshold && desiredDriveSpeed >= snapBackRadius)
        {
            backLeftTurn.set(backLeftTurnSpeed);
        }
        else
        {
            backLeftTurn.set(0.0);
        }
        if (Math.abs(backRightDifference) > encoderZeroThreshold && desiredDriveSpeed >= snapBackRadius)
        {
            backRightTurn.set(backRightTurnSpeed);
        }
        else
        {
            backRightTurn.set(0.0);
        }

        double frontLeftDriveSpeed = desiredDriveSpeed * maxDriveSpeed;
        double frontRightDriveSpeed = desiredDriveSpeed * maxDriveSpeed;
        double backLeftDriveSpeed = desiredDriveSpeed * maxDriveSpeed;
        double backRightDriveSpeed = desiredDriveSpeed * maxDriveSpeed;

        if (frontLeftIsInverted)
        {
            frontLeftDriveSpeed *= -1;
        }
        if (frontRightIsInverted)
        {
            frontRightDriveSpeed *= -1;
        }
        if (backLeftIsInverted)
        {
            backLeftDriveSpeed *= -1;
        }
        if (backRightIsInverted)
        {
            backRightDriveSpeed *= -1;
        }

        //individualize wheels
        if (Math.abs(desiredDriveSpeed) > minDriveSpeed)
        {
            frontLeftDrive.set(frontLeftDriveSpeed);
            frontRightDrive.set(frontRightDriveSpeed);
            backLeftDrive.set(backLeftDriveSpeed);
            backRightDrive.set(backRightDriveSpeed);
        }
        else
        {
            frontLeftDrive.set(0.0);
            frontRightDrive.set(0.0);
            backLeftDrive.set(0.0);
            backRightDrive.set(0.0);
        }

        System.out.println("Robot Rotation: " + gyro.getAngleCoterminal());

        // if (Math.abs(frontLeftDifference) > encoderZeroThreshold)
        // {
        //     frontLeftTurn.set(frontLeftDifference / 180.0 * kP);
        // }
        // else
        // {
        //     frontLeftTurn.set(0.0);
        // }

        // double leftTriggerValue = xbox.getRawAxis(Xbox.Axis.kLeftTrigger);
        // double rightTriggerValue = xbox.getRawAxis(Xbox.Axis.kRightTrigger);

        //uses the difference between the two trigger inputs to rotate the wheels
        //right trigger is negative voltage, moves clockwise
        //left trigger is positive voltage, moves counterclockwise
       //double turnSpeed = leftTriggerValue - rightTriggerValue;

        // frontLeftTurn.set(turnSpeed * maxTurnSpeed);
        // frontRightTurn.set(turnSpeed * maxTurnSpeed);
        // backLeftTurn.set(turnSpeed * maxTurnSpeed);
        // backRightTurn.set(turnSpeed * maxTurnSpeed);

        // if (xbox.getRawButton(Xbox.Button.kA))
        // {
        //     frontLeftDrive.setVoltage(maxDriveSpeedVoltage);
        //     frontRightDrive.setVoltage(maxDriveSpeedVoltage);
        //     backLeftDrive.setVoltage(maxDriveSpeedVoltage);
        //     backRightDrive.setVoltage(maxDriveSpeedVoltage);
        // }
        // else
        // {
        //     frontLeftDrive.setVoltage(0.0);
        //     frontRightDrive.setVoltage(0.0);
        //     backLeftDrive.setVoltage(0.0);
        //     backRightDrive.setVoltage(0.0);
        // }

        // if (xbox.getRawButton(Xbox.Button.kB))
        // {
        //     frontLeftTurn.setVoltage(maxTurnSpeedVoltage);
        //     frontRightTurn.setVoltage(maxTurnSpeedVoltage);
        //     backLeftTurn.setVoltage(maxTurnSpeedVoltage);
        //     backRightTurn.setVoltage(maxTurnSpeedVoltage);

        //     // System.out.println("Front Left Turn Encoder: " + frontLeftEncoder.getAbsolutePosition());
        //     // System.out.println("Front Right Turn Encoder: " + frontRightEncoder.getAbsolutePosition());
        //     // System.out.println("Back Left Turn Encoder: " + backLeftEncoder.getAbsolutePosition());
        //     // System.out.println("Back Right Turn Encoder: " + backRightEncoder.getAbsolutePosition());
        // }
        // else
        // {
        //     frontLeftTurn.setVoltage(0.0);
        //     frontRightTurn.setVoltage(0.0);
        //     backLeftTurn.setVoltage(0.0);
        //     backRightTurn.setVoltage(0.0);
        // }

        /*
        CANCoder values for straight forward (bolts toward the center)
        Front Left Turn Encoder: 168.57421875
        Front Right Turn Encoder: 304.541015625
        Back Left Turn Encoder: 102.392578125
        Back Right Turn Encoder: 349.27734375
        */

        if (xbox.getRawButton(Xbox.Button.kX) && xButtonFlag == true)
        {
            // System.out.println("Front Left Turn Encoder: " + frontLeftEncoder.getAbsolutePosition());
            // System.out.println("Front Right Turn Encoder: " + frontRightEncoder.getAbsolutePosition());
            // System.out.println("Back Left Turn Encoder: " + backLeftEncoder.getAbsolutePosition());
            // System.out.println("Back Right Turn Encoder: " + backRightEncoder.getAbsolutePosition());

            // System.out.println("Front Left Turn Angle: " + frontLeftTurnPosition);
            // System.out.println("Front Right Turn Angle: " + frontRightTurnPosition);
            // System.out.println("Back Left Turn Angle: " + backLeftTurnPosition);
            // System.out.println("Back Right Turn Angle: " + backRightTurnPosition);

            // System.out.println("Left Trigger Value: " + leftTriggerValue);
            // System.out.println("Right Trigger Value: " + rightTriggerValue);

            xButtonFlag = false;
        }

        if (!xbox.getRawButton(Xbox.Button.kX))
        {
            xButtonFlag = true;
        }

        // if (xbox.getRawButton(Xbox.Button.kY))
        // {
        //     System.out.println("Resetting wheels");

        //     if (frontLeftEncoder.getAbsolutePosition() < frontLeftEncoderZero - encoderZeroThreshold)
        //     {
        //         frontLeftTurn.setVoltage(maxTurnSpeedVoltage);
        //     }
        //     else
        //     {
        //         frontLeftTurn.setVoltage(0.0);
        //     }
        // }
    }

    public void testInit()
    {

    }

    public void testPeriodic()
    {

    }

    public void disabledInit()
    {

    }

    public void disabledPeriodic()
    {

    }
}