package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.OzMath;
import org.ozram1922.cfg.CfgDocument;
import org.ozram1922.cfg.CfgElement;
import org.ozram1922.cfg.CfgInterface;
import org.usfirst.frc.team1922.robot.AnalogUltrasonic;
import org.usfirst.frc.team1922.robot.commands.shooter.JoyCtrlAngle;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterAngleAnalog extends Subsystem implements CfgInterface {
    
	/*
	 * 
	 * Member Variables
	 * 
	 */
	protected CANTalon mAngleMotor;
	protected AnalogUltrasonic mDistanceFinder;
	protected float mAngleBaseline = 0.0f;
	
	
	/*
	 * 
	 * Config Variables
	 * 
	 */
	protected float mAngleRatio;
	protected float mAngleOffset;
	
	protected float mHighGoalAngle;
	protected float mIntakeAngle;
	protected float mLowGoalAngle;
	
	protected float mMinSafeAngle;
	protected float mMaxSafeAngle;
	
	
	protected float mP = 0.0f;
	protected float mI = 0.0f;
	protected float mD = 0.0f;
	protected int mMotorId = 5;
	protected int mUltraId = 0;
	
	public ShooterAngleAnalog()
	{
	}
	
	//mult ratio is in potValue per degree
	//horizAngle is in normalized encoder units
	public void Reconstruct()
	{
		mDistanceFinder = new AnalogUltrasonic(mUltraId);
		
		
		mAngleMotor = new CANTalon(mMotorId);
		mAngleMotor.setFeedbackDevice(FeedbackDevice.AnalogPot);
		mAngleMotor.changeControlMode(TalonControlMode.Position);
		mAngleMotor.configPotentiometerTurns(1);

		mAngleMotor.setProfile(0);
		
		//mAngleMotor.reverseSensor(true);
		mAngleMotor.reverseOutput(true);
		mAngleMotor.setInverted(false);
		
		mAngleMotor.setPID(mP, mI, mD);
		
		mAngleMotor.enableLimitSwitch(true, true);
	}
	
	//angle is relative to horizontal (negative to get to feeding position)
	public void SetAngle(float angle)
	{
		float set = mAngleBaseline + mAngleOffset - (mAngleRatio * angle);
		System.out.println("Setting Angle To:" + set);
		mAngleMotor.changeControlMode(TalonControlMode.Position);
		mAngleMotor.set(set);
		//mAngleMotor.set(angle);
	}
	
	public double GetAngle()
	{
		return (mAngleBaseline + mAngleOffset - mAngleMotor.getPosition())/mAngleRatio;
	}
	
	public double GetAngleRaw()
	{
		return mAngleMotor.getPosition();
	}
	
	public float GetMinSafeAngle()
	{
		return mMinSafeAngle;
	}
	
	public float GetMaxSafeAngle()
	{
		return mMaxSafeAngle;
	}
	
	public void SetSpeed(double d)
	{
		mAngleMotor.changeControlMode(TalonControlMode.PercentVbus);
		mAngleMotor.set(d);
	}
	
	//TODO: does this do what it should?  
	public boolean IsStopped()
	{
		return mAngleMotor.isFwdLimitSwitchClosed() || mAngleMotor.isRevLimitSwitchClosed();
	}
	
	public boolean OnTarget()
	{
		//System.out.println(mAngleMotor.getSetpoint());
		//return true;
		return OzMath.SigmaTest(mAngleMotor.getPosition(), mAngleMotor.getSetpoint(), .02);
		//TODO: get the setpoint in radians and do sigma test in radians
	}
	
	//returns in feet
	public double GetUltraDistance()
	{
		return mDistanceFinder.GetDistanceInches() / 12.0;
	}
	
	public double GetUltraDistanceRaw()
	{
		return mDistanceFinder.getAverageVoltage();
	}
	
	public void SetAngleBaseline()
	{
		mAngleBaseline = (float) mAngleMotor.getPosition();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new JoyCtrlAngle());
    }

	public double GetP() {
		return mAngleMotor.getP();
	}

	public double GetI() {
		return mAngleMotor.getI();
	}

	public double GetD() {
		return mAngleMotor.getD();
	}
	
	public void SetPID(double p, double i, double d)
	{
		mAngleMotor.setPID(p, i, d);
	}

	public float GetIntakeAngle() {
		return mIntakeAngle;
	}
	public float GetHighGoalAngle()
	{
		return mHighGoalAngle;
	}

	//technically this does not stop the PID controller, it just sets the setpoint to the current position
	public void StopPID() {
		SetAngle((float) GetAngle());
	}

	public boolean IsWithinSafeRange() {
		return GetAngle() > mMinSafeAngle && GetAngle() < mMaxSafeAngle;
	}
	
	@Override
	public void MakeCfgClassesNull()
	{
		if(mAngleMotor != null)
			mAngleMotor.delete();
		mAngleMotor = null;
	}

	@Override
	public boolean Deserialize(CfgElement element) {


		mUltraId = element.GetAttributeI("UltraId");
		
		mP = element.GetAttributeF("P");
		mI = element.GetAttributeF("I");
		mD = element.GetAttributeF("D");
		mMotorId = element.GetAttributeI("MotorId");
		mAngleRatio = element.GetAttributeF("AngleToNorm");
		mAngleOffset = element.GetAttributeF("PotOffset");
		mIntakeAngle = element.GetAttributeF("IntakeAngle");
		mHighGoalAngle = element.GetAttributeF("HighGoalRegister");
		mLowGoalAngle = element.GetAttributeF("LowGoalAngle");
		mMinSafeAngle = element.GetAttributeF("MinSafeAngle");
		mMaxSafeAngle = element.GetAttributeF("MaxSafeAngle");
		
		Reconstruct();
		return true;
	}

	@Override
	public CfgElement Serialize(CfgElement blank, CfgDocument doc) {


		blank.SetAttribute("UltraId", mUltraId);
		
		blank.SetAttribute("P", mP);
		blank.SetAttribute("I", mI);
		blank.SetAttribute("D", mD);
		
		blank.SetAttribute("MotorId", mMotorId);

		blank.SetAttribute("AngleToNorm", mAngleRatio);
		blank.SetAttribute("PotOffset", mAngleOffset);
		blank.SetAttribute("IntakeAngle", mIntakeAngle);
		blank.SetAttribute("LowGoalAngle", mLowGoalAngle);
		
		blank.SetAttribute("MinSafeAngle", mMinSafeAngle);
		blank.SetAttribute("MaxSafeAngle", mMaxSafeAngle);
		
		return blank;
	}

	@Override
	public String GetElementTitle() {
		return "Angle";
	}

	public float GetLowGoalAngle() {
		return mLowGoalAngle;
	}
}

