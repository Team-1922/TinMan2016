package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgDocument;
import org.ozram1922.cfg.CfgElement;
import org.ozram1922.cfg.CfgInterface;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *	
 */
public class BallRetriever extends Subsystem implements CfgInterface {
	
	
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
    
    public void StartMotor(boolean pos)
    {
    	mIntakeWheels.SetMotorSpeed((pos ? 1 : -1) * mMotorSpeed);
    }
    
    public void StopMotor()
    {
    	mIntakeWheels.SetMotorSpeed(0);
    }

	public void ToggleMotor() 
	{
		if(this.mIntakeWheels.GetMotorSpeed() > 0)
			StopMotor();
		else
			StartMotor(true);
		
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
	public boolean Deserialize(CfgElement element) {
		
		mMotorSpeed = element.GetAttributeF("MotorSpeed");
		mMotorId = element.GetAttributeI("MotorId");
		mShortStrokeId = element.GetAttributeI("ShortStrokeId");
		mLongStrokeId = element.GetAttributeI("LongStrokeId");
		
		Reconstruct();
		
		return true;
	}

	@Override
	public CfgElement Serialize(CfgElement element, CfgDocument doc) {
		element.SetAttribute("MotorSpeed", mMotorSpeed);
		element.SetAttribute("MotorId", mMotorId);
		element.SetAttribute("ShortStrokeId", mShortStrokeId);
		element.SetAttribute("LongStrokeId", mLongStrokeId);
		
		return element;
	}

	@Override
	public void MakeCfgClassesNull() {
		mIntakeWheels.MakeCfgClassesNull();
		mIntakePneumatics.MakeCfgClassesNull();
	}

	@Override
	public String GetElementTitle() {
		return "BallRetriever";
	}
}

