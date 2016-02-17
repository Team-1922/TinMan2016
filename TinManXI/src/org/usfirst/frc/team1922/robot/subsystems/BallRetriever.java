package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
import org.w3c.dom.Document;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *	
 */
public class BallRetriever extends Subsystem implements CfgInterface {

	protected ConfigurableClass mCfgClass = new ConfigurableClass("BallRetriever", this);
	
	
	protected BallRetrieverMotor mIntakeWheels;
	protected BallRetrieverPneumatics mIntakePneumatics;
	
	protected int mMotorId;
	protected int mShortStrokeId;
	protected int mLongStrokeId;
	public float mMotorSpeed;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    
    public BallRetriever()
    {
    	mIntakeWheels = new BallRetrieverMotor();
    	mIntakePneumatics = new BallRetrieverPneumatics();
    }
    
    public void Reconstruct()
    {
    	mIntakeWheels.Reconstruct(mMotorId);
    	mIntakePneumatics.Reconstruct(mShortStrokeId, mLongStrokeId);
    }
    
    public void StartMotor()
    {
    	mIntakeWheels.SetMotorSpeed(mMotorSpeed);
    }
    
    public void StopMotor()
    {
    	mIntakeWheels.SetMotorSpeed(0);
    }
    
    public void SetShortStroke(boolean on)
    {
    	mIntakePneumatics.SetShortPos(on);
    }
    
    public void SetLongStroke(boolean on)
    {
    	mIntakePneumatics.SetLongPos(on);
    }
    
    public BallRetrieverMotor GetMotorSubsystem()
    {
    	return mIntakeWheels;
    }
    
    public BallRetrieverPneumatics GetPneumaticsSubsystem()
    {
    	return mIntakePneumatics;
    }

	@Override
	public boolean DeserializeInternal() {
		mMotorSpeed = Float.parseFloat(mCfgClass.GetAttribute("MotorSpeed"));
		mMotorId = Integer.parseInt(mCfgClass.GetAttribute("MotorId"));
		mShortStrokeId = Integer.parseInt(mCfgClass.GetAttribute("ShortStrokeId"));
		mLongStrokeId = Integer.parseInt(mCfgClass.GetAttribute("LongStrokeId"));
		
		return true;
	}

	@Override
	public void SerializeInternal(Document doc) {
		mCfgClass.SetAttribute("MotorSpeed", Float.toString(mMotorSpeed));
		mCfgClass.SetAttribute("MotorId", Integer.toString(mMotorId));
		mCfgClass.SetAttribute("ShortStrokeId", Integer.toString(mShortStrokeId));
		mCfgClass.SetAttribute("LongStrokeId", Integer.toString(mLongStrokeId));
		
	}

	@Override
	public ConfigurableClass GetCfgClass() {
		// TODO Auto-generated method stub
		return mCfgClass;
	}

	@Override
	public void MakeCfgClassesNull() {
		// TODO Auto-generated method stub
		mIntakeWheels.MakeCfgClassesNull();
		mIntakePneumatics.MakeCfgClassesNull();
	}
}

