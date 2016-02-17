package org.usfirst.frc.team1922.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetIntakePositionLow extends CommandGroup {
    
    public  SetIntakePositionLow() {
    	addSequential(new SetIntakePosition(SetIntakePosition.IntakePosition.kLow));
    }
}
