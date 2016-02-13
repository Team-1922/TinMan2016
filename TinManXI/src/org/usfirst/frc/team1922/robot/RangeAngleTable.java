package org.usfirst.frc.team1922.robot;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import org.ozram1922.CSVLoader;
import org.ozram1922.OzMath;

public class RangeAngleTable {
	
	protected ArrayList<ArrayList<Double>> mRangeData;
	
	public RangeAngleTable(String path)
	{
		try {
			mRangeData = CSVLoader.LoadCSVFile(FileSystems.getDefault().getPath(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public double GetAngleFromRange(double range)
	{
		//find the two surrounding ranges
		double range1 = 0.0;
		double range2 = 0.0;
		double angle1 = 0.0;
		double angle2 = 0.0;
		
		for(int i = 0; i < mRangeData.size(); ++i)
		{
			//there should only be two doubles in each row
			if(mRangeData.get(i).get(0) > range && i > 0)
			{
				range2 = mRangeData.get(i).get(0);
				range1 = mRangeData.get(i - 1).get(0);
				angle2 = mRangeData.get(i).get(1);
				angle1 = mRangeData.get(i - 1).get(1);
			}
		}
		
		//mix between the two
		return OzMath.LinearInterpolate(angle1, angle2, (range - range1) / (range2 - range1));
	}

}
