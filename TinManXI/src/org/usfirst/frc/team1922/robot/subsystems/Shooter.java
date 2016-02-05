package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
import org.usfirst.frc.team1922.robot.commands.UpdateLateralPIDSwitch;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
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
	protected ConfigurableClass mCfgClass = new ConfigurableClass("Shooter", this);
	protected ShooterAngle mShooterAngle;
	protected ShooterWheels mShooterWheels;
	protected ShooterLateral mShooterLateral;
	
	protected UpdateLateralPIDSwitch mPIDUpdateCmd = new UpdateLateralPIDSwitch();
    
	
	/*
	 * 
	 * Cfg Member Variables
	 * 
	 */
	/*protected float mAngleP = 0.0f;
	protected float mAngleI = 0.0f;
	protected float mAngleD = 0.0f;
	protected float mAnglePotMultRatio = 1.0f;
	protected float mAnglePotOffset = 0.0f;
	protected int mAngleMotorId = 5;*/
	
	protected int mAngleFeedSol = 0;
	protected int mAngleSolFront = 0;
	protected int mAngleSolRear = 0;

	protected int mWheelsId = 6;
	protected float mWheelsP = 0.0f;
	protected float mWheelsI = 0.0f;
	protected float mWheelsD = 0.0f;
	protected float mWheelsF = 0.0f;
	protected int mWheelsEncToRot = 1;
	
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
		mShooterAngle = new ShooterAngle();
		mShooterWheels = new ShooterWheels();
		mShooterLateral = new ShooterLateral();
	}
	
	public void Reconstruct()
	{
		//load the shooter angle
		mShooterAngle.Reconstruct(mAngleFeedSol, mAngleSolFront, mAngleSolRear);
		mShooterWheels.Reconstruct(mWheelsId, 
				mWheelsP, mWheelsI, mWheelsD, 
				mWheelsEncToRot);
		mShooterLateral.Reconstruct(
				mLateralP, mLateralI, mLateralD, mLateralTolerance);
	}

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //setpoint: position to give
    public void SetAngleSetpoint(ShooterAngle.Position setpoint)
    {
    	if(mShooterAngle != null)
    		mShooterAngle.setPosition(setpoint);
    }
    
    public void EnableLateralPID()
    {
    	mPIDUpdateCmd.start();
    }
    
    public void DisableLateralPID()
    {
    	mPIDUpdateCmd.stop();
    }
    
    //sets the state of lateralPID control
    public PIDSubsystem GetLateralPID()
    {
    	return mShooterLateral;
    }
    
    //set the wheels speed in rpm
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
		
		mAngleFeedSol = Integer.parseInt(shooterAngleElement.getAttribute("FeedSol"));
		mAngleSolFront = Integer.parseInt(shooterAngleElement.getAttribute("FrontSol"));
		mAngleSolRear = Integer.parseInt(shooterAngleElement.getAttribute("RearSol"));

		
		
		Element shooterWheelsElement = (Element) mCfgClass.GetNthChild("Wheels", 0);
		
		mWheelsId = Integer.parseInt(shooterWheelsElement.getAttribute("MotorId"));
		
		mWheelsP = Float.parseFloat(shooterWheelsElement.getAttribute("P"));
		mWheelsI = Float.parseFloat(shooterWheelsElement.getAttribute("I"));
		mWheelsD = Float.parseFloat(shooterWheelsElement.getAttribute("D"));
		
		mWheelsEncToRot = Integer.parseInt(shooterWheelsElement.getAttribute("EncSamplesPerRotation"));
				
		
		
		Element shooterLateralElement = (Element) mCfgClass.GetNthChild("Lateral", 0);
		
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
		
		shooterAngleElement.setAttribute("FeedSol", Float.toString(mAngleFeedSol));
		shooterAngleElement.setAttribute("FrontSol", Float.toString(mAngleSolFront));
		shooterAngleElement.setAttribute("RearSol", Float.toString(mAngleSolRear));
		
		mCfgClass.AddChild(shooterAngleElement);
		
		
		//Cfg for the wheels
		Element shooterWheelsElement = doc.createElement("Wheels");
		
		shooterWheelsElement.setAttribute("MotorId", Integer.toString(mWheelsId));
		
		shooterWheelsElement.setAttribute("P", Float.toString(mWheelsP));
		shooterWheelsElement.setAttribute("I", Float.toString(mWheelsI));
		shooterWheelsElement.setAttribute("D", Float.toString(mWheelsD));
		
		shooterWheelsElement.setAttribute("EncSamplesPerRotation", Integer.toString(mWheelsEncToRot));
		
		mCfgClass.AddChild(shooterWheelsElement);

		
		//Cfg for the lateral adjustment (this is an interesting one, because it depends potentially on the drive train too)
		Element shooterLateralElement = doc.createElement("ShooterLateral");
		
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

