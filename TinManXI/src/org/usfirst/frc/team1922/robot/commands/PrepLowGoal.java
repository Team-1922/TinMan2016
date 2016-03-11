package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.commands.shooter.SetShooterAngle;
import org.usfirst.frc.team1922.robot.commands.shooter.SpinUpShooterWheels;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PrepLowGoal extends CommandGroup {
    
    public  PrepLowGoal() {
    	addParallel(new SetShooterAngle(0));
    	addParallel(new SetIntakePositionHigh());
    	addSequential(new SpinUpShooterWheels(1500));
    }
}
