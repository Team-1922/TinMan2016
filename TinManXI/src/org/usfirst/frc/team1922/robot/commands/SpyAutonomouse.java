package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.commands.shooter.SetShooterAngleAuto;
import org.usfirst.frc.team1922.robot.commands.shooter.ShootBall;
import org.usfirst.frc.team1922.robot.commands.shooter.SpinUpShooterWheelsCfg;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *	This one is MUCH simpler
 */
public class SpyAutonomouse extends CommandGroup {
    
    public  SpyAutonomouse() 
    {
    	addSequential(new SpinUpShooterWheelsCfg());
    	addSequential(new SetShooterAngleAuto());
    	addSequential(new ShootBall());
    }
}
