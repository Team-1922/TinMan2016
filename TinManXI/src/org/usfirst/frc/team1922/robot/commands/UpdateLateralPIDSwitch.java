package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UpdateLateralPIDSwitch extends Command {

    public UpdateLateralPIDSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

    	//this does not require anything, so we can do things in parallel without
    	//	interrupting any other commands
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//are we "OnTarget" for the drive train?
    	if(Robot.mDriveTrain.onTarget())
    	{
    		Robot.mShooter.GetLateralPID().enable();
    	}
    	else
    	{
    		//make sure this is enabled if the drive train is not on target
    		Robot.mDriveTrain.enable();
    		
    		//note that this does not explicitly disable the mShooter Lateral PID
    	}
    	
    	//are we "OnTarget" for the Micro-adjustments?
    	if(!Robot.mShooter.GetLateralPID().onTarget())
    	{
    		Robot.mShooter.GetLateralPID().enable();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//this never stops, as it is not actually a 'lineup' command
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.mShooter.GetLateralPID().disable();
    	Robot.mDriveTrain.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    //this is just to explicitly stop the thing from an external source
    public void stop()
    {
    	end();
    }
}
