package org.usfirst.frc.team1922.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetIntakePositionMedHigh extends CommandGroup {
    
    public  SetIntakePositionMedHigh() {
    	addSequential(new SetIntakePosition(SetIntakePosition.IntakePosition.kMedHigh));
    }
}
