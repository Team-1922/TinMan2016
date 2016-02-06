package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
import org.w3c.dom.Document;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *	
 */
public class BallRetriever extends Subsystem implements CfgInterface {
	
	protected CANTalon mRetrievalMotor;
	
	protected int mMotorId;
	public float kMotorSpeed;
	
	protected ConfigurableClass mCfgClass;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    
    public void Reconstruct()
    {
    	mRetrievalMotor = new CANTalon(mMotorId);
    }
    
    public void StartMotor()
    {
    	mRetrievalMotor.set(kMotorSpeed);
    }
    
    public void StopMotor()
    {
    	mRetrievalMotor.set(kMotorSpeed);
    }

	@Override
	public boolean DeserializeInternal() {
		kMotorSpeed = Float.parseFloat(mCfgClass.GetAttribute("MotorSpeed"));
		mMotorId = Integer.parseInt(mCfgClass.GetAttribute("MotorId"));
		
		return true;
	}

	@Override
	public void SerializeInternal(Document doc) {
		mCfgClass.SetAttribute("MotorSpeed", Float.toString(kMotorSpeed));
		mCfgClass.SetAttribute("MotorId", Integer.toString(mMotorId));
		
	}

	@Override
	public ConfigurableClass GetCfgClass() {
		// TODO Auto-generated method stub
		return mCfgClass;
	}

	@Override
	public void MakeCfgClassesNull() {
		// TODO Auto-generated method stub
		mRetrievalMotor.delete();
		mRetrievalMotor = null;
	}
}

