package org.usfirst.frc.team1922.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.ozram1922.Tuple;
import org.ozram1922.cfg.CfgInterface;
import org.ozram1922.cfg.ConfigurableClass;
import org.usfirst.frc.team1922.robot.commands.CommandRetrieval;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements CfgInterface{

	public enum TriggerAction
	{
		kWhileHeld,
		kWhenPressed,
		kWhenReleased
	}
	
	public static TriggerAction StringToTriggerAction(String action)
	{
		switch(action)
		{
		default:
		case "WhenPressed":
			return TriggerAction.kWhenPressed;
		case "WhenReleased":
			return TriggerAction.kWhenReleased;
		case "WhileHeld":
			return TriggerAction.kWhileHeld;
		}
	}
	
	public static String TriggerActionToString(TriggerAction action)
	{
		switch(action)
		{
		default:
		case kWhenPressed:
			return "WhenPressed";
		case kWhenReleased:
			return "WhenReleased";
		case kWhileHeld:
			return "WhileHeld";
		
		}
	}
	/*
	 * 
	 * Config Variables
	 * 
	 */
	private ConfigurableClass mCfgInstance = new ConfigurableClass("OI", this);
	
	protected HashMap<Tuple<String,Integer>, Tuple<Command, TriggerAction>> mCommandMap = new HashMap<Tuple<String,Integer>, Tuple<Command, TriggerAction>>();
	
	/*
	 * 
	 * Member Variables
	 * 
	 */
	HashMap<String, Tuple<Integer,Joystick>> mJoysticks = new HashMap<String, Tuple<Integer,Joystick>>();
	
	ArrayList<JoystickButton> mButtonCommands = new ArrayList<JoystickButton>();
	/*JoystickButton mNewBtn;
	Joystick mTestJoy = new Joystick(0);
	DriveForward cmd = new DriveForward();*/
	/*
	 * 
	 * Member Functions
	 * 
	 */
	
	public OI()
	{
		//Reconstruct();
	}
	public void Reconstruct()
	{
		//setup all of the command bindings with the loaded XML data
	    for(Map.Entry<Tuple<String,Integer>, Tuple<Command, TriggerAction>> pair : mCommandMap.entrySet())
		{
			JoystickButton j = new JoystickButton(mJoysticks.get(pair.getKey().x).y, pair.getKey().y) ;
			switch(pair.getValue().y)
			{
			default:
			case kWhenPressed:
				j.whenPressed(pair.getValue().x);
				break;
			case kWhenReleased:
				j.whenReleased(pair.getValue().x);
				break;
			case kWhileHeld:
				j.whileHeld(pair.getValue().x);
				break;
			}
			System.out.println(pair.getValue().toString());
			mButtonCommands.add(j);
		}
		
		
		//mNewBtn = new JoystickButton(mTestJoy, 9);
		//mNewBtn.whenPressed(cmd);
		
	}
	
	/*public float GetLeftPower()
	{
		Joystick LeftStick = mJoysticks.get("LeftJoystick").y;
		return LeftStick.getAxisChannel(AxisType.kY);
	}
	
	public float GetRightPower()
	{
		Joystick RightStick = mJoysticks.get("RightJoystick").y;
		return RightStick.getAxisChannel(AxisType.kY);
	}*/
	
	public Joystick GetJoystick(String name)
	{
		return this.mJoysticks.get(name).y;
	}

	/*
	 * 
	 * Override Functions
	 * 
	 */

	@Override
	public boolean DeserializeInternal() {
		//System.out.println("TEST AGAIN");
		
		//get children
		NodeList joysticks = mCfgInstance.GetChildren("Joystick");
		for(int i = 0; i < joysticks.getLength(); ++i)
		{
			Element thisElement = (Element)joysticks.item(i);
			mJoysticks.put(
					thisElement.getAttribute("Name"), 
					new Tuple<Integer,Joystick>(
							Integer.parseInt(thisElement.getAttribute("Id")), 
							new Joystick(Integer.parseInt(thisElement.getAttribute("Id")))));
		}
		
		//make list of joysticks
		
		//make map of buttons and commands
		NodeList commands = mCfgInstance.GetChildren("Command");
		for(int i = 0; i < commands.getLength(); ++i)
		{
			Element thisElement = (Element)commands.item(i);
			System.out.println(thisElement.getAttribute("Name"));
			//Command thisCommand = CommandRetrieval.GetCommandFromName(thisElement.getAttribute("Name"));
			mCommandMap.put(
					new Tuple<String,Integer>(
							thisElement.getAttribute("Joystick"), 
							Integer.parseInt(thisElement.getAttribute("Button"))), 
					new Tuple<Command, TriggerAction>(CommandRetrieval.GetCommandFromName(thisElement.getAttribute("Name")), 
							StringToTriggerAction(thisElement.getAttribute("TriggerType"))));
		}
		
		Reconstruct();
		
		return true;
	}

	@Override
	public void SerializeInternal(Document doc) {

	    Iterator<Entry<String, Tuple<Integer,Joystick>>> it = mJoysticks.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<String, Tuple<Integer,Joystick>> pair = (Entry<String, Tuple<Integer,Joystick>>)it.next();
			Element ej = doc.createElement("Joystick");
			ej.setAttribute("Name", pair.getKey());
			ej.setAttribute("Id", Integer.toString(pair.getValue().x));
			mCfgInstance.AddChild(ej);
		}

	    Iterator<Entry<Tuple<String,Integer>, Tuple<Command, TriggerAction>>> it0 = mCommandMap.entrySet().iterator();
		while(it0.hasNext())
		{
			Entry<Tuple<String,Integer>, Tuple<Command, TriggerAction>> pair = (Entry<Tuple<String,Integer>, Tuple<Command, TriggerAction>>)it0.next();
			Element ec = doc.createElement("Command");
			ec.setAttribute("Name", pair.getValue().x.getName());
			ec.setAttribute("TriggerType", TriggerActionToString(pair.getValue().y));
			ec.setAttribute("JoystickId", pair.getKey().x);
			ec.setAttribute("ButtonId", Integer.toString(pair.getKey().y));
			mCfgInstance.AddChild(ec);
		}
		
	}

	@Override
	public ConfigurableClass GetCfgClass() {
		return mCfgInstance;
	}
	@Override
	public void MakeCfgClassesNull() {
		mCommandMap.clear();;
		mJoysticks.clear();
		mButtonCommands.clear();		
	}
	
	//this outputs all of the button mapped commands onto the smart dashboard
	public void WriteSmartDashboardItems() {

		/*for(Map.Entry<Tuple<String,Integer>, Tuple<Command, TriggerAction>> entry : mCommandMap.entrySet())
		{
			
		}*/
	}


    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

