package org.usfirst.frc.team1922.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShovelTheFries extends CommandGroup {
    
    public  ShovelTheFries() {
		addSequential(new EnsureSafeShooterAngle());
		
		//This distance is currently unknown but will be updated with some testing
    	addSequential(new DriveForwardDistance(60));
    	addSequential(new SetIntakePositionLow());
    	
    	//Is 40 inches too little or too much??
    	addParallel(new DriveForwardDistance(60));
    	addParallel(new SetIntakePositionHigh());
    }
    }
