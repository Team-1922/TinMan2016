package org.usfirst.frc.team1922.robot.subsystems;

import org.usfirst.frc.team1922.robot.commands.TeleopDrive;
import org.w3c.dom.Document;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
/**
 *
 */

//TODO: deal with multiple inheritance problem
public class DriveTrain extends Subsystem implements CfgInterface {
	
	/*
	 * 
	 * Member Variables
	 * 
	 */
	
	private ConfigurableClass mCfgInstance = new ConfigurableClass("DriveTrain", this);
	
	/*
	 * 
	 * Override Functions
	 * 
	 */

	@Override
	public boolean DeserializeInternal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void SerializeInternal(Document doc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ConfigurableClass GetCfgClass() {
		return mCfgInstance;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new TeleopDrive());
    }

}

