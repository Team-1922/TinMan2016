package org.usfirst.frc.team1922.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *This class is connected to ONLY the micro adjustment
 */
public class ShooterLateral extends PIDSubsystem {
	
	//zero is NOT centered, the center of the image is (i think 640 wide)
	protected float mPIDWindageAdj;

    // Initialize your subsystem here
    public ShooterLateral() {
    	super("Shooter Lateral", 0, 0, 0);//just because we have to
    }
    
    public void Reconstruct(float p, float i, float d, float windageAdj, float tolerance)
    {
    	mPIDWindageAdj = windageAdj;
    	getPIDController().setPID(p, i, d);
    	getPIDController().setAbsoluteTolerance(tolerance);
    	
    	setSetpoint(mPIDWindageAdj);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return 0.0;
    }
    
    protected void usePIDOutput(double output) {
    	//
    }
    
    //DON"T USE THIS.  This will ALWAYS be a constant value
    @Deprecated
	public void setSetpoint(double setpoint)
    {
    
    }


	public void MakeCfgClassesNull() {
		//this may do something
	}
}
