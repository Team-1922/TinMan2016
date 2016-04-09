package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.commands.shooter.SetShooterAngle;
import org.usfirst.frc.team1922.robot.commands.shooter.SpinDownShooterWheels;
import org.usfirst.frc.team1922.robot.commands.shooter.SpinUpShooterWheels;
import org.usfirst.frc.team1922.robot.commands.shooter.SpinUpShooterWheelsCfg;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PrepHighGoal extends CommandGroup {
    
    public  PrepHighGoal() {
    	addParallel(new DisableIntakeWheels());
    	addParallel(new SetIntakePositionMedLow());
    	addSequential(new SetShooterAngle(Robot.mShooter.GetShooterAngle().GetHighGoalAngle()));
    	//addSequential(new SpinUpShooterWheels(-500));
    	//addSequential(new WaitTime(.125));
    	//addSequential(new SpinDownShooterWheels());
    	addSequential(new SpinUpShooterWheelsCfg());
    }
}
