package frc.elliot2.controls;

import frc.elliot2.Port;

public class DriverController extends Xbox
{
    private static DriverController driverController = new DriverController(Port.USB.XBOX_CONTROLLER_PORT);

    public enum DriverAxisAction
    {
        kDriveY(Axis.kLeftY, 0.1, 0.0, 1.0, true, AxisScale.kLinear),
        kDriveX(Axis.kLeftX, 0.1, 0.0, 1.0, false, AxisScale.kLinear),

        kRotate(Axis.kRightX, 0.1, 0.0, 1.0, false, AxisScale.kLinear)
        ;

        public final Axis axis;
        public final double axisDeadzone;
        public final double axisMinOutput;
        public final double axisMaxOutput;
        public final boolean axisIsFlipped;
        public final AxisScale axisScale;

        private DriverAxisAction(Axis axis, double axisDeadzone, double axisMinOutput, double axisMaxOutput, boolean axisIsFlipped, AxisScale axisScale)
        {
            this.axis = axis;
            this.axisDeadzone = axisDeadzone;
            this.axisMinOutput = axisMinOutput;
            this.axisMaxOutput = axisMaxOutput;
            this.axisIsFlipped = axisIsFlipped;
            this.axisScale = axisScale;
        } 
    }

    private DriverController(int port)
    {
        super(port);
    }

    public static DriverController getInstance()
    {
        return driverController;
    }

    public double getAction(DriverAxisAction axisAction)
    {
        return getRawAxis(axisAction.axis);
    }
}