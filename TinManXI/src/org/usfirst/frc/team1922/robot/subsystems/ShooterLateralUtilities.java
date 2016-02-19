package org.usfirst.frc.team1922.robot.subsystems;

import java.lang.reflect.Array;

import org.ozram1922.OzMath;
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
	
	long mTimeSincePreviousUniqueUpdate = 0;
	long mPreviousUniqueUpdateTime = 0;
	
	protected ConfigurableClass mCfgClass = new ConfigurableClass("ShooterLateralGlobal", this);
	
	//zero is NOT centered, the center of the image is (i think 640 wide)
	protected int mPIDWindageAdj;
	protected String mTableName;
	protected double mCameraToBaseWindowHeight; //the base of the TAPE
	
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
	
	public int GetError()
	{
		return Math.abs(mPIDWindageAdj - mBestWindow.mCenterX);
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
		
		//in that somewhat likely case which there is a LOWER number of some value
		//	due to asynchronous access, make sure we DON"T update this frame
		if(!OzMath.AreAllEqual(Array.getLength(areas), Array.getLength(widths), Array.getLength(heights), Array.getLength(centerXs), Array.getLength(centerYs), Array.getLength(solidities)))
		{
			return;
		}
    	
		/*
		 * 
		 * 
		 * 
		 * TODO: IMPORTANT: check that the width is greater than the height
		 * 			to prevent unwanted objects being detected (to start with)
		 * 
		 * 
		 * 
		 */
		

		//InvalidateBestWindow();
		/*if(Array.getLength(areas) == 0)
		{
		}
		else*/
		//are any of the areas significantly BETTER than the current best
		StrongholdWindow potNew = new StrongholdWindow(-1, -1, -1, -1, -1, -1);
		for(int i = 0; i < Array.getLength(areas); ++i)
		{
			if(widths[i] > heights[i])
			{
				if(areas[i] > potNew.mArea/* + 100*/)
				{
					potNew = new StrongholdWindow(
							areas[i], widths[i], heights[i],
							centerXs[i], centerYs[i], solidities[i]);
				}
			}
		}
		
		//is this "best" window the same as the old best window? (This is for timing purposes)  Even if the camera position stays the same
		//	There will still be small variances in some aspect of the window (EXACTLY THE SAME = STALE WINDOW)
		if(potNew != mBestWindow)
		{
			//if not, reset the timer
			mBestWindow = potNew;
			
			//System.out.println("New Window Found, Took: " + mTimeSincePreviousUniqueUpdate + "ms");
			
			mTimeSincePreviousUniqueUpdate = 0;
			mPreviousUniqueUpdateTime = System.currentTimeMillis();
		}
		else
		{
			//if true, update the time
			mTimeSincePreviousUniqueUpdate = System.currentTimeMillis() - mPreviousUniqueUpdateTime;
			
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
    	 * THIS IS OLD, MAY NOT BE RELEVENT
    	 */
	}
	
	public double GetCameraToWindowBaseHeight()
	{
		return mCameraToBaseWindowHeight;
	}
	
	public StrongholdWindow GetBestWindow()
	{
		//mBestWindow.Print();
		if(IsBestWindowStale())
		{
			System.out.println("Warning: Window is Stale!");
		}
		return mBestWindow;
	}
	
	//returns true if the best window is more than 200ms old
	public boolean IsBestWindowStale()
	{
		return mTimeSincePreviousUniqueUpdate > 200;
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
		mPIDWindageAdj = Integer.parseInt(mCfgClass.GetAttribute("Windage"));
		mCameraToBaseWindowHeight = Double.parseDouble(mCfgClass.GetAttribute("CameraToWindowBase"));
		
		SetTableName(mTableName);
		InvalidateBestWindow();
		return true;
	}

	@Override
	public void SerializeInternal(Document doc) {

		mCfgClass.SetAttribute("NetTableName", mTableName);
		mCfgClass.SetAttribute("Windage", Integer.toString(mPIDWindageAdj));
		mCfgClass.SetAttribute("CameraToWindowBase", Double.toString(mCameraToBaseWindowHeight));
	}

	@Override
	public ConfigurableClass GetCfgClass() {
		return mCfgClass;
	}

	@Override
	public void MakeCfgClassesNull() {
	}
}

