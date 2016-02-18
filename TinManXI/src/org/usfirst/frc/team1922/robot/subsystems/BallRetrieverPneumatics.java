package org.usfirst.frc.team1922.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallRetrieverPneumatics extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	protected Solenoid mShortStrokeSol;
	protected Solenoid mLongStrokeSol;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void Reconstruct(int shortId, int longId)
    {
    	mShortStrokeSol = new Solenoid(shortId);
    	mLongStrokeSol = new Solenoid(longId);
    }
    
    public void SetShortPos(boolean on)
    {
    	mShortStrokeSol.set(on);
    }
    
    public void SetLongPos(boolean on)
    {
    	mLongStrokeSol.set(on);
    }
    
    public void MakeCfgClassesNull()
    {
    	if(mShortStrokeSol != null)
    		mShortStrokeSol.free();
    	if(mLongStrokeSol != null)
    		mLongStrokeSol.free();
    	
    	mShortStrokeSol = null;
    	mLongStrokeSol = null;
    }
}

