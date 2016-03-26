package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EnsureSafeShooterAngle extends Command {

    public EnsureSafeShooterAngle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.mShooter.GetShooterAngle());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	/*if(Robot.mShooter.GetShooterAngle().GetAngle() < Robot.mShooter.GetShooterAngle().GetMinSafeAngle())
    		Robot.mShooter.GetShooterAngle().SetAngle(Robot.mShooter.GetShooterAngle().GetMinSafeAngle());  	
    	else if (Robot.mShooter.GetShooterAngle().GetAngle() > Robot.mShooter.GetShooterAngle().GetMaxSafeAngle())
    		Robot.mShooter.GetShooterAngle().SetAngle(Robot.mShooter.GetShooterAngle().GetMaxSafeAngle());*/
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//if(Robot.mShooter.GetShooterAngle().IsWithinSafeRange())
    		return true;
    	//else
    	//	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.mShooter.GetShooterAngle().StopPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
