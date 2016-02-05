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
	
	/*
	 * 
	 * Member Functions
	 * 
	 */
	
	//rpmToEnc: the ratio to convert rps's to encoder samples
	public ShooterWheels()
	{
	}
	
	public void Reconstruct(int talonID, float p, float i, float d, int encUnitsPerRot)
	{
		mWheels = new CANTalon(talonID);
		mWheels.configEncoderCodesPerRev(encUnitsPerRot);
		mWheels.setPID(p, i, d);
	}
	
	//Set the speed of the motor in rpm (handled by PID control on the talon
	public void SetSpeed(double speed)
	{
		mWheels.setSetpoint(speed);
		mWheels.disable();
	}
	
	public void SpinDown()
	{
		mWheels.setSetpoint(0.0);
		mWheels.disable();
	}
	
	public boolean IsSpunUp()
	{
		return mWheels.getEncVelocity() == mWheels.getSetpoint();
	}

    public void initDefaultCommand() 
    {
    }


	public void MakeCfgClassesNull() {
		mWheels.delete();
		mWheels = null;
	}
}

