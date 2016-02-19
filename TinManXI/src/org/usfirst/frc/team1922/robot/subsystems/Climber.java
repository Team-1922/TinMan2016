package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
import org.w3c.dom.Document;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem implements CfgInterface{
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	protected ConfigurableClass mCfgClass = new ConfigurableClass("Climber", this);
	
	protected Solenoid liftSolenoid;
	protected Solenoid tiltSolenoid;
	
	protected int liftSolenoidid = 0;
	protected int tiltSolenoidid = 1;

	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	@Override
	public boolean DeserializeInternal() {
		// TODO Auto-generated method stub
		liftSolenoidid = Integer.parseInt(mCfgClass.GetAttribute("liftSolenoid"));
		tiltSolenoidid = Integer.parseInt(mCfgClass.GetAttribute("tiltSolenoid"));
		
		liftSolenoid = new Solenoid(Math.abs(liftSolenoidid));
		tiltSolenoid = new Solenoid(Math.abs(tiltSolenoidid));
		
		return false;
	}

	@Override
	public void SerializeInternal(Document doc) {
		// TODO Auto-generated method stub
		mCfgClass.SetAttribute("liftSolenoid1", Integer.toString(liftSolenoidid));
		mCfgClass.SetAttribute("tiltSolenoid1", Integer.toString(tiltSolenoidid));
	}

	@Override
	public ConfigurableClass GetCfgClass() {
		// TODO Auto-generated method stub
		return mCfgClass;
	}

	@Override
	public void MakeCfgClassesNull() {
		// TODO Auto-generated method stub
		if(liftSolenoid != null){
			liftSolenoid.free();
		}
		if(tiltSolenoid != null){
			tiltSolenoid.free();
		}
		
		liftSolenoid = null;
		tiltSolenoid = null;
	}

	public void setliftSolenoid(boolean b) {
		// TODO Auto-generated method stub

	}
	
	public void settiltSolenoid(boolean b) {
		// TODO Auto-generated method stub

	}
	
	public boolean getliftSolenoid(){
		return liftSolenoid.get();
	}
	
	public boolean gettiltSolenoid(){
		return tiltSolenoid.get();
	}

}

