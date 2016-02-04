package org.usfirst.frc.team1922.robot.subsystems;

//we don't trust the consumer to be responsible, so FINAL
public class StrongholdWindow 
{
	public final double mArea;
	public final double mWidth;
	public final double mHeight;
	public final double mCenterX;
	public final double mCenterY;
	public final double mSolidity; //i don't know what this is, but why not
	
	public StrongholdWindow(
			double area, double width, double height, 
			double centerX, double centerY, double solidity)
	{
		mArea = area;
		mWidth = width;
		mHeight = height;
		mCenterX = centerX;
		mCenterY = centerY;
		mSolidity = solidity;
	}
	
	/*public static ArrayList<StrongholdWindow> MakeWindows(
			double[] areas, double[] widths, double[] heights,
			double[] centerXs, double[] centerYs, double[] solidities)
	{
		int len = Array.getLength(areas);
		
		ArrayList<StrongholdWindow> ret = new ArrayList<StrongholdWindow>(len);
		
		for(int i = 0; i < len; ++i)
		{
			ret.set(i, new StrongholdWindow(
					areas[i], widths[i], heights[i], 
					centerXs[i], centerYs[i], solidities[i]));
		}
		
		return ret;
	}*/

}
