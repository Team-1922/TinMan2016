package org.usfirst.frc.team1922.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetIntakePositionMedLow extends CommandGroup {
    
    public  SetIntakePositionMedLow() {
    	addSequential(new SetIntakePosition(SetIntakePosition.IntakePosition.kMedLow));
    }
}
