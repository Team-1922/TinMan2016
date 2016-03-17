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
		CfgElement shooterAngleElement = element.GetNthChild(mShooterAngle.GetElementTitle(), 0);
		if(shooterAngleElement == null)
		{
			System.out.println("Failed to Load Angle Subelement");
		}
		else
		{
			mShooterAngle.Deserialize(shooterAngleElement);
		}

		//mAngleSolFront = Integer.parseInt(shooterAngleElement.getAttribute("FrontSol"));
		//mAngleSolRear = Integer.parseInt(shooterAngleElement.getAttribute("RearSol"));
		
		/*
		 * 
		 * Wheels Config Values
		 * 
		 */
		CfgElement shooterWheelsElement = element.GetNthChild(mShooterWheels.GetElementTitle(), 0);
		if(shooterWheelsElement == null)
		{
			System.out.println("Failed to Load Shooter Wheels Subelement");
		}
		else
		{
			mShooterWheels.Deserialize(shooterWheelsElement);
		}
		
		
		/*
		 * 
		 * Feed Config Values
		 * 
		 */
		CfgElement shooterFeederElement = element.GetNthChild(mShooterFeeder.GetElementTitle(), 0);
		if(shooterFeederElement == null)
		{
			System.out.println("Failed to Load Feeder Subelement");
		}
		else
		{
			mShooterFeeder.Deserialize(shooterFeederElement);
		}
		
		return true;
	}

	@Override
	public CfgElement Serialize(CfgElement element, CfgDocument doc) 
	{
		//Cfg for the Angle
		CfgElement shooterAngleElement = doc.CreateElement(mShooterAngle.GetElementTitle());
		mShooterAngle.Serialize(shooterAngleElement, doc);
		element.AppendChild(shooterAngleElement);
		
		
		//Cfg for the Wheels
		CfgElement shooterWheelsElement = doc.CreateElement("Wheels");
		mShooterAngle.Serialize(shooterWheelsElement, doc);
		element.AppendChild(shooterWheelsElement);
		
		//cfg for the Feeder
		CfgElement shooterFeederElement = doc.CreateElement("ShooterFeeder");
		mShooterFeeder.Serialize(shooterFeederElement, doc);
		element.AppendChild(shooterFeederElement);
		
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

