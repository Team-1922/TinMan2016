package org.usfirst.frc.team1922.robot.commands.shooter;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	When this command ends, the wheels are STILL SPINNING
 */
public class SpinUpShooterWheels extends Command {

	private final double mRPM;
	
    public SpinUpShooterWheels(double rpm) {
    	System.out.println("Shooter Wheels Init: " + rpm);
    	mRPM = rpm;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.mShooter.GetShooterWheels());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Shooter Wheels Spinning Up...");
    	Robot.mShooter.SetWheelsSpeed(mRPM);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.mShooter.GetShooterWheels().IsSpunUp();
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
