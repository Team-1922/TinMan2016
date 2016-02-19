package org.usfirst.frc.team1922.robot.subsystems;

import org.usfirst.frc.team1922.robot.MultiSourcePIDSubsystem;
import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.commands.TeleopDrive;
import org.w3c.dom.Document;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.ozram1922.OzMath;
import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
/**
 *	NOTE: the PID on this class in ONLY designed to work for rotation motion;
 *		Any other attempt to use PID will spectacularly fail
 */
public class DriveTrain extends MultiSourcePIDSubsystem implements CfgInterface {
	
	/*
	 * 
	 * Config Variables
	 * 
	 */
	
	private ConfigurableClass mCfgInstance = new ConfigurableClass("DriveTrain", this);
	
	protected float mLeftSensitivity = 1.0f;
	protected float mRightSensitivity = 1.0f;
	
	//if these values are negative, that motor is inverted
	protected int mLeftMotorId1 = 1;
	protected int mLeftMotorId2 = 2;
	protected int mRightMotorId1 = 3;
	protected int mRightMotorId2 = 4;
	
	
	/*
	 * 
	 * Regular PID
	 * 
	 */
	protected float mLP;
	protected float mLI;
	protected float mLD;
	//this is in inches
	protected float mLTolerance;
	
	protected float mInchesToEncoderUnits;
	
	/*
	 * 
	 * Rotational PID
	 * 
	 */
	//this is in INCHES
	protected float mRTolerance;
	
	protected float mRP;
	protected float mRI;
	protected float mRD;
	protected float mDegreesToEncoderUnits;
	
	
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
		super("Drive Train");
		
		AddPIDController("Linear", mLP, mLI, mLD, 0.0f, new LinearSetter(), new SpeedGetter());
		GetPIDController("Linear").setAbsoluteTolerance(mLTolerance * mInchesToEncoderUnits);
		
		AddPIDController("Rotational", mRP, mRI, mRD, 0.0f, new RotationSetter(), new SpeedGetter());
		GetPIDController("Rotational").setAbsoluteTolerance(mRTolerance * mDegreesToEncoderUnits);
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
		
		//default to the linear back-forth motion
		PIDSwap(0);
	}
	
	public void SetPower(double left, double right)
	{
		mLeftMotor1.set(mLeftSensitivity * left);
		mLeftMotor2.set(mLeftSensitivity * left);
		mRightMotor1.set(mRightSensitivity * right);
		mRightMotor2.set(mRightSensitivity * right);
	}
	

	public double GetTolerance()
	{
		/*
		 * 
		 * This switches based on which PID system is controlling
		 * 
		 * If "Linear": returns tolerance in inches
		 * 
		 * If "Rotational": returns tolerance in DEGREES
		 * 
		 */
		switch (GetActiveControllerName())
		{
		default:
		case "Linear":
			return mLTolerance;
		case "Rotation":
			return OzMath.GetPixelCountFromLength(mRTolerance, Robot.mShooter.mShooterAngle.GetUltraDistance());
		}
	}
	
	public boolean RotationalOnTarget()
	{
		//return false; TODO: this should check to see if the camera is centered in the view AND the encoder is on target
		return onTarget() && (Robot.mGlobShooterLatUtils.GetError() < GetTolerance());
	}
	
	public void SetRotationalTolerance()
	{
		GetPIDController("Rotational").setAbsoluteTolerance(GetTolerance());
	}
	
	/*
	 * 
	 * Override Functions
	 * 
	 */

	@Override
	public boolean DeserializeInternal() {

		mLeftSensitivity = Float.parseFloat(mCfgInstance.GetAttribute("LeftSensitivity"));
		mRightSensitivity = Float.parseFloat(mCfgInstance.GetAttribute("RightSensitivity"));
		
		mLeftMotorId1 = Integer.parseInt(mCfgInstance.GetAttribute("LeftMotor1"));
		mLeftMotorId2 = Integer.parseInt(mCfgInstance.GetAttribute("LeftMotor2"));
		mRightMotorId1 = Integer.parseInt(mCfgInstance.GetAttribute("RightMotor1"));
		mRightMotorId2 = Integer.parseInt(mCfgInstance.GetAttribute("RightMotor2"));
		
		mLP = Float.parseFloat(mCfgInstance.GetAttribute("LinearP"));
		mLI = Float.parseFloat(mCfgInstance.GetAttribute("LinearI"));
		mLD = Float.parseFloat(mCfgInstance.GetAttribute("LinearD"));
		mLTolerance = Float.parseFloat(mCfgInstance.GetAttribute("LinearTolerance"));
		mInchesToEncoderUnits = Float.parseFloat(mCfgInstance.GetAttribute("InchesToEncoderUnits"));
		
		mRP = Float.parseFloat(mCfgInstance.GetAttribute("RotationalP"));
		mRI = Float.parseFloat(mCfgInstance.GetAttribute("RotationalI"));
		mRD = Float.parseFloat(mCfgInstance.GetAttribute("RotationalD"));
		mRTolerance = Float.parseFloat(mCfgInstance.GetAttribute("RotationalTolerance"));
		mDegreesToEncoderUnits = Float.parseFloat(mCfgInstance.GetAttribute("DegreesToEncoderUnits"));
		
		Reconstruct();
		
		return true;
	}

	@Override
	public void SerializeInternal(Document doc) {


		mCfgInstance.SetAttribute("LeftSensitivity", Float.toString(mLeftSensitivity));
		mCfgInstance.SetAttribute("RightSensitivity", Float.toString(mRightSensitivity));
		
		mCfgInstance.SetAttribute("LeftMotor1", Integer.toString(mLeftMotorId1));
		mCfgInstance.SetAttribute("LeftMotor2", Integer.toString(mLeftMotorId2));
		mCfgInstance.SetAttribute("RightMotor1", Integer.toString(mRightMotorId1));
		mCfgInstance.SetAttribute("RightMotor2", Integer.toString(mRightMotorId2));
		
		mCfgInstance.SetAttribute("LinearP", Float.toString(mLP));
		mCfgInstance.SetAttribute("LinearI", Float.toString(mLI));
		mCfgInstance.SetAttribute("LinearD", Float.toString(mLD));
		mCfgInstance.SetAttribute("LinearTolerance", Float.toString(mLTolerance));
		mCfgInstance.SetAttribute("InchesToEncoderUnits", Float.toString(mInchesToEncoderUnits));
		
		mCfgInstance.SetAttribute("RotationalP", Float.toString(mRP));
		mCfgInstance.SetAttribute("RotationalI", Float.toString(mRI));
		mCfgInstance.SetAttribute("RotationalD", Float.toString(mRD));
		mCfgInstance.SetAttribute("RotationalTolerance", Float.toString(mRTolerance));
		mCfgInstance.SetAttribute("DegreesToEncoderUnits", Float.toString(mDegreesToEncoderUnits));
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
	
	//type == 0: Forward/reverse
	//type == 1: Rotational
	public void PIDSwap(int type)
	{
		switch(type)
		{
		default:
		case 0:
			SetActiveController("Linear");
			break;
		case 1:
			SetActiveController("Rotational");
			break;
		}
	}
	
	/*
	 * There only needs to be one source
	 */
	class SpeedGetter implements PIDSource
	{

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return Robot.mDriveTrain.mLeftMotor1.getEncPosition();
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
		}
	}
	
	class RotationSetter implements PIDOutput
	{

		@Override
		public void pidWrite(double output) {
			Robot.mDriveTrain.SetPower(output, -output);
		}
		
	}
	class LinearSetter implements PIDOutput
	{

		@Override
		public void pidWrite(double output) {
			Robot.mDriveTrain.SetPower(output, output);
		}
		
	}
	
	public void UpdateRotationEncodersWithPixels() 
	{
		SetRotationalTolerance();
		
		mLeftMotor1.setEncPosition(OzMath.PixelsToEncoderUnits(Robot.mGlobShooterLatUtils.GetError()));
	}

}

