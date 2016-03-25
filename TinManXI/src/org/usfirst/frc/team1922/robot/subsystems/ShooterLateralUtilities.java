package org.usfirst.frc.team1922.robot.subsystems;

import java.lang.reflect.Array;

import org.ozram1922.OzMath;
import org.ozram1922.cfg.CfgDocument;
import org.ozram1922.cfg.CfgElement;
import org.ozram1922.cfg.CfgInterface;
import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.commands.NonAutoShooterCameraAssistant;

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
	
	//zero is NOT centered, the center of the image is 320 (i think 640 wide)
	protected int mPIDWindageAdj;
	protected String mTableName;
	protected double mCameraToBaseWindowHeight; //the base of the TAPE
	
	protected int mCameraViewWidth;
	protected int mCameraViewHeight;
	
	protected float mThrottleZeroPosition = 0.0f;
	protected float mVerticalFOV;
	protected float mHorizontalFOV;
	
	protected double mUpdateLatencyMS;
	
	protected boolean mUsingGRIP;
	protected NonAutoShooterCameraAssistant mNonAutoAssistantCommand;
	
	public ShooterLateralUtilities()
	{
	}
	
	public void SetTableName(String tableName)
	{
		table = NetworkTable.getTable(tableName);
	}
	
	public int GetWindage()
	{
		return mPIDWindageAdj;
	}
	
	public double GetUpdateLatency()
	{
		return mUpdateLatencyMS;
	}
	
	public int GetError()
	{
		return Math.abs(mPIDWindageAdj - mBestWindow.mCenterX);
	}
	
	//this will between -1 and 1
	public void SetThrottleZeroWidth(float loc)
	{
		mThrottleZeroPosition = loc;
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

			//potNew.Print();
			
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
	
	public float GetZeroWindowPos()
	{
		return mThrottleZeroPosition;
	}
	
	public int GetCameraViewWidth()
	{
		return mCameraViewWidth;
	}
	
	public int GetCameraViewHeight()
	{
		return mCameraViewHeight;
	}
	
	public void SetWindage(int windage)
	{
		mPIDWindageAdj = windage;
	}
	
	public double GetHorizontalFOV()
	{
		return mHorizontalFOV;
	}
	
	public double GetVerticalFOV()
	{
		return mVerticalFOV;
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
	public boolean Deserialize(CfgElement element) {

		mTableName = element.GetAttribute("NetTableName");
		mPIDWindageAdj = element.GetAttributeI("Windage");
		mCameraToBaseWindowHeight = element.GetAttributeF("CameraToWindowBase");
		mCameraViewWidth = element.GetAttributeI("ViewWidth");
		mCameraViewHeight = element.GetAttributeI("ViewHeight");
		
		mVerticalFOV = element.GetAttributeF("VertFOV");
		mHorizontalFOV = element.GetAttributeF("HorizFOV");
		
		mThrottleZeroPosition = mCameraViewWidth / 2;
		
		mUsingGRIP = element.GetAttributeI("UsingGRIP") == 1;
		
		mUpdateLatencyMS = element.GetAttributeI("UpdateLatency");
		
		if(!mUsingGRIP)
		{
			Robot.mNonAutoShootCam.InitVision();
			mNonAutoAssistantCommand = new NonAutoShooterCameraAssistant();
			mNonAutoAssistantCommand.start();
		}
		
		SetTableName(mTableName);
		InvalidateBestWindow();
		return true;
	}

	@Override
	public CfgElement Serialize(CfgElement element, CfgDocument doc) {

		element.SetAttribute("NetTableName", mTableName);
		element.SetAttribute("Windage", mPIDWindageAdj);
		element.SetAttribute("CameraToWindowBase", mCameraToBaseWindowHeight);
		element.SetAttribute("ViewWidth", mCameraViewWidth);
		element.SetAttribute("VertFOV", mVerticalFOV);
		element.SetAttribute("HorizFOV", mHorizontalFOV);
		element.SetAttribute("UpdateLatency", mUpdateLatencyMS);
		
		return element;
	}

	@Override
	public String GetElementTitle()
	{
		return "ShooterLateralGlobal";
	}

	@Override
	public void MakeCfgClassesNull() {
		if(mNonAutoAssistantCommand != null)
			mNonAutoAssistantCommand.cancel();
	}
}

