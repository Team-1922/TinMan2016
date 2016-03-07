package org.usfirst.frc.team1922.robot.subsystems;

import org.ozram1922.cfg.CfgDocument;
import org.ozram1922.cfg.CfgElement;
import org.ozram1922.cfg.CfgInterface;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem implements CfgInterface{
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	protected Solenoid liftSolenoid;
	protected Solenoid tiltSolenoid;
	
	protected int liftSolenoidid = 0;
	protected int tiltSolenoidid = 1;

	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	@Override
	public boolean Deserialize(CfgElement element) {
		liftSolenoidid = element.GetAttributeI("liftSolenoid");
		tiltSolenoidid = element.GetAttributeI("tiltSolenoid");
		
		liftSolenoid = new Solenoid(Math.abs(liftSolenoidid));
		tiltSolenoid = new Solenoid(Math.abs(tiltSolenoidid));
		
		return true;
	}

	@Override
	public CfgElement Serialize(CfgElement element, CfgDocument doc) {
		element.SetAttribute("liftSolenoid", liftSolenoidid);
		element.SetAttribute("tiltSolenoid", tiltSolenoidid);
		
		return element;
	}

	@Override
	public void MakeCfgClassesNull() {
		if(liftSolenoid != null){
			liftSolenoid.free();
		}
		if(tiltSolenoid != null){
			tiltSolenoid.free();
		}
		
		liftSolenoid = null;
		tiltSolenoid = null;
	}
	
	@Override
	public String GetElementTitle()
	{
		return "Climber";
	}

	public void setliftSolenoid(boolean b) {
		liftSolenoid.set(b);

	}
	
	public void settiltSolenoid(boolean b) {
		tiltSolenoid.set(b);

	}
	
	public boolean getliftSolenoid(){
		return liftSolenoid.get();
	}
	
	public boolean gettiltSolenoid(){
		return tiltSolenoid.get();
	}

}

