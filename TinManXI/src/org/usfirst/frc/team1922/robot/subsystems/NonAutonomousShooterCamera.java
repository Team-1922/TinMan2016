package org.usfirst.frc.team1922.robot.subsystems;

import org.usfirst.frc.team1922.robot.Robot;
import org.usfirst.frc.team1922.robot.commands.NonAutoShooterCameraAssistant;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Point;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class NonAutonomousShooterCamera extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    protected int session;
    protected boolean mIsInitialized = false;
    protected Image frame;
    
	public void InitVision()
	{
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        //TODO: if this goes wrong, how do we know?
        
        //TODO: how do we set camera quality to avoid using too much bandwidth?
        
        // the camera name (ex "cam0") can be found through the roborio web interface
        
        try
        {
	        session = NIVision.IMAQdxOpenCamera("cam0",
	                NIVision.IMAQdxCameraControlMode.CameraControlModeListener);
	        NIVision.IMAQdxConfigureGrab(session);
	        System.out.println("Timeout Value: " + NIVision.IMAQdxGetAttributeU32(session, "Timeout"));
	        CameraServer.getInstance().setQuality(25);
	    	mIsInitialized = true; 
        }
        catch(Exception e)
        {
        	System.out.println(e.getMessage());
        }
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	if(!Robot.mGlobShooterLatUtils.IsUsingGRIP())
    		setDefaultCommand(new NonAutoShooterCameraAssistant());
    }
    
    public void StartCapture()
    {
    	if(!mIsInitialized)
    		return;
        NIVision.IMAQdxStartAcquisition(session);
    }
    
    public void StopCapture()
    {
    	if(!mIsInitialized)
    		return;
        NIVision.IMAQdxStopAcquisition(session);
    }
    
    public void UpdateFrame()
    {
    	if(!mIsInitialized)
    		return;
        /**
         * draw the two lines for ligning up the window at the "bump" point (by defense)
         * 	(they are actually rects so the thickness is more than one pixel
         */
    	
    	//System.out.println("Drawing Frame");
    	int windage = Robot.mGlobShooterLatUtils.GetWindage();
    	int height = Robot.mGlobShooterLatUtils.GetCameraViewHeight();
    	
    	int elevation = height - Robot.mGlobShooterLatUtils.GetPresetElevation();
    	int width = Robot.mGlobShooterLatUtils.GetCameraViewWidth();

    	try
    	{
	        NIVision.IMAQdxGrab(session, frame, 1);
	        NIVision.imaqDrawShapeOnImage(frame, frame, 
	        		new NIVision.Rect(0, windage - 2, height, 4), 
	        		DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT, 0xFF0000); 
	        
	        //TODO: also update the "center X" for the window
	        //int bestCenterX = Robot.mGlobShooterLatUtils.GetBestWindow().mCenterX;
	        
	        //NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(bestCenterX, 0), new Point(bestCenterX, height), 0xFF0000);
	        
	        //Draw the horizontal line
	        NIVision.imaqDrawShapeOnImage(frame, frame, 
	        		new NIVision.Rect(elevation + 2, 0, 4, width), 
	        		DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT, 0xFF0000); 
	        
	        CameraServer.getInstance().setImage(frame);
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		
    		//if this fails ONCE, then NEVER call this again
    		mIsInitialized = false;
    	}
    
    }
}

