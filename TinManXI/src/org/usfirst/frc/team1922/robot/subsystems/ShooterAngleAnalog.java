package org.usfirst.frc.team1922.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterAngleAnalog extends Subsystem {
    
	protected CANTalon mAngleMotor;
	protected float mNumTurns;
	protected float mAngleOffset;
	
	public ShooterAngleAnalog()
	{
	}
	
	//numTurns is the number of turns of the shooter angle per 10 potentiometer turns
	public void Reconstruct(int canId, float p, float i, float d, float numTurns, float horizAngle)
	{
		mAngleMotor = new CANTalon(canId);
		mAngleMotor.setPID(p, i, d);
		mAngleMotor.configPotentiometerTurns(10);
		mNumTurns = numTurns;
		mAngleOffset = horizAngle;
	}
	
	//angle is relative to horizontal (negative to get to feeding position)
	public void SetAngle(float angle)
	{
		mAngleMotor.enable();
		mAngleMotor.setSetpoint(angle * mNumTurns + mAngleOffset);
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

