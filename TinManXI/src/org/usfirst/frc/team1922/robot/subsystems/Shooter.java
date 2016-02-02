package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem implements CfgInterface {
	
	/*
	 * 
	 * Member Variables
	 * 
	 */
	protected ConfigurableClass mCfgClass = new ConfigurableClass("Shooter", this);
	protected ShooterAngle mShooterAngle;
	protected ShooterWheels mShooterWheels;
	protected ShooterLateral mShooterLateral;
    
	
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

	protected int mWheelsId = 6;
	protected float mWheelsP = 0.0f;
	protected float mWheelsI = 0.0f;
	protected float mWheelsD = 0.0f;
	protected float mWheelsF = 0.0f;
	protected float mRpsToEnc = 1.0f;
	
	protected float mLateralP = 0.0f;
	protected float mLateralI = 0.0f;
	protected float mLateralD = 0.0f;
	protected float mWindageAdj = 0.0f;
	protected float mLateralTolerance = 0.0f;

	/*
	 * 
	 * Actual Member Functions
	 * 
	 */
	
	public Shooter()
	{
		mShooterAngle = new ShooterAngle();
		mShooterWheels = new ShooterWheels();
		mShooterLateral = new ShooterLateral();
	}
	
	public void Reconstruct()
	{
		//load the shooter angle
		mShooterAngle.Reconstruct(mAngleP, mAngleI, mAngleD,
				mAnglePotMultRatio, mAnglePotOffset,
				mAngleMotorId);
		mShooterWheels.Reconstruct(mWheelsId, 
				mWheelsP, mWheelsI, mWheelsD, 
				mRpsToEnc);
		mShooterLateral.Reconstruct(
				mLateralP, mLateralI, mLateralD, mWindageAdj, mLateralTolerance);
	}

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //setpoint: angle in degrees
    public void SetAngleSetpoint(double setpoint)
    {
    	if(mShooterAngle != null)
    		mShooterAngle.SetAngle(setpoint);
    }
    
    //set the wheels speed in rps
    public void SetWheelsSpeed(double speed)
    {
    	if(mShooterWheels != null)
    		mShooterWheels.SetSpeed(speed);
    }
    
    /*
     * 
     * Overridden Member Functions
     * 
     */

	@Override
	public boolean DeserializeInternal() {

		Element shooterAngleElement = (Element) mCfgClass.GetNthChild("Angle", 0);
		
		mAngleP = Float.parseFloat(shooterAngleElement.getAttribute("P"));
		mAngleI = Float.parseFloat(shooterAngleElement.getAttribute("I"));
		mAngleD = Float.parseFloat(shooterAngleElement.getAttribute("D"));
		
		mAnglePotMultRatio = Float.parseFloat(shooterAngleElement.getAttribute("AngleTo5v"));
		mAnglePotOffset = Float.parseFloat(shooterAngleElement.getAttribute("PotOffset"));
		mAngleMotorId = Integer.parseInt(shooterAngleElement.getAttribute("MotorId"));

		
		
		Element shooterWheelsElement = (Element) mCfgClass.GetNthChild("Wheels", 0);
		
		mWheelsId = Integer.parseInt(shooterWheelsElement.getAttribute("MotorId"));
		
		mWheelsP = Float.parseFloat(shooterWheelsElement.getAttribute("P"));
		mWheelsI = Float.parseFloat(shooterWheelsElement.getAttribute("I"));
		mWheelsD = Float.parseFloat(shooterWheelsElement.getAttribute("D"));
		
		mRpsToEnc = Float.parseFloat(shooterWheelsElement.getAttribute("RpsToEncSamples"));
				
		
		
		Element shooterLateralElement = (Element) mCfgClass.GetNthChild("Lateral", 0);
		
		mWindageAdj = Float.parseFloat(shooterLateralElement.getAttribute("Windage"));
		
		mLateralP = Float.parseFloat(shooterLateralElement.getAttribute("P"));
		mLateralI = Float.parseFloat(shooterLateralElement.getAttribute("I"));
		mLateralD = Float.parseFloat(shooterLateralElement.getAttribute("D"));
		
		Reconstruct();
		return true;
	}

	@Override
	public void SerializeInternal(Document doc) 
	{
		//Cfg for the Angle
		Element shooterAngleElement = doc.createElement("Angle");
		
		shooterAngleElement.setAttribute("P", Float.toString(mAngleP));
		shooterAngleElement.setAttribute("I", Float.toString(mAngleI));
		shooterAngleElement.setAttribute("D", Float.toString(mAngleD));

		shooterAngleElement.setAttribute("AngleTo5v", Float.toString(mAnglePotMultRatio));
		shooterAngleElement.setAttribute("PotOffset", Float.toString(mAnglePotOffset));
		shooterAngleElement.setAttribute("MotorId", Integer.toString(mAngleMotorId));
		
		mCfgClass.AddChild(shooterAngleElement);
		
		
		//Cfg for the wheels
		Element shooterWheelsElement = doc.createElement("Wheels");
		
		shooterWheelsElement.setAttribute("MotorId", Integer.toString(mWheelsId));
		
		shooterWheelsElement.setAttribute("P", Float.toString(mWheelsP));
		shooterWheelsElement.setAttribute("I", Float.toString(mWheelsI));
		shooterWheelsElement.setAttribute("D", Float.toString(mWheelsD));
		
		shooterWheelsElement.setAttribute("RpsToEncSamples", Float.toString(mRpsToEnc));
		
		mCfgClass.AddChild(shooterWheelsElement);

		
		//Cfg for the lateral adjustment (this is an interesting one, because it depends potentially on the drive train too)
		Element shooterLateralElement = doc.createElement("ShooterLateral");
		
		shooterLateralElement.setAttribute("Windage", Float.toString(mWindageAdj));
		shooterLateralElement.setAttribute("P", Float.toString(mLateralP));
		shooterLateralElement.setAttribute("I", Float.toString(mLateralI));
		shooterLateralElement.setAttribute("D", Float.toString(mLateralD));
		
		mCfgClass.AddChild(shooterLateralElement);

		//cfg for the this class
	}

	@Override
	public ConfigurableClass GetCfgClass() {
		// TODO Auto-generated method stub
		return mCfgClass;
	}

	@Override
	public void MakeCfgClassesNull() {
		mShooterAngle.MakeCfgClassesNull();
		mShooterWheels.MakeCfgClassesNull();
		mShooterLateral.MakeCfgClassesNull();
	}
}

