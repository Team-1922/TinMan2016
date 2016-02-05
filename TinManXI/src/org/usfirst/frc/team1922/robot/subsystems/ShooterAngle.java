package org.usfirst.frc.team1922.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 */
public class ShooterAngle {

	Solenoid mFeedSol;
	Solenoid mAngleSolFront;
	Solenoid mAngleSolRear;
	
	enum Position
	{
		kIntake,
		kLow,
		kMed,
		kHigh
	}
	
	enum SolTypes
	{
		kFeed,
		kAngleFront,
		kAngleRear
	}
	
	/*
	 * 
	 * Member Variables
	 * 
	 */
	//protected CANTalon mAngleMotor;
	
	//protected float mPotMultRatio;
	//protected float mPotOffset;
	
	
	/*
	 * 
	 * Actual Member Functions (This will be frequently deleted and recreated
	 * 
	 */
	
	/*
	 * 
	 * potMultRatio: the ratio to turn degrees into voltage
	 * potOffset: the offset (in degrees) to get the ratio to align with 0
	 * 
	 */
	ShooterAngle()
	{
	}
	
	//default solenoids are RETRACTED when false
	public void Reconstruct(int feedSol, int angleSolFront, int angleSolRear)
	{
		mFeedSol = new Solenoid(feedSol);
		mAngleSolFront = new Solenoid(angleSolFront);
		mAngleSolRear = new Solenoid(angleSolRear);
		//mPotMultRatio = potMultRatio;
		//mPotOffset = potOffset;
		//mAngleMotor = new CANTalon(talonID);
		//mAngleMotor.setPID(p, i, d);
	}
	
	public void setPosition(Position pos)
	{
		//mAngleMotor.setSetpoint((deg + mPotOffset) * mPotMultRatio);
		switch(pos)
		{
		case kHigh:
			mAngleSolFront.set(false);
			mAngleSolRear.set(false);
			break;
		case kIntake:
			mAngleSolFront.set(true);
			mAngleSolRear.set(true);
			break;
		case kLow:
			mAngleSolFront.set(true);
			mAngleSolRear.set(false);
			break;
		case kMed:
			mAngleSolFront.set(false);
			mAngleSolRear.set(true);
			break;
		default:
			break;
		
		}
	}
	
	public void SetSol(SolTypes type, boolean val)
	{
		switch(type)
		{
		case kAngleFront:
			mAngleSolFront.set(val);
			break;
		case kAngleRear:
			mAngleSolRear.set(val);
			break;
		case kFeed:
			mFeedSol.set(val);
			break;
		default:
			break;
		
		}
	}
	
	public void SetFeedSolenoid(boolean enabled)
	{
		mFeedSol.set(enabled);
	}
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }


	public void MakeCfgClassesNull() {
		mFeedSol = null;
		mAngleSolFront = null;
		mAngleSolRear = null;
		//mAngleMotor = null;
	}
}
