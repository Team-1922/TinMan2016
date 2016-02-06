package org.usfirst.frc.team1922.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterFeeder extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	protected Solenoid mFeedSolenoid;
	
	public ShooterFeeder()
	{
		
	}
	
	public void Reconstruct(int solId)
	{
		mFeedSolenoid = new Solenoid(solId);
	}
	
	public void MakeCfgClassesNull()
	{
		if(mFeedSolenoid != null)
			mFeedSolenoid.free();
		mFeedSolenoid = null;
	}
	
	public void SetSolenoid(boolean state)
	{
		mFeedSolenoid.set(state);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

