package org.usfirst.frc.team1922.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallRetrieverMotor extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	protected CANTalon mRetrievalMotor;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void Reconstruct(int id)
    {
    	mRetrievalMotor = new CANTalon(id);
    }
    
    public void SetMotorSpeed(double speed)
    {
    	mRetrievalMotor.set(speed);
    }
    
    public void MakeCfgClassesNull()
    {
    	mRetrievalMotor.delete();
    	mRetrievalMotor = null;
    }
}

