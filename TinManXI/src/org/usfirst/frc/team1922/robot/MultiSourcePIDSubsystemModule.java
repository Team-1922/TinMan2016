package org.usfirst.frc.team1922.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class MultiSourcePIDSubsystemModule 
{
	public PIDController mController;
	public PIDOutput mOutput;
	public PIDSource mInput;
	
	public MultiSourcePIDSubsystemModule(double p, double i, double d, double f, PIDOutput output, PIDSource input)
	{
		mController = new PIDController(p, i, d, f, input, output, PIDController.kDefaultPeriod);
		mOutput = output;
		mInput = input;
	}
	
}
