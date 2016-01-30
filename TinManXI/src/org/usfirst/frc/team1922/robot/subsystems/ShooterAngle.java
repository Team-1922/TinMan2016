package org.usfirst.frc.team1922.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;

/**
 *
 */
public class ShooterAngle {

	/*
	 * 
	 * Member Variables
	 * 
	 */
	protected CANTalon mAngleMotor;
	
	protected float mPotMultRatio;
	protected float mPotOffset;
	
	
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
	
	public void Reconstruct(float p, float i, float d, 
			int potId, float potMultRatio, float potOffset,
			int talonID)
	{
		mPotMultRatio = potMultRatio;
		mPotOffset = potOffset;
		mAngleMotor = new CANTalon(talonID);
		mAngleMotor.setPID(p, i, d);
	}
	
	public void SetAngle(double deg)
	{
		mAngleMotor.setSetpoint((deg + mPotOffset) * mPotMultRatio);
	}
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
