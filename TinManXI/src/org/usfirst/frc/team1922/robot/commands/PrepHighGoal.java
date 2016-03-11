package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.commands.shooter.SetShooterAngle;
import org.usfirst.frc.team1922.robot.commands.shooter.SpinUpShooterWheelsCfg;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PrepHighGoal extends CommandGroup {
    
    public  PrepHighGoal() {
    	addParallel(new SetShooterAngle(-Robot.mShooter.GetShooterAngle().GetIntakeAngle()));
    	addParallel(new SetIntakePositionMedLow());
    	addSequential(new SpinUpShooterWheelsCfg());
    }
}
