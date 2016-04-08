package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.subsystems.DriveTrain.PIDMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardDistance extends Command {

	protected final double mDistanceInches;
	
    public DriveForwardDistance(double distanceInches) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	mDistanceInches = distanceInches;
    	requires(Robot.mDriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	System.out.println("Driving Forward: " + mDistanceInches);
    	Robot.mDriveTrain.PIDSwap(PIDMode.kLinear);
    	Robot.mDriveTrain.SetDeltaSetpoint(mDistanceInches);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.mDriveTrain.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	//do nothing
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.mDriveTrain.disable();
    }
}
