package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateDriveTrainRadians extends Command {

	protected final double mRadians;
    public RotateDriveTrainRadians(double radians) {
    	mRadians = radians;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.mDriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.mDriveTrain.PIDSwap(2);
    	
    	//make sure to convert the radians into inches so the encoders respond correctly
    	Robot.mDriveTrain.setSetpoint(mRadians * Robot.mDriveTrain.GetRotationRadius());
    	Robot.mDriveTrain.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.mDriveTrain.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.mDriveTrain.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
