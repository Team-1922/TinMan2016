package org.usfirst.frc.team1922.robot.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootBall extends CommandGroup {
    
    public  ShootBall() {
    	addSequential(new FeedBall());
    	addSequential(new SpinDownShooterWheels());
    }
}
