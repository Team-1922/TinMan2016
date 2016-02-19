package org.usfirst.frc.team1922.robot.subsystems;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class DriveTrainRotational extends PIDSubsystem {
	
	//this is in inches
	protected float mTolerance;
	
    // Initialize your subsystem here
    public DriveTrainRotational() {
    	super(0, 0, 0);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void Reconstruct(float p, float i, float d, float toleranceInches)
    {
    	getPIDController().setPID(p, i, d);
    	mTolerance = toleranceInches;
    	
    }
    
    public void initDefaultCommand() 
    {
    }
    
    protected double returnPIDInput() 
    {
    	return 0.0;
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	Robot.mDriveTrain.SetPower(output, -output);
    }
}
