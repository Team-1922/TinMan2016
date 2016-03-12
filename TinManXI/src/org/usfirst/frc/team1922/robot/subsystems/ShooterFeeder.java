package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgDocument;
import org.ozram1922.cfg.CfgElement;
import org.ozram1922.cfg.CfgInterface;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterFeeder extends Subsystem implements CfgInterface {
	    
	/*
	 * 
	 * Member Variables
	 * 
	 */
	protected Solenoid mFeedSolenoid;

	/*
	 * 
	 * Config Variables
	 * 
	 */
	protected int mFeedSol = 0;
	
	public ShooterFeeder()
	{
		
	}
	
	public void Reconstruct()
	{
		mFeedSolenoid = new Solenoid(mFeedSol);
	}
	
	public void SetSolenoid(boolean state)
	{
		mFeedSolenoid.set(state);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	
    @Override
	public void MakeCfgClassesNull()
	{
		if(mFeedSolenoid != null)
			mFeedSolenoid.free();
		mFeedSolenoid = null;
	}

	@Override
	public boolean Deserialize(CfgElement element) {
		mFeedSol = element.GetAttributeI("Sol");
		Reconstruct();
		return true;
	}

	@Override
	public CfgElement Serialize(CfgElement blank, CfgDocument doc) {
		blank.SetAttribute("Sol", mFeedSol);
		return blank;
	}

	@Override
	public String GetElementTitle() {
		return "Feeder";
	}
}

