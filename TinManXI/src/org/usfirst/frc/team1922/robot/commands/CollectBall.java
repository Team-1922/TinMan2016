package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.commands.shooter.SetShooterAngleIntake;
import org.usfirst.frc.team1922.robot.commands.shooter.SpinUpShooterWheels;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CollectBall extends CommandGroup {
    
    public  CollectBall() {
    	addSequential(new EnsureSafeShooterAngle());
    	addParallel(new SpinUpShooterWheels(-1500));
    	addParallel(new EnableIntakeWheels(true));
    	addSequential(new SetShooterAngleIntake());
    	
    }
}
