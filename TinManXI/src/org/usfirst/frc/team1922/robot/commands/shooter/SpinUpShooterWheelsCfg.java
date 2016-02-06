package org.usfirst.frc.team1922.robot.commands.shooter;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SpinUpShooterWheelsCfg extends CommandGroup {
    
    public  SpinUpShooterWheelsCfg() {
    	addSequential(new SpinUpShooterWheels(Robot.mShooter.GetShooterDefaultSpeed()));
    }
}
