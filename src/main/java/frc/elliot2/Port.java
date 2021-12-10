package frc.elliot2;

public class Port
{
    private Port()
    {
        
    }

    public class Motor
    {
        public static final int FRONT_LEFT_DRIVE = 7;
        public static final int FRONT_LEFT_TURN = 9;
        public static final int FRONT_RIGHT_DRIVE = 10;
        public static final int FRONT_RIGHT_TURN = 12;
        public static final int BACK_LEFT_DRIVE = 4;
        public static final int BACK_LEFT_TURN = 6;
        public static final int BACK_RIGHT_DRIVE = 1;
        public static final int BACK_RIGHT_TURN = 3;
    }

    public class Encoder
    {
        public static final int FRONT_LEFT_ENCODER = 11;
        public static final int FRONT_RIGHT_ENCODER = 8;
        public static final int BACK_LEFT_ENCODER = 5;
        public static final int BACK_RIGHT_ENCODER = 2;
    }

    public class USB
    {
        public static final int XBOX_CONTROLLER_PORT = 0;
    }
}
