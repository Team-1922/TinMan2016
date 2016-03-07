package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *	This command is designed to be used when the window is in view, but is not centered, it will not run if the window is not in view
 */
public class CenterWindowInView extends Command {

	//private StrongholdWindow mLastWindow = new StrongholdWindow(-1, -1, -1, -1, -1, -1);
	private boolean mWillRun = true;
	private long mStartWaitTime = 0;
	
    public CenterWindowInView() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.mDriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//don't do anything if the window is not in view
    	System.out.println("Start Centering Window");
    	mWillRun = Robot.mGlobShooterLatUtils.GetBestWindow().mCenterX != -1;
    	
    	if(mWillRun)
    	{
    		Robot.mDriveTrain.SetActiveController("Rotational");
    		Robot.mDriveTrain.UpdateRotationEncodersWithPixels();
    		Robot.mDriveTrain.enable();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(System.currentTimeMillis() - mStartWaitTime > 1000)
    	{
    		Robot.mDriveTrain.UpdateRotationEncodersWithPixels();
    		mStartWaitTime = 0;
    	}
    		
    	//if the encoder PID is on target, start wait timer
    	if(Robot.mDriveTrain.onTarget())
    	{
    		if(System.currentTimeMillis() - mStartWaitTime < 1)
    			mStartWaitTime = System.currentTimeMillis();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//what is the best way to do this, because "onTarget()" doesn't seem to work
        return Robot.mDriveTrain.AimingOnTarget();
    	//return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Done Centering Window");
    	Robot.mDriveTrain.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
