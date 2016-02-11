package org.usfirst.frc.team1922.robot.subsystems;

import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.commands.TeleopDrive;
import org.w3c.dom.Document;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
/**
 *	NOTE: the PID on this class in ONLY designed to work for rotation motion;
 *		Any other attempt to use PID will spectacularly fail
 */
public class DriveTrain extends PIDSubsystem implements CfgInterface {
	
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
	
	protected float mP;
	protected float mI;
	protected float mD;
	protected float mTolerance;
	
	
	/*
	 * 
	 * Actual Member Variables
	 * 
	 */
	protected CANTalon mLeftMotor1;
	protected CANTalon mLeftMotor2;
	protected CANTalon mRightMotor1;
	protected CANTalon mRightMotor2;
	
	/*
	 * 
	 * Member Functions
	 * 
	 */
	public DriveTrain()
	{
		super(0,0,0);
		//Reconstruct();
	}
	public void Reconstruct()
	{
		/*mLeftMotor1 = new CANTalon(Math.abs(mLeftMotorId1));
		mLeftMotor2 = new CANTalon(Math.abs(mLeftMotorId2));
		mRightMotor1 = new CANTalon(Math.abs(mRightMotorId1));
		mRightMotor2 = new CANTalon(Math.abs(mRightMotorId2));*/
		
		//the id will typically be over 9000 if we aren't using the motor controller
		mLeftMotor1 = new CANTalon(Math.abs(mLeftMotorId1));
		mLeftMotor2 = new CANTalon(Math.abs(mLeftMotorId2));
		mRightMotor1 = new CANTalon(Math.abs(mRightMotorId1));
		mRightMotor2 = new CANTalon(Math.abs(mRightMotorId2));
		
		//configure the inversion settings
		mLeftMotor1.setInverted(mLeftMotorId1 < 0);
		mLeftMotor2.setInverted(mLeftMotorId2 < 0);
		mRightMotor1.setInverted(mRightMotorId1 < 0);
		mRightMotor2.setInverted(mRightMotorId2 < 0);
		
		getPIDController().setPID(mP, mI, mD);
		getPIDController().setAbsoluteTolerance(mTolerance);
		
		setSetpoint(Robot.mGlobShooterLatUtils.GetWindage());
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
		
		mP = Float.parseFloat(mCfgInstance.GetAttribute("P"));
		mI = Float.parseFloat(mCfgInstance.GetAttribute("I"));
		mD = Float.parseFloat(mCfgInstance.GetAttribute("D"));
		mTolerance = Float.parseFloat(mCfgInstance.GetAttribute("Tolerance"));
		
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
		
		mCfgInstance.SetAttribute("P", Float.toString(mP));
		mCfgInstance.SetAttribute("I", Float.toString(mI));
		mCfgInstance.SetAttribute("D", Float.toString(mD));
		
		mCfgInstance.SetAttribute("Tolerance", Float.toString(mTolerance));
		
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
		if(mLeftMotor1 != null)
			mLeftMotor1.delete();
		if(mLeftMotor2 != null)
			mLeftMotor2.delete();
		if(mRightMotor1 != null)
			mRightMotor1.delete();
		if(mRightMotor2 != null)
			mRightMotor2.delete();
		
		mLeftMotor1 = null;
		mLeftMotor2 = null;
		mRightMotor1 = null;
		mRightMotor2 = null;
	}
	@Override
	protected double returnPIDInput() {
		return Robot.mGlobShooterLatUtils.GetBestWindow().mCenterY;
	}
	
	//positive = cw; set to spin about a central axis
	@Override
	protected void usePIDOutput(double output) {
		
		//output is the 'spin'
		mLeftMotor1.set(output);
		mLeftMotor2.set(output);

		mRightMotor1.set(-output);
		mRightMotor2.set(-output);
	}

}

