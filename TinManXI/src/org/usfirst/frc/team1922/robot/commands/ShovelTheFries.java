package org.usfirst.frc.team1922.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShovelTheFries extends CommandGroup {
    
    public  ShovelTheFries() {
		addSequential(new EnsureSafeShooterAngle());
    	addSequential(new SetIntakePositionLow());
    }
}
