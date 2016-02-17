package org.usfirst.frc.team1922.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetIntakePositionHigh extends CommandGroup {
    
    public  SetIntakePositionHigh() {
    	addSequential(new SetIntakePosition(SetIntakePosition.IntakePosition.kHigh));
    }
}
