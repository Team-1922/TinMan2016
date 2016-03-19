package org.usfirst.frc.team1922.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MovementAutonomouse extends CommandGroup {
    
    public  MovementAutonomouse() {
    	addSequential(new DriveForwardDistance(36));
    }
}
