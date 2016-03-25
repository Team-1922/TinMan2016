package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.commands.shooter.SetShooterAngle;
import org.usfirst.frc.team1922.robot.commands.shooter.SpinUpShooterWheels;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PrepLowGoal extends CommandGroup {
    
    public  PrepLowGoal() {
    	addParallel(new SetShooterAngle(Robot.mShooter.GetShooterAngle().GetLowGoalAngle()));
    	addParallel(new SetIntakePositionHigh());
    	addSequential(new SpinUpShooterWheels(Robot.mShooter.GetShooterWheels().GetLowGoalRPM()));
    }
}
