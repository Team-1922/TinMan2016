package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class NonAutoShooterCameraAssistant extends Command {

    public NonAutoShooterCameraAssistant() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.mNonAutoShootCam);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.mNonAutoShootCam.StartCapture();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.mNonAutoShootCam.UpdateFrame();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.mNonAutoShootCam.StopCapture();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
