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
    protected Image frame;
    
	public void InitVision()
	{
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        //TODO: if this goes wrong, how do we know?
        
        //TODO: how do we set camera quality to avoid using too much bandwidth?
        
        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        CameraServer.getInstance().setQuality(25);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	if(!Robot.mGlobShooterLatUtils.IsUsingGRIP())
    		setDefaultCommand(new NonAutoShooterCameraAssistant());
    }
    
    public void StartCapture()
    {
        NIVision.IMAQdxStartAcquisition(session);
    }
    
    public void StopCapture()
    {
        NIVision.IMAQdxStopAcquisition(session);
    }
    
    public void UpdateFrame()
    {
        /**
         * draw the two lines for ligning up the window at the "bump" point (by defense)
         * 	(they are actually rects so the thickness is more than one pixel
         */
    	
    	System.out.println("Drawing Frame");
    	int windage = Robot.mGlobShooterLatUtils.GetWindage();
    	int height = Robot.mGlobShooterLatUtils.GetCameraViewHeight();
    	
    	int elevation = height - Robot.mGlobShooterLatUtils.GetPresetElevation();
    	int width = Robot.mGlobShooterLatUtils.GetCameraViewWidth();

        NIVision.IMAQdxGrab(session, frame, 1);
        NIVision.imaqDrawShapeOnImage(frame, frame, 
        		new NIVision.Rect(height, windage - 3, height, 6), 
        		DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0xFF0000); 
        
        //TODO: also update the "center X" for the window
        //int bestCenterX = Robot.mGlobShooterLatUtils.GetBestWindow().mCenterX;
        
        //NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(bestCenterX, 0), new Point(bestCenterX, height), 0xFF0000);
        
        //Draw the horizontal line
        NIVision.imaqDrawShapeOnImage(frame, frame, 
        		new NIVision.Rect(elevation + 3, 0, 6, width), 
        		DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0xFF0000); 
        
        CameraServer.getInstance().setImage(frame);
    
    }
}

