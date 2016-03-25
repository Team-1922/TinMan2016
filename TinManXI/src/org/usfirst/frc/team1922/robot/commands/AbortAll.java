package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AbortAll extends Command {

    public AbortAll() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.mBallRetriever);
    	requires(Robot.mDriveTrain);
    	requires(Robot.mShooter.GetShooterAngle());
    	requires(Robot.mShooter.GetShooterWheels());
    	requires(Robot.mShooter.GetShooterFeeder());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.mShooter.GetShooterWheels().SoftStop();
    	Robot.mDriveTrain.SetPower(0, 0);
    	Robot.mBallRetriever.GetMotorSubsystem().SetMotorSpeed(0);
    	Robot.mBallRetriever.GetPneumaticsSubsystem().SetShortPos(false);
    	Robot.mBallRetriever.GetPneumaticsSubsystem().SetLongPos(false);
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
