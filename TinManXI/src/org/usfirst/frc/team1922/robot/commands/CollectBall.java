package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.commands.shooter.SetShooterAngleIntake;
import org.usfirst.frc.team1922.robot.commands.shooter.SpinUpShooterWheels;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CollectBall extends CommandGroup {
    
    public  CollectBall() {
    	addSequential(new EnsureSafeShooterAngle());
    	addParallel(new SetIntakePositionMedLow());
    	addParallel(new SpinUpShooterWheels(Robot.mShooter.GetShooterWheels().GetIntakeRPM()));
    	addParallel(new EnableIntakeWheels(true));
    	addSequential(new SetShooterAngleIntake());
    	
    }
}
