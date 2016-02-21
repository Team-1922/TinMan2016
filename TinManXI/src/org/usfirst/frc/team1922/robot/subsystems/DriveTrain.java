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

import java.util.HashMap;

import org.ozram1922.OzMath;
import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
/**
 *	WHen using SetSetpoint, MAKE SURE to have the rotation units in INCHES (radians * radius)
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
	protected float mMP;
	protected float mMI;
	protected float mMD;
	//this is in inches
	protected float mMTolerance;
	
	protected float mInchesToEncoderUnits;
	protected float mTurningRadius;
	
	/*
	 * 
	 * Rotational PID
	 * 
	 */
	//this is in PERCENT OF THE WINDOW WIDTH
	protected float mATolerance;
	
	protected float mAP;
	protected float mAI;
	protected float mAD;
	protected float mRadiansToEncoderUnits;
	
	
	/*
	 * 
	 * Actual Member Variables
	 * 
	 */
	protected CANTalon mLeftMotor1;
	protected CANTalon mLeftMotor2;
	protected CANTalon mRightMotor1;
	protected CANTalon mRightMotor2;
	
	protected double mClutchRatio = 1.0;
	
	/*
	 * 
	 * Member Functions
	 * 
	 */
	public DriveTrain()
	{
		super("Drive Train");
		
		HashMap<String, PIDOutput> outputs = new HashMap<String, PIDOutput>();
		HashMap<String, PIDSource> sources = new HashMap<String, PIDSource>();
		
		outputs.put("Linear", linearSetter);
		outputs.put("Rotational", rotationalSetter);
		
		sources.put("Default", getter);
		
		AddPIDController("Movement", mMP, mMI, mMD, 0.0f, outputs, sources, "Linear", "Default");
		GetPIDController("Movement").setAbsoluteTolerance(mMTolerance * mInchesToEncoderUnits);
		
		//AddPIDController("Aiming", mRP, mRI, mRD, 0.0f, new RotationSetter(), new SpeedGetter());
		//GetPIDController("Aiming").setAbsoluteTolerance(mRTolerance * mDegreesToEncoderUnits);
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
		mLeftMotor1.set(mClutchRatio * mLeftSensitivity * left);
		mLeftMotor2.set(mClutchRatio * mLeftSensitivity * left);
		mRightMotor1.set(mClutchRatio * mRightSensitivity * right);
		mRightMotor2.set(mClutchRatio * mRightSensitivity * right);
	}
	
	public void SetSensitivityClutch(double val)
	{
		mClutchRatio = val;
	}
	
	public boolean IsSensitivityClutchEnabled()
	{
		return mClutchRatio < 1.0;
	}
	
	public void PIDSetPower(double left, double right)
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
		return GetTolerance(GetActiveControllerName());
	}
	
	public double GetTolerance(String name)
	{
		switch(name)
		{
		default:
		case "Movement":
			return mMTolerance;
		case "Aiming":
			return mATolerance * Robot.mGlobShooterLatUtils.GetBestWindow().mWidth;
			//return OzMath.GetPixelCountFromDistanceAndLength(mATolerance, OzMath.GetHyp(Robot.mShooter.mShooterAngle.GetUltraDistance(), Robot.mGlobShooterLatUtils.GetCameraToWindowBaseHeight()), Robot.mGlobShooterLatUtils.GetCameraViewWidth());
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
	
	public float GetRotationRadius()
	{
		return mTurningRadius;
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
		
		mMP = Float.parseFloat(mCfgInstance.GetAttribute("MovementP"));
		mMI = Float.parseFloat(mCfgInstance.GetAttribute("MovementI"));
		mMD = Float.parseFloat(mCfgInstance.GetAttribute("MovementD"));
		mMTolerance = Float.parseFloat(mCfgInstance.GetAttribute("MovementTolerance"));
		mInchesToEncoderUnits = Float.parseFloat(mCfgInstance.GetAttribute("InchesToEncoderUnits"));
		mTurningRadius = Float.parseFloat(mCfgInstance.GetAttribute("TurningRadius"));
		
		mAP = Float.parseFloat(mCfgInstance.GetAttribute("AimingP"));
		mAI = Float.parseFloat(mCfgInstance.GetAttribute("AimingI"));
		mAD = Float.parseFloat(mCfgInstance.GetAttribute("AimingD"));
		mATolerance = Float.parseFloat(mCfgInstance.GetAttribute("AimingTolerance"));
		mRadiansToEncoderUnits = Float.parseFloat(mCfgInstance.GetAttribute("RadiansToEncoderUnits"));
		
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
		
		mCfgInstance.SetAttribute("MovementP", Float.toString(mMP));
		mCfgInstance.SetAttribute("MovementI", Float.toString(mMI));
		mCfgInstance.SetAttribute("MovementD", Float.toString(mMD));
		mCfgInstance.SetAttribute("MovementTolerance", Float.toString(mMTolerance));
		mCfgInstance.SetAttribute("InchesToEncoderUnits", Float.toString(mInchesToEncoderUnits));
		mCfgInstance.SetAttribute("TurningRadius", Float.toString(mTurningRadius));
		
		mCfgInstance.SetAttribute("AimingP", Float.toString(mAP));
		mCfgInstance.SetAttribute("AimingI", Float.toString(mAI));
		mCfgInstance.SetAttribute("AimingD", Float.toString(mAD));
		mCfgInstance.SetAttribute("AimingTolerance", Float.toString(mATolerance));
		mCfgInstance.SetAttribute("RadiansToEncoderUnits", Float.toString(mRadiansToEncoderUnits));
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
	public void setSetpoint(double setpoint) 
	{
	    GetActiveController().setSetpoint(setpoint);
	}
	
	//type == 0: Linear (i.e. forward/back)
	//type == 1: Rotational (coarse)
	//type == 1: Aiming (TODO:)
	public void PIDSwap(int type)
	{
		switch(type)
		{
		default:
		case 0:
			SetActiveController("Movement");
			GetActiveControllerModule().SetOutput("Linear");
			break;
		case 1:
			SetActiveController("Movement");
			GetActiveControllerModule().SetOutput("Rotational");
			break;
		//case 2:
			//SetActiveController("Aiming");
			//break;
		}
	}
	
	/*
	 * There only needs to be one source
	 */
	protected PIDSource getter = new PIDSource()
	{

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return Robot.mDriveTrain.mLeftMotor1.getEncPosition() / Robot.mDriveTrain.mInchesToEncoderUnits;		
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
		}
	};
	protected PIDOutput rotationalSetter = new PIDOutput()
	{

		@Override
		public void pidWrite(double output) {
			Robot.mDriveTrain.PIDSetPower(output, -output);
		}
		
	};
	protected PIDOutput linearSetter = new PIDOutput()
	{

		@Override
		public void pidWrite(double output) {
			Robot.mDriveTrain.PIDSetPower(output, output);
		}
		
	};
	
	public void UpdateRotationEncodersWithPixels() 
	{
		SetRotationalTolerance();
		
		mLeftMotor1.setEncPosition(OzMath.PixelsToEncoderUnits(Robot.mGlobShooterLatUtils.GetError()));
	}

}

