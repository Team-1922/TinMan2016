package org.usfirst.frc.team1922.robot.subsystems;

import org.usfirst.frc.team1922.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Point;
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
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
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
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */
    	int windage = Robot.mGlobShooterLatUtils.GetWindage();
    	int height = Robot.mGlobShooterLatUtils.GetCameraViewHeight();

        NIVision.IMAQdxGrab(session, frame, 1);
        NIVision.imaqDrawLineOnImage(frame, frame, 
        		DrawMode.DRAW_VALUE,  
        		new Point(windage, 0), new Point(windage, height), 
        		0xFF0000);
        
        CameraServer.getInstance().setImage(frame);
    
    }
}

