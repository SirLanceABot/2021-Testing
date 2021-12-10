package frc.elliot2.drivetrain;

import frc.elliot2.Port;

import java.sql.Driver;

import frc.elliot2.NavX;
import frc.elliot2.controls.DriverController;

public class Drivetrain
{
    private SwerveModule frontLeftModule;
    private SwerveModule frontRightModule;
    private SwerveModule backLeftModule;
    private SwerveModule backRightModule;

    private static Drivetrain drivetrain = new Drivetrain();

    private static NavX gyro = NavX.getInstance();

    private static DriverController driverController = DriverController.getInstance();

    private static int frontLeftDrivePort = Port.Motor.FRONT_LEFT_DRIVE;
    private static int frontRightDrivePort = Port.Motor.FRONT_RIGHT_DRIVE;
    private static int backLeftDrivePort = Port.Motor.BACK_LEFT_DRIVE;
    private static int backRightDrivePort = Port.Motor.BACK_RIGHT_DRIVE;

    private static int frontLeftTurnPort = Port.Motor.FRONT_LEFT_TURN;
    private static int frontRightTurnPort = Port.Motor.FRONT_RIGHT_TURN;
    private static int backLeftTurnPort = Port.Motor.BACK_LEFT_TURN;
    private static int backRightTurnPort = Port.Motor.BACK_RIGHT_TURN;

    private static int frontLeftEncoderPort = Port.Encoder.FRONT_LEFT_ENCODER;
    private static int frontRightEncoderPort = Port.Encoder.FRONT_RIGHT_ENCODER;
    private static int backLeftEncoderPort = Port.Encoder.BACK_LEFT_ENCODER;
    private static int backRightEncoderPort = Port.Encoder.BACK_RIGHT_ENCODER;

    private static final double FRONT_LEFT_ENCODER_OFFSET = 76.904296875;
    private static final double FRONT_RIGHT_ENCODER_OFFSET = 215.244140625;
    private static final double BACK_LEFT_ENCODER_OFFSET = 239.453125;
    private static final double BACK_RIGHT_ENCODER_OFFSET = 13.447265625;

    private Drivetrain()
    {
        frontLeftModule = new SwerveModule(frontLeftDrivePort, frontLeftTurnPort, frontLeftEncoderPort, true);
        frontRightModule = new SwerveModule(frontRightDrivePort, frontRightTurnPort, frontRightEncoderPort, false);
        backLeftModule = new SwerveModule(backLeftDrivePort, backLeftTurnPort, backLeftEncoderPort, true);
        backRightModule = new SwerveModule(backRightDrivePort, backRightTurnPort, backRightEncoderPort, false);

        frontLeftModule.setEncoderOffset(FRONT_LEFT_ENCODER_OFFSET);
        frontRightModule.setEncoderOffset(FRONT_RIGHT_ENCODER_OFFSET);
        backLeftModule.setEncoderOffset(BACK_LEFT_ENCODER_OFFSET);
        backRightModule.setEncoderOffset(BACK_RIGHT_ENCODER_OFFSET);

        gyro.reset();
    }

    public static Drivetrain getInstance()
    {
        return drivetrain;
    }

    public void swerveDrive()
    {
        double driveYSpeed = driverController.getAction(DriverController.DriverAxisAction.kDriveY);
        double driveXSpeed = driverController.getAction(DriverController.DriverAxisAction.kDriveX);

        double rotateSpeed = driverController.getAction(DriverController.DriverAxisAction.kRotate);
    }

    public double getBodyAngle()
    {
        return gyro.getAngleCoterminal();
    }
}