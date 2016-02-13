package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.OzMath;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Ultrasonic.Unit;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterAngleAnalog extends Subsystem {
    
	protected CANTalon mAngleMotor;
	protected Ultrasonic mDistanceFinder;
	protected float mAngleRatio;
	protected float mAngleOffset;
	protected float mAngleBaseline = 0.0f;
	
	public ShooterAngleAnalog()
	{
	}
	
	//mult ratio is in potValue per degree
	//horizAngle is in potValue's
	public void Reconstruct(int canId, float p, float i, float d, float multRatio, float horizAngle, int ping, int echo)
	{
		mDistanceFinder = new Ultrasonic(ping, echo, Unit.kInches);
		
		mAngleMotor = new CANTalon(canId);
		mAngleMotor.setFeedbackDevice(FeedbackDevice.AnalogPot);
		mAngleMotor.changeControlMode(TalonControlMode.Position);
		mAngleMotor.configPotentiometerTurns(10);
		
		mAngleMotor.reverseOutput(true);
		
		mAngleMotor.setProfile(0);
		mAngleMotor.setPID(p, i, d);
		
		//TODO: is this the correct way to do this?  How do limit switches work
		//	with the talon SRX?
		mAngleMotor.enableLimitSwitch(true, true);
		
		mAngleRatio	 = multRatio;
		mAngleOffset = horizAngle;
		

	}
	
	//angle is relative to horizontal (negative to get to feeding position)
	public void SetAngle(float angle)
	{
		float set = mAngleRatio * angle + mAngleBaseline + mAngleOffset;
		System.out.println("Setting Angle To:" + set);
		mAngleMotor.changeControlMode(TalonControlMode.Position);
		mAngleMotor.set(set);
	}
	
	public void SetSpeed(double d)
	{
		mAngleMotor.changeControlMode(TalonControlMode.PercentVbus);
		mAngleMotor.set(d);
	}
	
	//TODO: does this do what it should?  
	public boolean IsStopped()
	{
		return mAngleMotor.isFwdLimitSwitchClosed() || mAngleMotor.isRevLimitSwitchClosed();
	}
	
	public boolean OnTarget()
	{
		//System.out.println(mAngleMotor.getSetpoint());
		//return true;
		return OzMath.SigmaTest(mAngleMotor.getPosition(), mAngleMotor.getSetpoint(), .01);
		//the "sigma" here should be in voltage or degrees?
	}
	
	public void SetAngleBaseline()
	{
		mAngleBaseline = (float) mAngleMotor.getPosition();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	
	public void MakeCfgClassesNull()
	{
		if(mAngleMotor != null)
			mAngleMotor.delete();
		mAngleMotor = null;
	}
}

