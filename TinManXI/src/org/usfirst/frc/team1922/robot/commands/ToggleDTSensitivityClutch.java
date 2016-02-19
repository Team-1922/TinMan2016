package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleDTSensitivityClutch extends Command {

    public ToggleDTSensitivityClutch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	//this does not need to bind up the drive train class
    	//requires(Robot.mDriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Robot.mDriveTrain.IsSensitivityClutchEnabled())
    		Robot.mDriveTrain.SetSensitivityClutch(.5);
    	else
    		Robot.mDriveTrain.SetSensitivityClutch(1.0);
    		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
