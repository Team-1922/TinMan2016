package org.usfirst.frc.team1922.robot.commands;

import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.commands.shooter.SetShooterAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PrepBallRetrieval extends CommandGroup {
    
    public  PrepBallRetrieval() {

    	addParallel(new SetShooterAngle(Robot.mShooter.GetShooterAngle().GetIntakeAngle()));
    	addParallel(new SetIntakePositionMedLow());
    }
}
