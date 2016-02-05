package org.usfirst.frc.team1922.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FindAndCenterWindow extends CommandGroup {
    
    public  FindAndCenterWindow() {
    		
    	this.addSequential(new PutWindowInView());
    	this.addSequential(new CenterWindowInView());
    }
}
