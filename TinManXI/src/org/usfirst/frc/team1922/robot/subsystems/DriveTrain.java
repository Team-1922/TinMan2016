package org.usfirst.frc.team1922.robot.subsystems;

import org.usfirst.frc.team1922.robot.commands.TeleopDrive;
import org.w3c.dom.Document;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
/**
 *
 */

//TODO: deal with multiple inheritance problem
public class DriveTrain extends Subsystem implements CfgInterface {
	
	/*
	 * 
	 * Config Variables
	 * 
	 */
	
	private ConfigurableClass mCfgInstance = new ConfigurableClass("DriveTrain", this);
	
	protected float mXSensitivity = 1.0f;
	protected float mYSensitivity = 1.0f;
	
	//if these values are negative, that motor is inverted
	protected int mLeftMotorId1 = 1;
	protected int mLeftMotorId2 = 2;
	protected int mRightMotorId1 = 3;
	protected int mRightMotorId2 = 4;
	
	
	/*
	 * 
	 * Actual Member Variables
	 * 
	 */
	protected SpeedController mLeftMotor1;
	protected SpeedController mLeftMotor2;
	protected SpeedController mRightMotor1;
	protected SpeedController mRightMotor2;
	
	/*
	 * 
	 * Member Functions
	 * 
	 */
	public DriveTrain()
	{
		//Reconstruct();
	}
	public void Reconstruct()
	{
		/*mLeftMotor1 = new CANTalon(Math.abs(mLeftMotorId1));
		mLeftMotor2 = new CANTalon(Math.abs(mLeftMotorId2));
		mRightMotor1 = new CANTalon(Math.abs(mRightMotorId1));
		mRightMotor2 = new CANTalon(Math.abs(mRightMotorId2));*/
		
		//the id will typically be over 9000 if we aren't using the motor controller
		mLeftMotor1 = new Victor(Math.abs(mLeftMotorId1));
		mLeftMotor2 = new Victor(Math.abs(mLeftMotorId2));
		mRightMotor1 = new Victor(Math.abs(mRightMotorId1));
		mRightMotor2 = new Victor(Math.abs(mRightMotorId2));
		
		//configure the inversion settings
		mLeftMotor1.setInverted(mLeftMotorId1 < 0);
		mLeftMotor2.setInverted(mLeftMotorId2 < 0);
		mRightMotor1.setInverted(mRightMotorId1 < 0);
		mRightMotor2.setInverted(mRightMotorId2 < 0);
	}
	
	public void SetPower(double left, double right)
	{
		mLeftMotor1.set(left);
		mLeftMotor2.set(left);
		mRightMotor1.set(right);
		mRightMotor2.set(right);
	}
	
	/*
	 * 
	 * Override Functions
	 * 
	 */

	@Override
	public boolean DeserializeInternal() {

		mXSensitivity = Float.parseFloat(mCfgInstance.GetAttribute("XSensitivity"));
		mYSensitivity = Float.parseFloat(mCfgInstance.GetAttribute("YSensitivity"));
		
		mLeftMotorId1 = Integer.parseInt(mCfgInstance.GetAttribute("LeftMotor1"));
		mLeftMotorId2 = Integer.parseInt(mCfgInstance.GetAttribute("LeftMotor2"));
		mRightMotorId1 = Integer.parseInt(mCfgInstance.GetAttribute("RightMotor1"));
		mRightMotorId2 = Integer.parseInt(mCfgInstance.GetAttribute("RightMotor2"));
		
		Reconstruct();
		
		return true;
	}

	@Override
	public void SerializeInternal(Document doc) {


		mCfgInstance.SetAttribute("XSensitivity", Float.toString(mXSensitivity));
		mCfgInstance.SetAttribute("YSensitivity", Float.toString(mYSensitivity));
		
		mCfgInstance.SetAttribute("LeftMotor1", Integer.toString(mLeftMotorId1));
		mCfgInstance.SetAttribute("LeftMotor2", Integer.toString(mLeftMotorId2));
		mCfgInstance.SetAttribute("RightMotor1", Integer.toString(mRightMotorId1));
		mCfgInstance.SetAttribute("RightMotor2", Integer.toString(mRightMotorId2));
		
	}

	@Override
	public ConfigurableClass GetCfgClass() {
		return mCfgInstance;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new TeleopDrive());
    }
	@Override
	public void MakeCfgClassesNull() {
		mLeftMotor1 = null;
		mLeftMotor2 = null;
		mRightMotor1 = null;
		mRightMotor2 = null;
	}

}

