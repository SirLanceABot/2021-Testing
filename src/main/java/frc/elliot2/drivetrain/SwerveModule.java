package frc.elliot2.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

public class SwerveModule
{
    private WPI_TalonFX driveMotor;
    private WPI_TalonFX turnMotor;
    private CANCoder encoder;
    private double encoderOffset;

    protected SwerveModule(int driveMotorPort, int turnMotorPort, int encoderPort, boolean isDriveInverted)
    {
        driveMotor = new WPI_TalonFX(driveMotorPort);
        turnMotor = new WPI_TalonFX(turnMotorPort);
        encoder = new CANCoder(encoderPort);

        if (isDriveInverted)
        {
            driveMotor.setInverted(true);
        }
    }

    public void setEncoderOffset(double offset)
    {
        encoderOffset = offset;
    }

    public double getWheelAngle()
    {
        return encoder.getAbsolutePosition() - encoderOffset;
    }
}