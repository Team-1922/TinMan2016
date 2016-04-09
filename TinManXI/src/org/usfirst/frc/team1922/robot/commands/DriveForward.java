package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {

	protected double mTime;
	protected double mLeftPower;
	protected double mRightPower;
    public DriveForward(double time, double leftPower, double rightPower) {
        // Use re quires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	mTime = time;
    	mLeftPower = leftPower;
    	mRightPower = rightPower;
    	requires(Robot.mDriveTrain);
    	setTimeout(mTime);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.mDriveTrain.SetPower(mLeftPower, mRightPower);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.mDriveTrain.SetPower(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
