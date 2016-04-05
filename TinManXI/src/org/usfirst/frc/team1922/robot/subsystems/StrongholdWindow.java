package org.usfirst.frc.team1922.robot.subsystems;

//we don't trust the consumer to be responsible, so FINAL
public class StrongholdWindow 
{
	public final int mArea;
	public final int mWidth;
	public final int mHeight;
	public final int mCenterX;
	public final int mCenterY;
	public final int mMatchVal;
	
	public StrongholdWindow(
			int area, int width, int height, 
			int centerX, int centerY, int matchVal)
	{
		mArea = area;
		mWidth = width;
		mHeight = height;
		mCenterX = centerX;
		mCenterY = centerY;
		mMatchVal = matchVal;
	}
	
	public StrongholdWindow(
			double area, double width, double height, 
			double centerX, double centerY, double matchVal)
	{
		mArea = (int) area;
		mWidth = (int) width;
		mHeight = (int) height;
		mCenterX = (int) centerX;
		mCenterY = (int) centerY;
		mMatchVal = (int) matchVal;
	}
	
	public void Print()
	{
		System.out.println("Window: \n Area: " + mArea + " \n Width: " + mWidth + "\n Height: " + mHeight + "\n CenterX: " + mCenterX + "\n CenterY: " + mCenterY + "\n MatchVal: " + mMatchVal + "\n");
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
