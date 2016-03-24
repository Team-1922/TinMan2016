package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.OzMath;
import org.ozram1922.cfg.CfgDocument;
import org.ozram1922.cfg.CfgElement;
import org.ozram1922.cfg.CfgInterface;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterWheels extends Subsystem implements CfgInterface {

	/*
	 * 
	 * Member Variables
	 * 
	 */
	protected CANTalon mWheels;
	
	/*
	 * 
	 * Config Variables 
	 * 
	 */

	protected int mId = 6;
	protected float mP = 0.0f;
	protected float mI = 0.0f;
	protected float mD = 0.0f;
	protected float mF = 0.0f;
	protected int mEncToRot = 1;
	protected float mSetRpm = 0;
	protected float mIntakeRpm = 1;
	
	/*
	 * 
	 * Member Functions
	 * 
	 */
	
	//rpmToEnc: the ratio to convert rps's to encoder samples
	public ShooterWheels()
	{
	}
	
	public void Reconstruct()
	{
		System.out.println("Reconstruct Shooter Wheels: " + mId + "," + mP + "," + mI + "," + mD + "," + mF + "," + mEncToRot);
		mWheels = new CANTalon(mId);
		mWheels.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		//mWheels.reverseSensor(true);
		mWheels.configEncoderCodesPerRev(mEncToRot);
		
		mWheels.reverseOutput(false);
		mWheels.setInverted(true);
		
		mWheels.setEncPosition(0);//zero this out so the value doesn't get too large

		//mWheels.configNominalOutputVoltage(+0.0f, -0.0f);
		//mWheels.configPeakOutputVoltage(+12.0f, 0.0f);
		
		mWheels.setProfile(0);
		mWheels.setPID(mP, mI, mD);
		mWheels.setF(mF);
	}
	
	//Set the speed of the motor in rpm (handled by PID control on the talon
	public void SetSpeed(double speed)
	{
		//System.out.println("Speed: " + speed);
		mWheels.changeControlMode(TalonControlMode.Speed);
		//mWheels.enable();
		mWheels.set(speed);
		//mWheels.disable();
	}
	
	public CANTalon GetController()
	{
		return mWheels;
	}
	
	public double GetSetpoint()
	{
		return mWheels.getSetpoint();
	}
	
	public void SpinDown()
	{
		mWheels.setEncPosition(0);//zero this out so the value doesn't get too large
		SoftStop();
	}
	
	public double GetP()
	{
		return mWheels.getP();
	}
	
	public double GetI()
	{
		return mWheels.getI();
	}
	
	public double GetD()
	{
		return mWheels.getD();
	}
	
	public double GetF()
	{
		return mWheels.getF();
	}
	
	public void SetPID(double P, double I, double D, double F)
	{
		mWheels.setPID(P, I, D);
		mWheels.setF(F);
	}
	
	public boolean IsSpunUp()
	{
		return OzMath.SigmaTest(mWheels.getSpeed(), mWheels.getSetpoint(), 50);
	}

    public void initDefaultCommand() 
    {
    }


	public double GetSpeed() {
		return this.mWheels.getSpeed();
	}
	
	public double GetEncPos()
	{
		return this.mWheels.getPosition();
	}

	public void ToggleIntakeSpin() 
	{
		if(GetSetpoint() > 0.0)
			SetSpeed(mIntakeRpm);
		else
			SoftStop();
	}
    
    public float GetShooterDefaultSpeed()
    {
    	return mSetRpm;
    }

	public void StartIntake(boolean pos) {
		SetSpeed((pos ? 1 : -1) * mIntakeRpm);
	}
	
	public void SoftStop()
	{
		mWheels.changeControlMode(TalonControlMode.PercentVbus);
		mWheels.set(0);
	}
	
	public void HardStop()
	{
		SetSpeed(0);
	}

	@Override
	public void MakeCfgClassesNull() {
		if(mWheels != null)
			mWheels.delete();
		mWheels = null;
	}
	
	@Override
	public boolean Deserialize(CfgElement element) {
		
		mId = element.GetAttributeI("MotorId");
		
		mP = element.GetAttributeF("P");
		mI = element.GetAttributeF("I");
		mD = element.GetAttributeF("D");
		mF = element.GetAttributeF("F");
		
		mSetRpm = element.GetAttributeF("ShootRPM");
		mIntakeRpm = element.GetAttributeF("IntakeRPM");
		
		mEncToRot = element.GetAttributeI("EncSamplesPerRotation");
		
		Reconstruct();
		return true;
	}

	@Override
	public CfgElement Serialize(CfgElement blank, CfgDocument doc) {

		
		blank.SetAttribute("MotorId", mId);
		
		blank.SetAttribute("P", mP);
		blank.SetAttribute("I", mI);
		blank.SetAttribute("D", mD);
		blank.SetAttribute("F", mF);
		
		blank.SetAttribute("ShootRPM", mSetRpm);
		blank.SetAttribute("IntakeRPM", mIntakeRpm);
		
		blank.SetAttribute("EncSamplesPerRotation", mEncToRot);
		
		return blank;
	}

	@Override
	public String GetElementTitle() {
		return "Wheels";
	}
}

