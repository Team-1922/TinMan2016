package org.usfirst.frc.team1922.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.tables.ITable;

/*
 * This PID controller is designed to abstract one subsystem which needs multiple
 * 	ways to have PID control.  This includes unique inputs and outputs
 * 
 */
public abstract class MultiSourcePIDSubsystem extends Subsystem implements Sendable {

	  /** The internal {@link PIDController} */
	  private HashMap<String,MultiSourcePIDSubsystemModule> mControllers = new HashMap<String, MultiSourcePIDSubsystemModule>();
	  private String mActiveController = "";
	  
	  public MultiSourcePIDSubsystem(String name)
	  {
		  super(name);
	  }

	  
	  public void AddPIDController(String name, double p, double i, double d, double f, HashMap<String,PIDOutput> outputs, HashMap<String,PIDSource> sources, String defaultOutput, String defaultSource) {
	    mControllers.put(name, new MultiSourcePIDSubsystemModule(p, i, d, f));
	    if(outputs == null || sources == null)
	    	throw new IllegalArgumentException();
	    
	    mControllers.get(name).AddOutputs(outputs);
	    mControllers.get(name).AddSources(sources);
	    
	    if(!outputs.containsKey(defaultOutput) || !sources.containsKey(defaultSource))
	    	throw new IllegalArgumentException();
	    	
	    mControllers.get(name).SetOutput(defaultOutput);
	    mControllers.get(name).SetSource(defaultSource);
	  }
	  
	  
	  public PIDController GetPIDController(String name) {
		if(!mControllers.containsKey(name))
			return null;
	    return mControllers.get(name).mController;
	  }

	  public void SetActiveController(String name)
	  {
		  //disable the previous controller
		  disable();
		  
		  if(mControllers.get(name) == null)
			  return;
		  mActiveController = name;
		  
		  //DON'T automatically enable the new controller
	  }
	  
	  public PIDController GetActiveController()
	  {
		  return GetPIDController(mActiveController);
	  }
	  
	  public String GetActiveControllerName()
	  {
		  return mActiveController;
	  }
	  
	  public MultiSourcePIDSubsystemModule GetPIDControllerModule(String name)
	  {
		  return mControllers.get(name);
	  }
	  
	  public MultiSourcePIDSubsystemModule GetActiveControllerModule()
	  {
		  return GetPIDControllerModule(mActiveController);
	  }

	  /**
	   * Adds the given value to the setpoint. If
	   * {@link PIDSubsystem#setInputRange(double, double) setInputRange(...)} was
	   * used, then the bounds will still be honored by this method.
	   *$
	   * @param deltaSetpoint the change in the setpoint
	   */
	  public void setSetpointRelative(double deltaSetpoint) {
		if(GetActiveController() == null)
			return;
	    setSetpoint(getPosition() + deltaSetpoint);
	  }

	  /**
	   * Sets the setpoint to the given value. If
	   * {@link PIDSubsystem#setInputRange(double, double) setInputRange(...)} was
	   * called, then the given setpoint will be trimmed to fit within the range.
	   *$
	   * @param setpoint the new setpoint
	   */
	  public void setSetpoint(double setpoint) {
		if(GetActiveController() == null)
			return;
	    GetActiveController().setSetpoint(setpoint);
	  }

	  /**
	   * Returns the setpoint.
	   *$
	   * @return the setpoint
	   */
	  public double getSetpoint() {
		if(GetActiveController() == null)
			return Double.NaN;
	    return GetActiveController().getSetpoint();
	  }

	  /**
	   * Returns the current position
	   *$
	   * @return the current position
	   */
	  public double getPosition() {
		if(GetActiveControllerModule() == null)
			return Double.NaN;
		if(GetActiveControllerModule().GetActiveSource() == null)
			return Double.NaN;
	    return GetActiveControllerModule().GetActiveSource().pidGet();
	  }

	  /**
	   * Sets the maximum and minimum values expected from the input.
	   *
	   * @param minimumInput the minimum value expected from the input
	   * @param maximumInput the maximum value expected from the output
	   */
	  public void setInputRange(double minimumInput, double maximumInput) {
		  if(GetActiveController() == null)
			return;
		  GetActiveController().setInputRange(minimumInput, maximumInput);
	  }

	  /**
	   * Sets the maximum and minimum values to write.
	   *
	   * @param minimumOutput the minimum value to write to the output
	   * @param maximumOutput the maximum value to write to the output
	   */
	  public void setOutputRange(double minimumOutput, double maximumOutput) {
		  if(GetActiveController() == null)
			return;
		  GetActiveController().setOutputRange(minimumOutput, maximumOutput);
	  }

	  /**
	   * Set the absolute error which is considered tolerable for use with OnTarget.
	   * The value is in the same range as the PIDInput values.
	   *$
	   * @param t the absolute tolerance
	   */
	  public void setAbsoluteTolerance(double t) {
		  if(GetActiveController() == null)
			return;
		  GetActiveController().setAbsoluteTolerance(t);
	  }

	  /**
	   * Set the percentage error which is considered tolerable for use with
	   * OnTarget. (Value of 15.0 == 15 percent)
	   *$
	   * @param p the percent tolerance
	   */
	  public void setPercentTolerance(double p) {
		  if(GetActiveController() == null)
			return;
		  GetActiveController().setPercentTolerance(p);
	  }

	  /**
	   * Return true if the error is within the percentage of the total input range,
	   * determined by setTolerance. This assumes that the maximum and minimum input
	   * were set using setInput.
	   *$
	   * @return true if the error is less than the tolerance
	   */
	  public boolean onTarget() {
		if(GetActiveController() == null)
			return false;
	    return GetActiveController().onTarget();
	  }

	  /**
	   * Enables the internal {@link PIDController}
	   */
	  public void enable() {
		if(GetActiveController() == null)
			return;
	    GetActiveController().enable();
	  }

	  /**
	   * Disables the internal {@link PIDController}
	   */
	  public void disable() {
		if(GetActiveController() == null)
			return;
		GetActiveController().disable();
	  }

	  public String getSmartDashboardType() {
	    return "MultiSourcePIDSubsystem";
	  }

	  public void initTable(ITable table) {
	    GetActiveController().initTable(table);
	    super.initTable(table);
	  }
	}
