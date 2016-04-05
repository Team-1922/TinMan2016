package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgDocument;
import org.ozram1922.cfg.CfgElement;
import org.ozram1922.cfg.CfgInterface;

/**
 * 
 * This controls the switching between the Drive train and the ShovelLateral PID
 *
 */
public class Shooter implements CfgInterface {
	
	/*
	 * 
	 * Member Variables
	 * 
	 */
	protected ShooterAngleAnalog mShooterAngle;
	protected ShooterWheels mShooterWheels;
	protected ShooterFeeder mShooterFeeder;
    

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
		if(!element.DeserializeChild(mShooterAngle))
		{
			System.out.println("Failed to Load Angle Subelement");
			return false;
		}

		//mAngleSolFront = Integer.parseInt(shooterAngleElement.getAttribute("FrontSol"));
		//mAngleSolRear = Integer.parseInt(shooterAngleElement.getAttribute("RearSol"));
		
		/*
		 * 
		 * Wheels Config Values
		 * 
		 */
		if(!element.DeserializeChild(mShooterWheels))
		{
			System.out.println("Failed to Load Wheels Subelement");
			return false;
		}
		
		
		/*
		 * 
		 * Feed Config Values
		 * 
		 */
		if(!element.DeserializeChild(mShooterFeeder))
		{
			System.out.println("Failed to Load Feeder Subelement");
			return false;
		}
		
		return true;
	}

	@Override
	public CfgElement Serialize(CfgElement element, CfgDocument doc) 
	{
		//Cfg for the Angle
		element.SerializeChild(mShooterAngle, doc);		
		
		//Cfg for the Wheels
		element.SerializeChild(mShooterWheels, doc);
		
		//cfg for the Feeder
		element.SerializeChild(mShooterFeeder, doc);
		
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
	}
}

