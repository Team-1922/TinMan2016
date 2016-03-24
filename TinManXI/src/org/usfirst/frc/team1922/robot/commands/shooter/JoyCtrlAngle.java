package org.usfirst.frc.team1922.robot.commands.shooter;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoyCtrlAngle extends Command {

    public JoyCtrlAngle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.mShooter.GetShooterAngle());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//have a hardcoded threshold for the angle
    	double stickVal = Robot.oi.GetJoystick("OpStick").getY();
    	//if(Math.abs(stickVal) > .15)
    	//{
    		Robot.mShooter.GetShooterAngle().SetSpeed(stickVal);
    	//}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.mShooter.GetShooterAngle().SetSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
