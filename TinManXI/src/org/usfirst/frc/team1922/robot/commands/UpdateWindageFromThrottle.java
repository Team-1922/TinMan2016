package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Reset the windage to zero at the current throttle location
 */
public class UpdateWindageFromThrottle extends Command {

    public UpdateWindageFromThrottle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	//requires nothing
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.mGlobShooterLatUtils.SetWindage((int)((float)Robot.mGlobShooterLatUtils.GetCameraViewWidth() * ((Robot.oi.GetJoystick("OpStick").getThrottle() + 1)/2 - Robot.mGlobShooterLatUtils.GetZeroWindowPos())));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
