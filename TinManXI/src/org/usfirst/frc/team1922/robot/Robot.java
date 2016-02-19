
package org.usfirst.frc.team1922.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import java.io.IOException;

import org.ozram1922.cfg.CfgLoader;
import org.usfirst.frc.team1922.robot.commands.OverwriteXMLCfg;
import org.usfirst.frc.team1922.robot.commands.ReloadXMLCfg;
import org.usfirst.frc.team1922.robot.commands.shooter.JoyCtrlAngle;
import org.usfirst.frc.team1922.robot.commands.shooter.SetShooterAngle;
import org.usfirst.frc.team1922.robot.subsystems.BallRetriever;
import org.usfirst.frc.team1922.robot.subsystems.Climber;
import org.usfirst.frc.team1922.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1922.robot.subsystems.Shooter;
import org.usfirst.frc.team1922.robot.subsystems.ShooterLateralUtilities;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static DriveTrain mDriveTrain = new DriveTrain();
	public static BallRetriever mBallRetriever = new BallRetriever();
	public static Shooter mShooter = new Shooter();
	public static ShooterLateralUtilities mGlobShooterLatUtils = new ShooterLateralUtilities();
	public static OI oi = new OI();
	public static CfgLoader mCfgLoader = new CfgLoader();
	public static RangeAngleTable mRangeAngleTable;
	public static String mCfgFileName = "TinManXI.cfg.xml";
	public static String mCsvRangeAngleName = "RangeAngleTable.csv";
	public static Climber mClimber = new Climber();

    Command autonomousCommand;
    SendableChooser chooser;
    
    Command mSaveFile;
    Command mLoadFile;
    Command mJoyCtrlAngle;
    Command mSetAngle;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

		mRangeAngleTable = new RangeAngleTable(mCsvRangeAngleName);
		
		//register XML loading classes here
		mCfgLoader.RegisterCfgClass(mGlobShooterLatUtils); //this has to be first
		mCfgLoader.RegisterCfgClass(mClimber);
		mCfgLoader.RegisterCfgClass(mDriveTrain);
		mCfgLoader.RegisterCfgClass(mShooter);
		mCfgLoader.RegisterCfgClass(mBallRetriever);
		mCfgLoader.RegisterCfgClass(oi);
		
		//load the xml file here
		mCfgLoader.LoadFile(XMLData.GetString(), false);
		
		//System.out.println("TEST");
		
		mSaveFile = new OverwriteXMLCfg();
		mLoadFile = new ReloadXMLCfg();
		

        /*SmartDashboard.putNumber("Drive P", Robot.mDriveTrain.getPIDController().getP());
        SmartDashboard.putNumber("Drive I", Robot.mDriveTrain.getPIDController().getI());
        SmartDashboard.putNumber("Drive D", Robot.mDriveTrain.getPIDController().getD());
        SmartDashboard.putNumber("Drive F", Robot.mDriveTrain.getPIDController().getF());
       */
		
		//startGRIP();
    }

	//TODO: Make this Good
    protected void startGRIP()
    {
        /* Run GRIP in a new process */
        try {
            new ProcessBuilder("/home/lvuser/grip").inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	//in order to make sure the network tables update first, it is not a command
    	mGlobShooterLatUtils.UpdateCycle();
    	
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        /*
         * FOR TESTING ONLY
         * 
         */
        /*mSetAngle = new SetShooterAngle(15);
        mSetAngle.start();*/
        
        /*mJoyCtrlAngle = new JoyCtrlAngle();
        mJoyCtrlAngle.start();*/
        
        //mSaveFile.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//in order to make sure the network tables update first, it is not a command
    	mGlobShooterLatUtils.UpdateCycle();
    	
        Scheduler.getInstance().run();
        
        /*Robot.mDriveTrain.getPIDController().setPID(
        		SmartDashboard.getNumber("Drive P"),
        		SmartDashboard.getNumber("Drive I"), 
        		SmartDashboard.getNumber("Drive D"));
        
        SmartDashboard.putNumber("Drive Setpoint", Robot.mDriveTrain.getPIDController().getSetpoint());
        */
        
        //Robot.mShooter.GetShooterWheels().GetController().set(2000);
        //Robot.mShooter.GetShooterWheels().SetSpeed(SmartDashboard.getNumber("Wheels Setpoint"));

        /*SmartDashboard.putNumber("Wheels Setpoint", Robot.mShooter.GetShooterWheels().GetController().get());
        
        SmartDashboard.putNumber("Encoder Position", Robot.mShooter.GetShooterWheels().GetController().getPosition());

        SmartDashboard.putNumber("Encoder Speed", Robot.mShooter.GetShooterWheels().GetController().getSpeed());
    */
        
        UpdateSmartDashboardItems();
    }
    
    public void UpdateSmartDashboardItems()
    {
    	SmartDashboard.putNumber("Window Alignment Error (Pixels)", mGlobShooterLatUtils.GetWindage() - mGlobShooterLatUtils.GetBestWindow().mCenterX);
    	SmartDashboard.putBoolean("Window Aligned?", mDriveTrain.onTarget());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
