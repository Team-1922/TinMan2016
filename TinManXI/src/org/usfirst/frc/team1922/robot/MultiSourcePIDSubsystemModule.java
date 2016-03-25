package org.usfirst.frc.team1922.robot;

import java.util.HashMap;
import java.util.Map.Entry;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class MultiSourcePIDSubsystemModule 
{
	public PIDController mController;
	protected PIDOutput mActiveOutput;
	protected PIDSource mActiveSource;
	public HashMap<String, PIDOutput> mOutputs = new HashMap<String, PIDOutput>();
	public HashMap<String, PIDSource> mSources = new HashMap<String, PIDSource>();
	
	  /** An output which calls {@link PIDCommand#usePIDOutput(double)} */
	  private PIDOutput output = new PIDOutput() {

	    public void pidWrite(double output) {
	      UsePIDOutput(output);
	    }
	  };
	  /** A source which calls {@link PIDCommand#returnPIDInput()} */
	  private PIDSource source = new PIDSource() {
	    public void setPIDSourceType(PIDSourceType pidSource) {}

	    public PIDSourceType getPIDSourceType() {
	      return PIDSourceType.kDisplacement;
	    }

	    public double pidGet() {
	      return ReturnPIDInput();
	    }
	  };
	
	
	public MultiSourcePIDSubsystemModule(double p, double i, double d, double f)
	{
		mController = new PIDController(p, i, d, f, source, output, PIDController.kDefaultPeriod);
	}
	
	public void AddOutputs(HashMap<String,PIDOutput> outputs)
	{
		for (Entry<String, PIDOutput> entry : outputs.entrySet())
		{
			AddOutput(entry.getKey(), entry.getValue());
		}
	}
	
	public void AddSources(HashMap<String,PIDSource> sources)
	{
		for (Entry<String, PIDSource> entry : sources.entrySet())
		{
			AddSource(entry.getKey(), entry.getValue());
		}
	}
	
	public synchronized void AddOutput(String name, PIDOutput output)
	{
		mOutputs.put(name, output);
	}
	
	public synchronized void AddSource(String name, PIDSource source)
	{
		mSources.put(name, source);
	}
	
	public synchronized void SetOutput(String output)
	{
		if(mOutputs.containsKey(output))
		{
			mActiveOutput = mOutputs.get(output);
		}
	}

	public synchronized void SetSource(String source)
	{
		if(mSources.containsKey(source))
		{
			mActiveSource = mSources.get(source);
		}
	}

	public PIDOutput GetActiveOutput()
	{
		return mActiveOutput;
	}
	
	public PIDSource GetActiveSource()
	{
		return mActiveSource;
	}
	
	protected synchronized double ReturnPIDInput()
	{
		return mActiveSource.pidGet();
	}
	
	protected synchronized void UsePIDOutput(double output)
	{
		mActiveOutput.pidWrite(output);
	}
	
}
