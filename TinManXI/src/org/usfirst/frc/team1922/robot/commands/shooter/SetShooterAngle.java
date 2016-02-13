package org.usfirst.frc.team1922.robot.commands.shooter;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */


public class SetShooterAngle extends Command {

		
	protected float mAngle;
    public SetShooterAngle(float angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	mAngle = angle;
    	
    	requires(Robot.mShooter.GetShooterAngle());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("SetShooterAngle");
    	Robot.mShooter.GetShooterAngle().SetAngle(mAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.mShooter.GetShooterAngle().OnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("End SetShooterAngle");
    	//do this to keep the motor from "growling"
    	Robot.mShooter.GetShooterAngle().SetSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
