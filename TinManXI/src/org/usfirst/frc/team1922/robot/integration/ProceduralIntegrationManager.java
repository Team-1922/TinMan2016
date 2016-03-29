package org.usfirst.frc.team1922.robot.integration;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;

public class ProceduralIntegrationManager 
{
	protected ArrayList<IProcIntegrationModule> mProcIntegrationModules;
	protected java.util.Timer mControlLoop;
	protected long mUpdatePeriodMS;
	protected AtomicBoolean mIsPaused;
	protected AtomicBoolean mIsDead;
	protected AtomicBoolean mIsStarted;
	
	public ProceduralIntegrationManager(long updatePeriodMS, float turningRadius)
	{
		mIsPaused = new AtomicBoolean();
		mIsDead = new AtomicBoolean();
		mIsStarted = new AtomicBoolean();
		mIsPaused.set(false);
		mIsDead.set(false);
		mIsStarted.set(false);
		mUpdatePeriodMS = updatePeriodMS;
		mProcIntegrationModules = new ArrayList<IProcIntegrationModule>();
		mControlLoop = new java.util.Timer();
		
		
		//add the defaults
		AddIntegrationModule(new EncoderProcIntegration(turningRadius));
		AddIntegrationModule(new AccelerometerProcIntegration());
	}
	
	public synchronized void Start()
	{
		if(!mIsDead.get() && !mIsStarted.get())
			mControlLoop.schedule(new IntTask(this), 0L, mUpdatePeriodMS);
	}
	
	public class IntTask extends TimerTask
	{
	    private ProceduralIntegrationManager mParent;

	    public IntTask(ProceduralIntegrationManager manager) {
	      if (manager == null) {
	        throw new NullPointerException("Given ProceduralIntegrationManager was null");
	      }
	      mParent = manager;
	    }

		@Override
		public void run() 
		{
			mParent.UpdateProc();
		}
		
	}
	
	//this is irreversable
	public synchronized void Stop()
	{
		mControlLoop.cancel();
		mIsDead.set(true);
	}
	
	public void Pause()
	{
		mIsPaused.set(true);
	}
	
	public void Resume()
	{
		mIsPaused.set(true);
	}
	
	public synchronized int AddIntegrationModule(IProcIntegrationModule module)
	{
		mProcIntegrationModules.add(module);
		return mProcIntegrationModules.size() - 1;
	}
	
	public ModuleData GetIntegrationModuleData(int index)
	{
		return mProcIntegrationModules.get(index).GetModuleData();
	}
	
	public ModuleData GetEncoderModuleData()
	{
		return GetIntegrationModuleData(0);
	}
	
	public ModuleData GetAccelerometerModuleData()
	{
		return GetIntegrationModuleData(1);
	}
	
	public void UpdateProc()
	{
		if(mIsPaused.get())
			return;
		
		for(IProcIntegrationModule thisMod : mProcIntegrationModules)
		{
			thisMod.RunLoopProc();
			thisMod.UpdateModuleData();
		}
	}
	
}
