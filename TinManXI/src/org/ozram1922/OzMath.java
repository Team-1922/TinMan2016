package org.ozram1922;

import org.usfirst.frc.team1922.robot.Robot;

public class OzMath {
	
	public static boolean SigmaTest(double x, double y, double sigma)
	{
		return Math.abs(x - y) < sigma;
	}

	public static double GetTrajAngle(double distance) 
	{
		return Robot.mRangeAngleTable.GetAngleFromRange(distance);
	}
	
	//x is the min, y is the max, val is a float from 0 to 1
	public static double LinearInterpolate(double x, double y, double val)
	{
		return x + (y - x) * val;
	}
	
	//get the number of pixels a certain length is at a specified distance
	public static double GetPixelCountFromDistanceAndLength(double len, double distance)
	{
		return -64.19502441 * Math.log(distance) + 222.42240554;
	}
	
	//see http://stackoverflow.com/questions/5503091/is-there-clean-syntax-for-checking-if-multiple-variables-all-have-the-same-value
	//	for credit
	public static boolean AreAllEqual(int checkValue, int... otherValues)
	{
	    for (int value : otherValues)
	    {
	        if (value != checkValue)
	        {
	            return false;
	        }
	    }
	    return true;
	}

}
