package org.usfirst.frc.team1922.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDrive extends CommandGroup {
    
	public enum Direction
	{
		kForward,
		kReverse
	}
	
    public  AutoDrive(Direction dir) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new EnsureSafeShooterAngle());
    	addSequential(new SetIntakePositionHigh());
    	
    	switch(dir)
    	{
    	case kForward:
    		addSequential(new DriveForward(3.5, 0.75, 0.75));
    		//addSequential(new DriveForwardDistance(84));
    		break;
    	case kReverse:
			addSequential(new DriveForward(3.5, -0.75, -0.75));
    		//addSequential(new DriveForwardDistance(-84));
    		break;
    	}
    	
        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
