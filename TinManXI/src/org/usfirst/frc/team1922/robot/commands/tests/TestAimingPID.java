package org.usfirst.frc.team1922.robot.commands.tests;

import org.usfirst.frc.team1922.robot.commands.CenterWindowInView;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestAimingPID extends CommandGroup {
    
    public  TestAimingPID() {
    	addSequential(new CenterWindowInView());
    }
}
