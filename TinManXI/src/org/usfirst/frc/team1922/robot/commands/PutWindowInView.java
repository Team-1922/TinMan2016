package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	This is designed to put the window in view, but NOT center it
 */
public class PutWindowInView extends Command {

	private boolean mWillRun = true;
    public PutWindowInView() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	//this also, like "CenterWindowInView" does not require anything explicitly,
    	//	but does implicitly
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	mWillRun = Robot.mGlobShooterLatUtils.GetBestWindow().mCenterX == 1;
    	
    	if(mWillRun)
    		Robot.mShooter.EnableLateralPID();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.mGlobShooterLatUtils.GetBestWindow().mCenterX != 1;
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
