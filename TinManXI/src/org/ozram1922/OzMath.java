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
	//  len: in inches
	//	distance: in inches
	public static double GetPixelCountFromDistanceAndLength(double len, double distance)
	{
		//see http://1drv.ms/1mK12zk  for how I got this function
		return (-5.40945617*Math.log(distance) + 18.59408912) * len;
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
