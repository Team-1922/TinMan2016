package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgDocument;
import org.ozram1922.cfg.CfgElement;
import org.ozram1922.cfg.CfgInterface;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDSwag extends Subsystem implements CfgInterface
{
	
	protected Relay mShooterLight;
	protected int mShooterLightID;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void SetShooterLight(boolean on)
    {
    	mShooterLight.set(on ? Value.kOn : Value.kOff);
    }
    
    public boolean IsShooterLightOn()
    {
    	return mShooterLight.get() == Value.kOn ? true : false;
    }
    
	@Override
	public boolean Deserialize(CfgElement element) {
		mShooterLightID = element.GetAttributeI("ShooterLED");
		
		mShooterLight = new Relay(mShooterLightID);
		return true;
	}

	@Override
	public CfgElement Serialize(CfgElement blank, CfgDocument doc) {
		blank.SetAttribute("ShooterLED", mShooterLightID);
		return blank;
	}

	@Override
	public void MakeCfgClassesNull() {
		if(mShooterLight != null)
			mShooterLight.free();
	}

	@Override
	public String GetElementTitle() {
		return "LEDSwag";
	}
}

