package org.usfirst.frc.team1922.robot.integration;

import java.util.HashMap;

import org.ozram1922.OzMath;
import org.ozram1922.Vec2;

public class EncoderProcIntegration implements IProcIntegrationModule{

	protected ModuleData mMyModuleData = null;

	Vec2 mPrevPosition = new Vec2(0, 0);
	double mPrevAngle = 0;
	
	protected final double radius;
	
	public EncoderProcIntegration(double turningRadius)
	{
		radius = turningRadius;
	}
	
	@Override
	public void RunLoopProc() {

		//See this image for derivation: https://onedrive.live.com/redir?resid=7FF0017D4A10B657!6622&authkey=!AMZggoeHhhmkecw&v=3&ithint=photo%2cjpg
		
		double deltaRightWheel = 0;
		double deltaLeftWheel = 0;
		Vec2 prevPos;
		double oldAngle;
		synchronized(this)
		{
			/*
			 * 
			 * GET PAST DATA HERE
			 * 
			 */
			prevPos = mPrevPosition;
			oldAngle = mPrevAngle;
			
			//GET DELTA RIGHT WHEEL
			//GET DELTA LEFT WHEEL
		}
		/*
		 * 
		 * INSERT PROCESSING HERE
		 * 
		 */
		
		double R = 0;
		double theta = 0;
		Vec2 deltaPos = new Vec2(0,0);
		Vec2 newPos = new Vec2(0,0);
		if(deltaRightWheel == 0)
		{
			if(deltaLeftWheel == 0)
			{
				return;
			}
			else
			{
				R = 2*radius;
				theta = deltaLeftWheel / R;
			}
		}
		else
		{
			if(deltaLeftWheel == 0)
			{
				R = 2*radius;
				theta = deltaRightWheel / R;
			}
			else
			{
				
				R = (2*radius-deltaRightWheel)/(deltaRightWheel - deltaLeftWheel);
				theta = (deltaLeftWheel)/(R - 2*radius);
				deltaPos = new Vec2(
						(R - radius)*Math.sin(theta), 
						(R - radius)*(1-Math.cos(theta)));
				
				newPos = OzMath.Add(OzMath.Rotate(deltaPos, oldAngle), prevPos);
				
			}
		}
		
		synchronized(this)
		{
			/*
			 * 
			 * SET DATA HERE
			 * 
			 */
			mPrevPosition = newPos;
			mPrevAngle += theta;
		}
	}

	@Override
	public ModuleData GetModuleData() 
	{
		ModuleData ret;
		synchronized(mMyModuleData)
		{
			ret = mMyModuleData;
		}
		return ret;
	}

	@Override
	public void UpdateModuleData() 
	{
		HashMap<String, Double> modData = new HashMap<String, Double>();	
		
		modData.put("PosX", mPrevPosition.x);
		modData.put("PosY", mPrevPosition.y);
		modData.put("Angle", mPrevAngle);
		synchronized(mMyModuleData)
		{			
			mMyModuleData = new ModuleData(modData);
		}
	}

}
