package org.usfirst.frc.team1922.robot.commands.tests;

import org.usfirst.frc.team1922.robot.commands.RotateDriveTrainRadians;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestRotationPID extends CommandGroup {
    
    public  TestRotationPID() {
    	addSequential(new RotateDriveTrainRadians(1.7));
    }
}
