package org.usfirst.frc.team1922.robot.subsystems;

import java.lang.reflect.Array;

import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
import org.w3c.dom.Document;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class ShooterLateralUtilities extends Subsystem implements CfgInterface {
	
	NetworkTable table;
	double[] defaultValue = new double[0];
	StrongholdWindow mBestWindow;
	
	protected ConfigurableClass mCfgClass = new ConfigurableClass("ShooterLateralGlobal", this);
	
	//zero is NOT centered, the center of the image is (i think 640 wide)
	protected float mPIDWindageAdj;
	protected String mTableName;
	
	public ShooterLateralUtilities()
	{
	}
	
	public void SetTableName(String tableName)
	{
		table = NetworkTable.getTable(tableName);
	}
	
	public float GetWindage()
	{
		return mPIDWindageAdj;
	}
	
	//call this BEFORE the command updater, 
	public void UpdateCycle()
	{
		double[] areas      = table.getNumberArray("area", defaultValue);
		double[] widths     = table.getNumberArray("width", defaultValue);
		double[] heights    = table.getNumberArray("height", defaultValue);
		double[] centerXs   = table.getNumberArray("centerX", defaultValue);
		double[] centerYs   = table.getNumberArray("centerY", defaultValue);
		double[] solidities = table.getNumberArray("solidity", defaultValue);
    	
		
		if(Array.getLength(areas) == 0)
		{
			InvalidateBestWindow();
		}
		else
		{
			//are any of the areas significantly BETTER than the current best
			for(int i = 0; i < Array.getLength(areas); ++i)
			{
				if(areas[i] > mBestWindow.mArea + 100)
				{
					mBestWindow = new StrongholdWindow(
							areas[i], widths[i], heights[i],
							centerXs[i], centerYs[i], solidities[i]);
				}
			}
		}
    	/*
    	 * Flow:
    	 * 
    	 * Get network table information
    	 * 
    	 * Is there a valid window in View?
    	 * if no, remove the current "best window"
    	 * if yes, check if there is already a "best window"
    	 * 	is the area significantly bigger? (~100px bigger?)
    	 * 		if yes, replace, if no don't replace (not worth the PID readjusts time)
    	 * 
    	 */
	}
	
	public StrongholdWindow GetBestWindow()
	{
		return mBestWindow;
	}
	
	public void InvalidateBestWindow()
	{
		//do this manually
		mBestWindow = new StrongholdWindow(-1, -1, -1, -1, -1, -1);
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    
    
	@Override
	public boolean DeserializeInternal() {

		mTableName = mCfgClass.GetAttribute("NetTableName");
		mPIDWindageAdj = Float.parseFloat(mCfgClass.GetAttribute("Windage"));
		
		SetTableName(mTableName);
		return true;
	}

	@Override
	public void SerializeInternal(Document doc) {

		mCfgClass.SetAttribute("NetTableName", mTableName);
		mCfgClass.SetAttribute("Windage", Float.toString(mPIDWindageAdj));
	}

	@Override
	public ConfigurableClass GetCfgClass() {
		return mCfgClass;
	}

	@Override
	public void MakeCfgClassesNull() {
		
	}
}

