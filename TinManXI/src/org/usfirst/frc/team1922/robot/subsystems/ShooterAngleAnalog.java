package org.usfirst.frc.team1922.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterAngleAnalog extends Subsystem {
    
	protected CANTalon mAngleMotor;
	protected float mNumTurns;
	protected float mAngleOffset;
	protected float mAngleBaseline;
	
	public ShooterAngleAnalog()
	{
	}
	
	//numTurns is the number of turns of the shooter angle per 10 potentiometer turns
	//horizAngle is in degrees
	public void Reconstruct(int canId, float p, float i, float d, float numTurns, float horizAngle)
	{
		mAngleMotor = new CANTalon(canId);
		mAngleMotor.setPID(p, i, d);
		mAngleMotor.configPotentiometerTurns(10);
		mAngleMotor.changeControlMode(TalonControlMode.Speed);
		mNumTurns = numTurns;
		mAngleOffset = horizAngle;
	}
	
	//angle is relative to horizontal (negative to get to feeding position)
	public void SetAngle(float angle)
	{
		mAngleMotor.enable();
		mAngleMotor.changeControlMode(TalonControlMode.Speed);
		mAngleMotor.setSetpoint(mNumTurns * (angle + mAngleOffset) + mAngleBaseline);
	}
	
	public void SetSpeed(double d)
	{
		mAngleMotor.changeControlMode(TalonControlMode.PercentVbus);
		mAngleMotor.set(d);
	}
	
	//TODO: does this do what it should?  
	public boolean IsStopped()
	{
		return mAngleMotor.getForwardSoftLimit() == 1;
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
		mAngleMotor.delete();
		mAngleMotor = null;
	}
}

