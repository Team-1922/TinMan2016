package org.usfirst.frc.team1922.robot.subsystems;

import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.commands.TeleopDrive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.ozram1922.cfg.CfgDocument;
import org.ozram1922.cfg.CfgElement;
import org.ozram1922.cfg.CfgInterface;
/**
 *	WHen using SetSetpoint, MAKE SURE to have the rotation units in INCHES (radians * radius)
 */
public class DriveTrain /*extends MultiSourcePIDSubsystem*/extends Subsystem implements CfgInterface {
	
	/*
	 * 
	 * Config Variables
	 * 
	 */
	
	protected float mLeftSensitivity = 1.0f;
	protected float mRightSensitivity = 1.0f;
	
	//if these values are negative, that motor is inverted
	protected int mLeftMotorId1 = 1;
	protected int mLeftMotorId2 = 2;
	protected int mRightMotorId1 = 3;
	protected int mRightMotorId2 = 4;
	
	protected String mEnabledPIDMode = "Manual";
	
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
		
		/*HashMap<String, PIDOutput> outputs = new HashMap<String, PIDOutput>();
		HashMap<String, PIDSource> sources = new HashMap<String, PIDSource>();
		
		outputs.put("Linear", linearSetter);
		outputs.put("Rotational", rotationalSetter);
		
		sources.put("Default", getter);
		
		AddPIDController("Movement", mMP, mMI, mMD, 0.0f, outputs, sources, "Linear", "Default");
		GetPIDController("Movement").setAbsoluteTolerance(mMTolerance);*/
		
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
		
		mLeftMotor1.setPID(mMP, mMI, mMD);
		
		
		//update the PID values
		//GetPIDController("Movement").setPID(mMP, mMI, mMD);
		//GetPIDController("Movement").setAbsoluteTolerance(mMTolerance);
		
		
		//default to the linear back-forth motion
		PIDSwap("Manual");
	}
	
	public void SetPower(double left, double right)
	{
		PIDSwap("Manual");
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
	
	@Deprecated
	public void PIDSetPower(double left, double right)
	{
		mLeftMotor1.set(-mLeftSensitivity * left);
		mLeftMotor2.set(-mLeftSensitivity * left);
		mRightMotor1.set(-mRightSensitivity * right);
		mRightMotor2.set(-mRightSensitivity * right);
	}

	public double GetTolerance()
	{
		/*
		 * 
		 * This switches based on which PID system is controlling
		 * 
		 * If "Linear": returns tolerance in inches
		 * 
		 * If "Rotational": returns tolerance in inches
		 * 
		 * If "Aiming": returns pixels
		 * 
		 */
		return GetTolerance(mEnabledPIDMode);
	}
	
	public double GetTolerance(String name)
	{
		switch(name)
		{
		case "Manual":
			return 0;
		default:
		case "Rotational":
		case "Linear":
			return mMTolerance;
		case "Aiming":
			return mATolerance * Robot.mGlobShooterLatUtils.GetBestWindow().mWidth;
			//return OzMath.GetPixelCountFromDistanceAndLength(mATolerance, OzMath.GetHyp(Robot.mShooter.mShooterAngle.GetUltraDistance(), Robot.mGlobShooterLatUtils.GetCameraToWindowBaseHeight()), Robot.mGlobShooterLatUtils.GetCameraViewWidth());
		}
	}
	
	public double GetToleranceEnc()
	{
		return GetToleranceEnc(mEnabledPIDMode);
	}
	
	public double GetToleranceEnc(String mode)
	{
		return GetTolerance(mode) * mInchesToEncoderUnits;
	}
	
	public boolean AimingOnTarget()
	{
		return Robot.mGlobShooterLatUtils.GetError() < GetTolerance() && onTarget("Rotational");
	}
	
	@Deprecated
	public void SetAimingTolerance()
	{
		//GetPIDController("Aiming").setAbsoluteTolerance(GetTolerance());
	}
	
	public float GetRotationRadius()
	{
		return mTurningRadius;
	}
	
	//call "PIDSwap" before this for defined behavior
	//if rotational: radians
	//if linear: inches
	//if aiming: n/a
	public void SetSetpoint(double units)
	{
		switch(mEnabledPIDMode)
		{
		case "Manual":
			break;
		case "Linear":
			mLeftMotor1.set(units * mInchesToEncoderUnits);
			break;
		case "Rotational":
			mLeftMotor1.set(units * mRadiansToEncoderUnits);
			break;
		case "Aiming":
			mLeftMotor1.set(0);
			break;
		}
	}
	
	public void SetDeltaSetpoint(double units)
	{
		switch(mEnabledPIDMode)
		{
		case "Manual":
			break;
		case "Linear":
			mLeftMotor1.set(units * mInchesToEncoderUnits + mLeftMotor1.getEncPosition());
			break;
		case "Rotational":
			mLeftMotor1.set(units * mRadiansToEncoderUnits + mLeftMotor1.getEncPosition());
			break;
		case "Aiming":
			mLeftMotor1.set(0);
			break;
		}
	}
	
	public double GetEncPosInches()
	{
		System.out.println(Robot.mDriveTrain.mLeftMotor1.getEncPosition() / Robot.mDriveTrain.mInchesToEncoderUnits);
		return Robot.mDriveTrain.mLeftMotor1.getEncPosition() / Robot.mDriveTrain.mInchesToEncoderUnits;		
		
	}
	
	/*
	 * 
	 * Override Functions
	 * 
	 */

	@Override
	public boolean Deserialize(CfgElement element) {

		mLeftSensitivity = element.GetAttributeF("LeftSensitivity");
		mRightSensitivity = element.GetAttributeF("RightSensitivity");
		
		mLeftMotorId1 = element.GetAttributeI("LeftMotor1");
		mLeftMotorId2 = element.GetAttributeI("LeftMotor2");
		mRightMotorId1 = element.GetAttributeI("RightMotor1");
		mRightMotorId2 = element.GetAttributeI("RightMotor2");
		
		mMP = element.GetAttributeF("MovementP");
		mMI = element.GetAttributeF("MovementI");
		mMD = element.GetAttributeF("MovementD");
		mMTolerance = element.GetAttributeF("MovementTolerance");
		mInchesToEncoderUnits = element.GetAttributeF("InchesToEncoderUnits");
		mTurningRadius = element.GetAttributeF("TurningRadius");
		
		mATolerance = Float.parseFloat(element.GetAttribute("AimingTolerance"));
		
		//This may be OK to do.  because enc per angle = (enc/in) * radius
		mRadiansToEncoderUnits = mInchesToEncoderUnits * mTurningRadius;
		//mRadiansToEncoderUnits = Float.parseFloat(mCfgInstance.GetAttribute("RadiansToEncoderUnits"));
		
		Reconstruct();
		
		return true;
	}

	@Override
	public CfgElement Serialize(CfgElement element, CfgDocument doc) {


		element.SetAttribute("LeftSensitivity", mLeftSensitivity);
		element.SetAttribute("RightSensitivity", mRightSensitivity);
		
		element.SetAttribute("LeftMotor1", mLeftMotorId1);
		element.SetAttribute("LeftMotor2", mLeftMotorId2);
		element.SetAttribute("RightMotor1", mRightMotorId1);
		element.SetAttribute("RightMotor2", mRightMotorId2);
		
		element.SetAttribute("MovementP", mMP);
		element.SetAttribute("MovementI", mMI);
		element.SetAttribute("MovementD", mMD);
		element.SetAttribute("MovementTolerance", mMTolerance);
		element.SetAttribute("InchesToEncoderUnits", mInchesToEncoderUnits);
		element.SetAttribute("TurningRadius", mTurningRadius);
		
		element.SetAttribute("AimingTolerance", mATolerance);
		//mCfgInstance.SetAttribute("RadiansToEncoderUnits", Float.toString(mRadiansToEncoderUnits));
		
		return element;
	}

	@Override
	public String GetElementTitle()
	{
		return "DriveTrain";
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


	public void PIDSwap(String type)
	{
		switch(type)
		{
		default:
		case "Manual":
			mLeftMotor1.changeControlMode(TalonControlMode.PercentVbus);
			mLeftMotor2.changeControlMode(TalonControlMode.PercentVbus);
			mRightMotor1.changeControlMode(TalonControlMode.PercentVbus);
			mRightMotor2.changeControlMode(TalonControlMode.PercentVbus);
			

			mLeftMotor1.setInverted(mLeftMotorId1 < 0);
			mLeftMotor2.setInverted(mLeftMotorId2 < 0);
			mRightMotor1.setInverted(mRightMotorId1 < 0);
			mRightMotor2.setInverted(mRightMotorId2 < 0);
			
			mEnabledPIDMode = type;
			break;
		case "Linear":
			mLeftMotor1.changeControlMode(TalonControlMode.Position);
			mLeftMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			
			mLeftMotor2.changeControlMode(TalonControlMode.Follower);
			mLeftMotor2.set(mLeftMotorId1);
			
			mRightMotor1.changeControlMode(TalonControlMode.Follower);
			mRightMotor1.set(mLeftMotorId1);
			
			mRightMotor2.changeControlMode(TalonControlMode.Follower);
			mRightMotor2.set(mLeftMotorId1);
			

			mLeftMotor1.reverseOutput(mLeftMotorId1 < 0);
			mLeftMotor2.reverseOutput(mLeftMotorId2 < 0);
			mRightMotor1.reverseOutput(mRightMotorId1 < 0);
			mRightMotor2.reverseOutput(mRightMotorId2 < 0);
			
			mEnabledPIDMode = type;
			break;
		case "Aiming":
		case "Rotational":
			
			//REMEMBER: Positive angle = CCW!

			mLeftMotor1.changeControlMode(TalonControlMode.Position);
			mLeftMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			
			mLeftMotor2.changeControlMode(TalonControlMode.Follower);
			mLeftMotor2.set(mLeftMotorId1);
			
			mRightMotor1.changeControlMode(TalonControlMode.Follower);
			mRightMotor1.set(mLeftMotorId1);
			
			mRightMotor2.changeControlMode(TalonControlMode.Follower);
			mRightMotor2.set(mLeftMotorId1);

			mLeftMotor1.reverseOutput(mLeftMotorId1 > 0);
			mLeftMotor2.reverseOutput(mLeftMotorId2 > 0);
			mRightMotor1.reverseOutput(mRightMotorId1 < 0);
			mRightMotor2.reverseOutput(mRightMotorId2 < 0);
			
			mEnabledPIDMode = type;
			break;
		//case 2:
			//SetActiveController("Aiming");
			//break;
		}
	}
	
	
	
	//if isHorizontal == false, it is vertical; bottom=distance from bottom extrema to the BOTTOM of "pixels"
	public int PixelsToEncoderUnits(int pixels, int bottom, boolean isHorizontal)
	{
		//see Kevin for this equation
		double mu = 0;
		double hpx = 0;
		double t = bottom + pixels;
		double b = bottom;
		if(isHorizontal)
		{
			mu = Robot.mGlobShooterLatUtils.GetHorizontalFOV();
			hpx = Robot.mGlobShooterLatUtils.GetCameraViewWidth();
		}
		else
		{
			mu = Robot.mGlobShooterLatUtils.GetVerticalFOV();
			hpx = Robot.mGlobShooterLatUtils.GetCameraViewHeight();
		}
		
		double tanMuOver2 = Math.tan(mu/2);
		double hpxOverSinMu = hpx/Math.sin(mu);
		double tACotTerm = Math.atan(1/(hpxOverSinMu/t - tanMuOver2));
		double bACotTerm = Math.atan(1/(hpxOverSinMu/b - tanMuOver2));
		
		return (int) ((mu - tACotTerm - bACotTerm) * mRadiansToEncoderUnits);
	}
	
	public void UpdateRotationEncodersWithPixels() 
	{
		//SetAimingTolerance();
		
		StrongholdWindow bestWindow = Robot.mGlobShooterLatUtils.GetBestWindow();
		
		int pixels = Robot.mGlobShooterLatUtils.GetError();
		int base = bestWindow.mCenterX;
		int encoderUnits = PixelsToEncoderUnits(Robot.mGlobShooterLatUtils.GetError(), bestWindow.mCenterX, true);
		
		System.out.println("Updating DT Encoders with Pixel Conversions: Pixels=" + pixels + "; base=" + base + "; encoder Units=" + encoderUnits + ";");
		mLeftMotor1.setEncPosition(encoderUnits);
	}
	public void disable() {
		PIDSwap("Manual");
	}
	public boolean onTarget(String mode)
	{
		if(mode == "Aiming")
		{
			return GetToleranceEnc(mode) > Robot.mGlobShooterLatUtils.GetError();
		}
		return GetToleranceEnc(mode) > mLeftMotor1.getError();
	}
	public boolean onTarget()
	{
		return onTarget(mEnabledPIDMode);
	}

}

