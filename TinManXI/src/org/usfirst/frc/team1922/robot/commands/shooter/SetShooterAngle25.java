package org.usfirst.frc.team1922.robot.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetShooterAngle25 extends CommandGroup {
    
    public  SetShooterAngle25() {
    	addSequential(new SetShooterAngle(25));
    }
}
