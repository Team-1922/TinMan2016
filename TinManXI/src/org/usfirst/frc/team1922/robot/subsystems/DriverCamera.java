package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
import org.w3c.dom.Document;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriverCamera extends Subsystem implements CfgInterface {
    
	protected ConfigurableClass mCfgClass = new ConfigurableClass("DriverCamera", this);
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	protected Servo mRotServo;
	
	protected int mServoId;
	protected float mFullPos;
	protected float mFullNeg;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void SetFullPos()
    {
    	Set(mFullPos);
    }
    
    public void SetFullNeg()
    {
    	Set(mFullNeg);
    }
    
    public void Set(double pos)
    {
    	mRotServo.set(pos);
    }
    
    public double Get()
    {
    	return mRotServo.get();
    }

	@Override
	public boolean DeserializeInternal() {
		mServoId = Integer.parseInt(mCfgClass.GetAttribute("ServoId"));
		mFullPos = Float.parseFloat(mCfgClass.GetAttribute("FullPos"));
		mFullNeg = Float.parseFloat(mCfgClass.GetAttribute("FullNeg"));
		
		mRotServo = new Servo(mServoId);
		return true;
	}

	@Override
	public void SerializeInternal(Document doc) {
		mCfgClass.SetAttribute("ServoId", Integer.toString(mServoId));
		mCfgClass.SetAttribute("FullPos", Float.toString(mFullPos));
		mCfgClass.SetAttribute("FullNeg", Float.toString(mFullNeg));
		
	}

	@Override
	public ConfigurableClass GetCfgClass() {
		return mCfgClass;
	}

	@Override
	public void MakeCfgClassesNull() {
		if(mRotServo != null)
			mRotServo.free();
		
		mRotServo = null;
	}
}

