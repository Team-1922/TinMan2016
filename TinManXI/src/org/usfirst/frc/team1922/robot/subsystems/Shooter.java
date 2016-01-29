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
    
	
	/*
	 * 
	 * Cfg Member Variables
	 * 
	 */
	protected float mAngleP;
	protected float mAngleI;
	protected float mAngleD;
	protected float mAngleF;
	protected int mAnglePotId;
	protected float mAnglePotMultRatio;
	protected float mAnglePotOffset;
	protected int mAngleMotorId;

	protected int mWheelsId;
	protected float mWheelsP;
	protected float mWheelsI;
	protected float mWheelsD;
	protected float mWheelsF;
	protected float mRpsToEnc;

	/*
	 * 
	 * Actual Member Functions
	 * 
	 */
	
	public void Reconstruct()
	{
		//load the shooter angle
		mShooterAngle = new ShooterAngle(mAngleP, mAngleI, mAngleD, mAngleF,
				mAnglePotId, mAnglePotMultRatio, mAnglePotOffset,
				mAngleMotorId);
		mShooterWheels = new ShooterWheels(mWheelsId, mWheelsP, mWheelsI, mWheelsD, mWheelsF, mRpsToEnc);
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

		Element shooterAngleElement = (Element) mCfgClass.GetNthChild("ShooterAngle", 0);
		
		mAngleP = Float.parseFloat(shooterAngleElement.getAttribute("P"));
		mAngleI = Float.parseFloat(shooterAngleElement.getAttribute("I"));
		mAngleD = Float.parseFloat(shooterAngleElement.getAttribute("D"));
		mAngleF = Float.parseFloat(shooterAngleElement.getAttribute("F"));
		
		mAnglePotId = Integer.parseInt(shooterAngleElement.getAttribute("PotId"));
		mAnglePotMultRatio = Float.parseFloat(shooterAngleElement.getAttribute("AngleTo5v"));
		mAnglePotOffset = Float.parseFloat(shooterAngleElement.getAttribute("PotOffset"));
		mAngleMotorId = Integer.parseInt(shooterAngleElement.getAttribute("MotorId"));

		
		
		Element shooterWheelsElement = (Element) mCfgClass.GetNthChild("ShooterWheels", 0);
		
		mWheelsId = Integer.parseInt(mCfgClass.GetAttribute("MotorId"));
		
		mWheelsP = Float.parseFloat(shooterWheelsElement.getAttribute("P"));
		mWheelsI = Float.parseFloat(shooterWheelsElement.getAttribute("I"));
		mWheelsD = Float.parseFloat(shooterWheelsElement.getAttribute("D"));
		mWheelsF = Float.parseFloat(shooterWheelsElement.getAttribute("F"));
		
		mRpsToEnc = Float.parseFloat(shooterWheelsElement.getAttribute("RpsToEncSamples"));
		
		Reconstruct();
		return true;
	}

	@Override
	public void SerializeInternal(Document doc) 
	{
		//Cfg for the Angle
		Element shooterAngleElement = doc.createElement("ShooterAngle");
		
		shooterAngleElement.setAttribute("P", Float.toString(mAngleP));
		shooterAngleElement.setAttribute("I", Float.toString(mAngleI));
		shooterAngleElement.setAttribute("D", Float.toString(mAngleD));
		shooterAngleElement.setAttribute("F", Float.toString(mAngleF));

		shooterAngleElement.setAttribute("PotId", Integer.toString(mAnglePotId));
		shooterAngleElement.setAttribute("AngleTo5v", Float.toString(mAnglePotMultRatio));
		shooterAngleElement.setAttribute("PotOffset", Float.toString(mAnglePotOffset));
		shooterAngleElement.setAttribute("MotorId", Integer.toString(mAngleMotorId));
		
		mCfgClass.AddChild(shooterAngleElement);
		
		
		//Cfg for the wheels
		Element shooterWheelsElement = doc.createElement("ShooterAngle");
		
		shooterWheelsElement.setAttribute("MotorId", Integer.toString(mWheelsId));
		
		shooterWheelsElement.setAttribute("P", Float.toString(mWheelsP));
		shooterWheelsElement.setAttribute("I", Float.toString(mWheelsI));
		shooterWheelsElement.setAttribute("D", Float.toString(mWheelsD));
		shooterWheelsElement.setAttribute("F", Float.toString(mWheelsF));
		
		shooterWheelsElement.setAttribute("RpsToEncSamples", Float.toString(mRpsToEnc));
		
		mCfgClass.AddChild(shooterWheelsElement);


		//cfg for the this class
	}

	@Override
	public ConfigurableClass GetCfgClass() {
		// TODO Auto-generated method stub
		return mCfgClass;
	}

	@Override
	public void MakeCfgClassesNull() {
		mShooterWheels = null;
		mShooterAngle = null;		
	}
}

