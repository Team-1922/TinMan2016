package org.usfirst.frc.team1922.robot;

public class XMLData 
{
	public static String GetString()
	{
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <root> <DriveTrain LeftMotor1=\"7\" LeftMotor2=\"-2\" RightMotor1=\"-3\" RightMotor2=\"-4\" XSensitivity=\"1.0\" YSensitivity=\"1.0\" P=\"0.0\" I=\"0.0\" D=\"0.0\" Tolerance=\"0.0\"/> <OI> <Joystick Name=\"OpStick\" Id=\"0\" /> <Joystick Name=\"LeftStick\" Id=\"1\" /> <Joystick Name=\"RightStick\" Id=\"2\" /> <Command Joystick=\"OpStick\" Button=\"9\" Name=\"shooter.SpinUpShooterWheelsCfg\" /> <Command Joystick=\"OpStick\" Button=\"3\" Name=\"FindAndCenterWindow\" /> </OI> <ShooterLateralGlobal NetTableName=\"GRIP/myContoursReport\" Windage=\"320\" /> <Shooter> <Angle P=\"0.0\" I=\"0.0\" D=\"0.0\" AngleTo5v=\"1.0\" PotOffset=\"0.0\" MotorId=\"5\" /> <Wheels MotorId=\"6\" P=\"2.2\" I=\"0.0\" D=\"50.0\" F=\".0465\" EncSamplesPerRotation=\"4096\" ShootRPM=\"3000\" /> <Lateral P=\"0.0\" I=\"0.0\" D=\"0.0\" Tolerance=\"0.0\" /> <Feeder Sol=\"0\" /> </Shooter> </root>";
	}
}
