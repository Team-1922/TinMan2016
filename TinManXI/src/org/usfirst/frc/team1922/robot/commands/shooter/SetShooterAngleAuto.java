package org.usfirst.frc.team1922.robot.commands.shooter;

import org.ozram1922.OzMath;
import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Sets the shooter angle based on the ultrasonic distance
 */
public class SetShooterAngleAuto extends Command {

    public SetShooterAngleAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.mShooter.GetShooterAngle());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.mShooter.GetShooterAngle().SetAngle((float)OzMath.GetTrajAngle(Robot.mShooter.GetShooterAngle().GetUltraDistance()));
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
    	Robot.mShooter.GetShooterAngle().SetSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
