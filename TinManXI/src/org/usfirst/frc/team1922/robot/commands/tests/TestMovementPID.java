package org.usfirst.frc.team1922.robot.commands.tests;

import org.usfirst.frc.team1922.robot.commands.DriveForwardDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestMovementPID extends CommandGroup {
    
    public  TestMovementPID() {
    	addSequential(new DriveForwardDistance(18));
    }
}
