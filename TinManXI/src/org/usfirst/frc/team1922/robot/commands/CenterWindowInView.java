package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.subsystems.StrongholdWindow;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	This command is designed to be used when the window is in view, but is not centered, it will not run if the window is not in view
 */
public class CenterWindowInView extends Command {

	//private StrongholdWindow mLastWindow = new StrongholdWindow(-1, -1, -1, -1, -1, -1);
	private boolean mWillRun = true;
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
    		Robot.mDriveTrain.enable();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//make sure we wait for the update to come before continuing
    	/*if(mLastWindow == Robot.mGlobShooterLatUtils.GetBestWindow())
    	{
    		Robot.mDriveTrain.disable();
    	}
    	else
    	{
    		Robot.mDriveTrain.enable();
    	}*/
    	System.out.println(Robot.mDriveTrain.getPIDController().getError());
    	
    	//reset the "i" value if we are close to the setpoint to avoid huge oscilation
    	/*if(Math.abs(Robot.mDriveTrain.getPIDController().getError()) > Robot.mDriveTrain.GetTolerance() * 5.0)
    	{
    		Robot.mDriveTrain.getPIDController().reset();
    		Robot.mDriveTrain.getPIDController().enable();
    	}*/
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//what is the best way to do this, because "onTarget()" doesn't seem to work
        return Math.abs(Robot.mDriveTrain.getPIDController().getError()) < Robot.mDriveTrain.GetTolerance() || !mWillRun;
    	//return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Done Centering WIndow");
    	Robot.mDriveTrain.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
