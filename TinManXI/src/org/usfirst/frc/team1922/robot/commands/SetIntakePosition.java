package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetIntakePosition extends Command {

	public enum IntakePosition
	{
		kHigh,
		kLow,
		kMedHigh,
		kMedLow
	}
	
	IntakePosition mIntakePos;
	
    public SetIntakePosition(IntakePosition pos) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.mBallRetriever.GetPneumaticsSubsystem());
    	mIntakePos = pos;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	switch(mIntakePos)
    	{
    	default:
		case kHigh:
			Robot.mBallRetriever.SetShortStroke(false);
			Robot.mBallRetriever.SetLongStroke(false);
			break;
		case kLow:
			Robot.mBallRetriever.SetShortStroke(true);
			Robot.mBallRetriever.SetLongStroke(true);
			break;
		case kMedHigh:
			Robot.mBallRetriever.SetShortStroke(true);
			Robot.mBallRetriever.SetLongStroke(false);
			break;
		case kMedLow:
			Robot.mBallRetriever.SetShortStroke(false);
			Robot.mBallRetriever.SetLongStroke(true);
			break;
    	}
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
