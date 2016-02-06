package org.usfirst.frc.team1922.robot.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *	This is desinged for a base-line reading for the angle potentiometer
 */
public class ConfigAnglePotentiometer extends CommandGroup {
    
    public  ConfigAnglePotentiometer() {

    	addSequential(new DriveAngleUntilStop());
    	
    	addSequential(new OverwriteNewAngleBaseline());
    }
}
