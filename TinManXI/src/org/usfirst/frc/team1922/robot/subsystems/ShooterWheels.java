package org.usfirst.frc.team1922.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterWheels extends Subsystem {

	/*
	 * 
	 * Member Variables
	 * 
	 */
	protected CANTalon mWheels;
	protected float mRpsToEnc;
	
	/*
	 * 
	 * Member Functions
	 * 
	 */
	
	//rpmToEnc: the ratio to convert rps's to encoder samples
	public ShooterWheels()
	{
	}
	
	public void Reconstruct(int talonID, float p, float i, float d, float rpsToEnc)
	{
		mRpsToEnc = rpsToEnc;
		mWheels = new CANTalon(talonID);
		mWheels.setPID(p, i, d);
	}
	
	//Set the speed of the motor in rps (handled by PID control on the talon
	public void SetSpeed(double speed)
	{
		mWheels.setSetpoint(mRpsToEnc * speed);
	}

    public void initDefaultCommand() 
    {
    }


	public void MakeCfgClassesNull() {
		mWheels = null;
	}
}

