package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgDocument;
import org.ozram1922.cfg.CfgElement;
import org.ozram1922.cfg.CfgInterface;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * This controls the switching between the Drive train and the ShovelLateral PID
 *
 */
public class Shooter extends Subsystem implements CfgInterface {
	
	/*
	 * 
	 * Member Variables
	 * 
	 */
	protected ShooterAngleAnalog mShooterAngle;
	protected ShooterWheels mShooterWheels;
	protected ShooterFeeder mShooterFeeder;
	
	//protected UpdateLateralPIDSwitch mPIDUpdateCmd = new UpdateLateralPIDSwitch();
    
	
	/*
	 * 
	 * Cfg Member Variables
	 * 
	 */
	protected float mAngleP = 0.0f;
	protected float mAngleI = 0.0f;
	protected float mAngleD = 0.0f;
	protected float mAnglePotMultRatio = 1.0f;
	protected float mAnglePotOffset = 0.0f;
	protected int mAngleMotorId = 5;
	protected int mAngleUltraId = 0;
	
	protected int mFeedSol = 0;
	
	/*protected int mAngleSolFront = 0;
	protected int mAngleSolRear = 0;*/

	protected int mWheelsId = 6;
	protected float mWheelsP = 0.0f;
	protected float mWheelsI = 0.0f;
	protected float mWheelsD = 0.0f;
	protected float mWheelsF = 0.0f;
	protected int mWheelsEncToRot = 1;
	protected float mWheelsSetRpm = 0;
	protected float mWheelsIntakeRpm = 1;
	
	protected float mLateralP = 0.0f;
	protected float mLateralI = 0.0f;
	protected float mLateralD = 0.0f;
	protected float mLateralTolerance = 0.0f;

	/*
	 * 
	 * Actual Member Functions
	 * 
	 */
	
	public Shooter()
	{
		mShooterFeeder = new ShooterFeeder();
		mShooterAngle = new ShooterAngleAnalog();
		mShooterWheels = new ShooterWheels();
		//mShooterLateral = new ShooterLateral();
	}
	
	public void Reconstruct()
	{
		//load the shooter angle
		mShooterFeeder.Reconstruct(mFeedSol);
		mShooterAngle.Reconstruct(mAngleMotorId, 
				mAngleP, mAngleI, mAngleD, 
				mAnglePotMultRatio, mAnglePotOffset,
				mAngleUltraId);
		//mShooterAngle.Reconstruct(mAngleSolFront, mAngleSolRear);
		mShooterWheels.Reconstruct(mWheelsId, 
				mWheelsP, mWheelsI, mWheelsD, mWheelsF,
				mWheelsEncToRot);
		//mShooterLateral.Reconstruct(
		//		mLateralP, mLateralI, mLateralD, mLateralTolerance);
	}

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //setpoint: position to give
    /*public void SetAngleSetpoint(ShooterAngle.Position setpoint)
    {
    	if(mShooterAngle != null)
    		mShooterAngle.setPosition(setpoint);
    }*/
    
    /*public void EnableLateralPID()
    {
    	mPIDUpdateCmd.start();
    }
    
    public void DisableLateralPID()
    {
    	mPIDUpdateCmd.stop();
    }*/
    
    //sets the state of lateralPID control
    /*public PIDSubsystem GetLateralPID()
    {
    	return mShooterLateral;
    }*/
    
    //set the wheels speed in rpm
    public void SetWheelsSpeed(double speed)
    {
    	if(mShooterWheels != null)
    		mShooterWheels.SetSpeed(speed);
    }
    
    /*public void SetAngle(ShooterAngle.Position angle)
    {
    	mShooterAngle.setPosition(angle);
    }*/
    
    public ShooterFeeder GetShooterFeeder()
    {
    	return mShooterFeeder;
    }
    
    public ShooterAngleAnalog GetShooterAngle()
    {
    	return mShooterAngle;
    }
    
    public ShooterWheels GetShooterWheels()
    {
    	return mShooterWheels;
    }
    
    /*public ShooterLateral GetShooterLateral()
    {
    	return mShooterLateral;
    }*/
    
    
    public float GetShooterDefaultSpeed()
    {
    	return mWheelsSetRpm;
    }

	public void ToggleIntakeSpin() 
	{
		if(mShooterWheels.GetSetpoint() > 0.0)
			mShooterWheels.SetSpeed(mWheelsIntakeRpm);
		else
			mShooterWheels.SetSpeed(0);
	}
    
    /*
     * 
     * Overridden Member Functions
     * 
     */

	@Override
	public boolean Deserialize(CfgElement element) {

		/*
		 * 
		 * Angle Config Values
		 * 
		 */
		CfgElement shooterAngleElement = element.GetNthChild("Angle", 0);

		//mAngleSolFront = Integer.parseInt(shooterAngleElement.getAttribute("FrontSol"));
		//mAngleSolRear = Integer.parseInt(shooterAngleElement.getAttribute("RearSol"));

		mAngleUltraId = shooterAngleElement.GetAttributeI("UltraId");
		
		mAngleP = shooterAngleElement.GetAttributeF("P");
		mAngleI = shooterAngleElement.GetAttributeF("I");
		mAngleD = shooterAngleElement.GetAttributeF("D");
		mAngleMotorId = shooterAngleElement.GetAttributeI("MotorId");
		mAnglePotMultRatio = shooterAngleElement.GetAttributeF("AngleToNorm");
		mAnglePotOffset = shooterAngleElement.GetAttributeF("PotOffset");
		
		/*
		 * 
		 * Wheels Config Values
		 * 
		 */
		CfgElement shooterWheelsElement = element.GetNthChild("Wheels", 0);
		
		mWheelsId = shooterWheelsElement.GetAttributeI("MotorId");
		
		mWheelsP = shooterWheelsElement.GetAttributeF("P");
		mWheelsI = shooterWheelsElement.GetAttributeF("I");
		mWheelsD = shooterWheelsElement.GetAttributeF("D");
		mWheelsF = shooterWheelsElement.GetAttributeF("F");
		
		mWheelsSetRpm = shooterWheelsElement.GetAttributeF("ShootRPM");
		mWheelsIntakeRpm = shooterWheelsElement.GetAttributeF("IntakeRPM");
		
		mWheelsEncToRot = shooterWheelsElement.GetAttributeI("EncSamplesPerRotation");
				
		
		/*
		 * 
		 * Lateral Config Values
		 * 
		 */
		CfgElement shooterLateralElement = element.GetNthChild("Lateral", 0);
		
		mLateralP = shooterLateralElement.GetAttributeF("P");
		mLateralI = shooterLateralElement.GetAttributeF("I");
		mLateralD = shooterLateralElement.GetAttributeF("D");
		
		mLateralTolerance = shooterLateralElement.GetAttributeF("Tolerance");
		
		
		/*
		 * 
		 * Feed Config Values
		 * 
		 */
		CfgElement shooterFeederElement = element.GetNthChild("Feeder", 0);
		
		mFeedSol = shooterFeederElement.GetAttributeI("Sol");
		
		Reconstruct();
		return true;
	}

	@Override
	public CfgElement Serialize(CfgElement element, CfgDocument doc) 
	{
		//Cfg for the Angle
		CfgElement shooterAngleElement = doc.CreateElement("Angle");
		
		//shooterAngleElement.setAttribute("FrontSol", Float.toString(mAngleSolFront));
		//shooterAngleElement.setAttribute("RearSol", Float.toString(mAngleSolRear));

		shooterAngleElement.SetAttribute("UltraId", mAngleUltraId);
		
		shooterAngleElement.SetAttribute("P", mAngleP);
		shooterAngleElement.SetAttribute("I", mAngleI);
		shooterAngleElement.SetAttribute("D", mAngleD);
		
		shooterAngleElement.SetAttribute("MotorId", mAngleMotorId);

		shooterAngleElement.SetAttribute("AngleToNorm", mAnglePotMultRatio);
		shooterAngleElement.SetAttribute("PotOffset", mAnglePotOffset);
		
		
		element.AppendChild(shooterAngleElement);
		
		
		//Cfg for the wheels
		CfgElement shooterWheelsElement = doc.CreateElement("Wheels");
		
		shooterWheelsElement.SetAttribute("MotorId", mWheelsId);
		
		shooterWheelsElement.SetAttribute("P", mWheelsP);
		shooterWheelsElement.SetAttribute("I", mWheelsI);
		shooterWheelsElement.SetAttribute("D", mWheelsD);
		shooterWheelsElement.SetAttribute("F", mWheelsF);
		
		shooterWheelsElement.SetAttribute("ShootRPM", mWheelsSetRpm);
		shooterWheelsElement.SetAttribute("IntakeRPM", mWheelsIntakeRpm);
		
		shooterWheelsElement.SetAttribute("EncSamplesPerRotation", mWheelsEncToRot);
		
		element.AppendChild(shooterWheelsElement);

		
		//Cfg for the lateral adjustment (this is an interesting one, because it depends potentially on the drive train too)
		CfgElement shooterLateralElement = doc.CreateElement("ShooterLateral");
		
		shooterLateralElement.SetAttribute("P", mLateralP);
		shooterLateralElement.SetAttribute("I", mLateralI);
		shooterLateralElement.SetAttribute("D", mLateralD);
		shooterLateralElement.SetAttribute("Tolerance", mLateralTolerance);
		
		element.AppendChild(shooterLateralElement);
		
		//cfg for the feeder
		CfgElement shooterFeederElement = doc.CreateElement("ShooterFeeder");
		
		shooterFeederElement.SetAttribute("Sol", mFeedSol);
		
		element.AppendChild(shooterFeederElement);

		//cfg for the this class
		
		return element;
	}

	@Override
	public String GetElementTitle()
	{
		return "Shooter";
	}

	@Override
	public void MakeCfgClassesNull() {
		mShooterFeeder.MakeCfgClassesNull();
		mShooterAngle.MakeCfgClassesNull();
		mShooterWheels.MakeCfgClassesNull();
		//mShooterLateral.MakeCfgClassesNull();
	}

	public void StartIntake(boolean pos) {
		mShooterWheels.SetSpeed((pos ? 1 : -1) * mWheelsIntakeRpm);
	}
	
	public void StopIntake()
	{
		mShooterWheels.SetSpeed(0);
	}
}

