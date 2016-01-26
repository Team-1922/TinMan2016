package org.usfirst.frc.team1922.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class CommandRetrieval {
	
	public static Command GetCommandFromName(String name)
	{
		switch(name)
		{
		case "OverwriteXMLCfg":
			return new OverwriteXMLCfg();
		case "ReloadXMLCfg":
			return new ReloadXMLCfg();
		default:
			return null;
		}
	}

}
