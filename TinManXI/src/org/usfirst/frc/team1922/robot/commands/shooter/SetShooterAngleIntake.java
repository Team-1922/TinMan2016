package org.usfirst.frc.team1922.robot.commands.shooter;

import org.usfirst.frc.team1922.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetShooterAngleIntake extends CommandGroup {
    
    public  SetShooterAngleIntake() {
    	addSequential(new SetShooterAngle(Robot.mShooter.GetShooterAngle().GetIntakeAngle()));
    }
}
