package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *	This is designed to put the window in view, but NOT center it
 */
public class PutWindowInView extends Command {

	//private StrongholdWindow mLastWindow = new StrongholdWindow(-1, -1, -1, -1, -1, -1);
	private boolean mWillRun = true;
	private long mStartWaitTime = 0;
	
    public PutWindowInView() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	//this also, like "CenterWindowInView" does not require anything explicitly,
    	//	but does implicitly
    	requires(Robot.mDriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Init Put Window in View");
    	
    	mWillRun = Robot.mGlobShooterLatUtils.GetBestWindow().mCenterX == -1;
    	
    	if(mWillRun)
    	{
    		Robot.mDriveTrain.SetActiveController("Rotational");
    		Robot.mDriveTrain.UpdateRotationEncodersWithPixels();
    		Robot.mDriveTrain.enable();
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(System.currentTimeMillis() - mStartWaitTime > Robot.mGlobShooterLatUtils.GetUpdateLatency())
    	{
    		Robot.mDriveTrain.UpdateRotationEncodersWithPixels();
    		mStartWaitTime = 0;
    	}
    		
    	//if the encoder PID is on target, start wait timer
    	if(Robot.mDriveTrain.onTarget())
    	{
    		if(System.currentTimeMillis() - mStartWaitTime < 1)//make sure if it is already set, don't overwrite it
    			mStartWaitTime = System.currentTimeMillis();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.mGlobShooterLatUtils.GetBestWindow().mCenterX != -1;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Done Putting Window In View");
    	Robot.mDriveTrain.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
