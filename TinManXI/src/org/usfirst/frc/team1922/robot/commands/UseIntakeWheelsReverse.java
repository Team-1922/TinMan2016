package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UseIntakeWheelsReverse extends Command {

    public UseIntakeWheelsReverse() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.mBallRetriever.GetMotorSubsystem());
    	requires(Robot.mShooter.GetShooterWheels());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.mBallRetriever.StartMotor(false);
    	Robot.mShooter.GetShooterWheels().StartIntake(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.mBallRetriever.StopMotor();
    	Robot.mShooter.GetShooterWheels().SoftStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
