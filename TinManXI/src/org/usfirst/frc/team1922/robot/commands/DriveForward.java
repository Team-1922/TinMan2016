package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {

    public DriveForward() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.mDriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.mDriveTrain.SetPower(.5, .5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return this.timeSinceInitialized() > 5;
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
