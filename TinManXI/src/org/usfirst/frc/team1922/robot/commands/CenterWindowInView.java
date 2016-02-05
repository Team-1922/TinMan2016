package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	This command is designed to be used when the window is in view, but is not centered, it will not run if the window is not in view
 */
public class CenterWindowInView extends Command {

	private boolean mWillRun = true;
    public CenterWindowInView() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	//this does not require anything perse, however EnableLateralPID WILL eat those subsystems up
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//don't do anything if the window is not in view
    	
    	mWillRun = Robot.mGlobShooterLatUtils.GetBestWindow().mCenterY == -1;
    	
    	if(mWillRun)
    	{
    		Robot.mShooter.EnableLateralPID();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.mShooter.GetLateralPID().onTarget() || !mWillRun;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.mShooter.DisableLateralPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
