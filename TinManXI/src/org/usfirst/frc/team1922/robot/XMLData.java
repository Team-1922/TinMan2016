package org.usfirst.frc.team1922.robot;

@Deprecated
public class XMLData 
{
	public static String GetString()
	{
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <root> <DriveTrain LeftMotor1=\"-7\" LeftMotor2=\"-2\" RightMotor1=\"3\" RightMotor2=\"4\" LeftSensitivity=\"1.0\" RightSensitivity=\"0.8\" P=\"0.00083\" I=\"0.0\" D=\"0.0\" Tolerance=\"5.0\"/> <OI> <Joystick Name=\"OpStick\" Id=\"0\" /> <Joystick Name=\"LeftStick\" Id=\"1\" /> <Joystick Name=\"RightStick\" Id=\"2\" /> <Command Joystick=\"OpStick\" Button=\"9\" Name=\"shooter.SpinUpShooterWheelsCfg\" /> <Command Joystick=\"OpStick\" Button=\"7\" Name=\"PutWindowInView\" /> <Command Joystick=\"OpStick\" Button=\"3\" Name=\"FindAndCenterWindow\" /> <Command Joystick=\"OpStick\" Button=\"5\" Name=\"shooter.SetShooterAngle25\" /> <Command Joystick=\"OpStick\" Button=\"2\" Name=\"shooter.SetShooterAngleHorizontal\" /> <Command Joystick=\"OpStick\" Button=\"4\" Name=\"shooter.SpinDownShooterWheels\" /> </OI> <ShooterLateralGlobal NetTableName=\"GRIP/myContoursReport\" Windage=\"160\" /> <Shooter> <Angle P=\"375.0\" I=\"0.015\" D=\"0.0\" AngleTo5v=\"0.007461538\" PotOffset=\"0.150\" MotorId=\"5\" Ping=\"0\" Echo=\"1\" /> <Wheels MotorId=\"6\" P=\"2.2\" I=\"0.0\" D=\"50.0\" F=\".0465\" EncSamplesPerRotation=\"4096\" ShootRPM=\"2000\"/> <Lateral P=\"0.0\" I=\"0.0\" D=\"0.0\" Tolerance=\"0.0\" /> <Feeder Sol=\"0\" /> </Shooter> <BallRetriever MotorId=\"1\" MotorSpeed=\".75\" /> </root>";
		
		//New Stuff
		//P=350 (maybe 400)
		//I=.015
		//D=0
		//F=0
		//0=.150
		//65=.635
		//mult ratio = 0.00746153846153846153846153846154
		//offset = .150
	}
}
