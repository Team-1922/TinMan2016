package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.commands.shooter.SetShooterAngleAuto;
import org.usfirst.frc.team1922.robot.commands.shooter.ShootBall;
import org.usfirst.frc.team1922.robot.commands.shooter.SpinUpShooterWheelsCfg;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Autonomouse extends CommandGroup {
	public enum DefensePosition
	{
		k1,
		k2,
		k3,
		k4,
		k5;
		
		public int ToNumber()
		{
			switch(this)
			{
			case k1:
				return 1;
			case k2:
				return 2;
			case k3:
				return 3;
			case k4:
				return 4;
			case k5:
				return 5;
			}
			return 0;
		}
	}
	
	//as if we are in the center of the field, and looking towards the goal we score on
	public enum Direction
	{
		kLeft,
		kRight,
		kForward,
		kReverse
	}
    
    public Autonomouse(DefensePosition startPos, DefensePosition desiredPos, Direction direction) 
    {
    	//for distance, do final-initial
    	int unitDistance = desiredPos.ToNumber() - startPos.ToNumber();
    	
    	double distanceInches = (double)unitDistance * 50.37500;
    	
    	
    	//reorient if necessary
    	switch(direction)
    	{
		case kForward:
			if(unitDistance != 0)
				addSequential(new RotateDriveTrainRadians(Math.PI/2));
			break;
		case kLeft:
			unitDistance = -unitDistance;
			break;
		case kReverse:
			if(unitDistance != 0)
				addSequential(new RotateDriveTrainRadians(-Math.PI/2));
			break;
		case kRight:
			//do nothing
			break;
    	
    	}
    	
    	//set to go forward
    	addSequential(new DriveForwardDistance(distanceInches));
    	
    	//rotate to be facing the obstacle (in EVERY case, the robot WILL be facing right)
    	addSequential(new RotateDriveTrainRadians(-Math.PI/2));
    	
    	//go over the obstacle (use a factor of 2 to go twice as far the size of the obsticle)
    	addSequential(new DriveForwardDistance(94.5));//should this be a config value?
    	
    	//TODO: when should the shooter start spinning up?  
    	//TODO: Should we assume a certain shooter angle when setting up on field?
    	addSequential(new PutWindowInView());
    	addSequential(new SpinUpShooterWheelsCfg());
    	addSequential(new CenterWindowInView());
    	addSequential(new SetShooterAngleAuto());
    	addSequential(new ShootBall());
    }
}
