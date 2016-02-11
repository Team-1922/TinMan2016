package org.usfirst.frc.team1922.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
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
	
	public void Reconstruct(int talonID, float p, float i, float d, float f, int encUnitsPerRot)
	{
		System.out.println("Reconstruct Shooter Wheels: " + talonID + "," + p + "," + i + "," + d + "," + f + "," + encUnitsPerRot);
		mWheels = new CANTalon(talonID);
		mWheels.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		mWheels.reverseSensor(true);
		mWheels.configEncoderCodesPerRev(encUnitsPerRot);
		

		mWheels.configNominalOutputVoltage(+0.0f, -0.0f);
		mWheels.configPeakOutputVoltage(+12.0f, 0.0f);
		
		mWheels.setProfile(0);
		mWheels.setPID(p, i, d);
		mWheels.setF(f);
	}
	
	//Set the speed of the motor in rpm (handled by PID control on the talon
	public void SetSpeed(double speed)
	{
		System.out.println("Speed: " + speed);
		mWheels.changeControlMode(TalonControlMode.Speed);
		//mWheels.enable();
		mWheels.set(speed);
		//mWheels.disable();
	}
	
	public CANTalon GetController()
	{
		return mWheels;
	}
	
	public double GetSetpoint()
	{
		return mWheels.getSetpoint();
	}
	
	public void SpinDown()
	{
		mWheels.set(0.0);
		mWheels.setEncPosition(0);//zero this out so the value doesn't get too large
		//mWheels.disable();
	}
	
	public double GetP()
	{
		return mWheels.getP();
	}
	
	public double GetI()
	{
		return mWheels.getI();
	}
	
	public double GetD()
	{
		return mWheels.getD();
	}
	
	public double GetF()
	{
		return mWheels.getF();
	}
	
	public void SetPID(double P, double I, double D, double F)
	{
		mWheels.setPID(P, I, D);
		mWheels.setF(F);
	}
	
	public boolean IsSpunUp()
	{
		return mWheels.getEncVelocity() == mWheels.getSetpoint();
	}

    public void initDefaultCommand() 
    {
    }


	public void MakeCfgClassesNull() {
		if(mWheels != null)
			mWheels.delete();
		mWheels = null;
	}
}

