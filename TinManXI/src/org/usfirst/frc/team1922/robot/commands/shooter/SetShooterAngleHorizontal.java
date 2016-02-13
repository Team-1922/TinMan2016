package org.usfirst.frc.team1922.robot.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetShooterAngleHorizontal extends CommandGroup {
    
    public  SetShooterAngleHorizontal() {
    	addSequential(new SetShooterAngle(0));
    }
}
