package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.commands.shooter.SetShooterAngle;
import org.usfirst.frc.team1922.robot.commands.shooter.SpinDownShooterWheels;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HomeConfig extends CommandGroup {
    
    public  HomeConfig() {
    	addSequential(new EnsureSafeShooterAngle());
    	addParallel(new SetShooterAngle(0));
    	addParallel(new SpinDownShooterWheels());
    	addParallel(new DisableIntakeWheels());
    	addSequential(new SetIntakePositionHigh());
    }
}
