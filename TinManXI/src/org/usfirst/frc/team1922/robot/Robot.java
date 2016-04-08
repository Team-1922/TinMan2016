
package org.usfirst.frc.team1922.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import java.io.IOException;

import org.ozram1922.cfg.CfgLoader;
import org.usfirst.frc.team1922.robot.commands.AutoDrive;
import org.usfirst.frc.team1922.robot.commands.ShovelTheFries;
import org.usfirst.frc.team1922.robot.commands.tests.TestAimingPID;
import org.usfirst.frc.team1922.robot.commands.tests.TestMovementPID;
import org.usfirst.frc.team1922.robot.commands.tests.TestRotationPID;
import org.usfirst.frc.team1922.robot.subsystems.BallRetriever;
import org.usfirst.frc.team1922.robot.subsystems.Climber;
import org.usfirst.frc.team1922.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1922.robot.subsystems.DriverCamera;
import org.usfirst.frc.team1922.robot.subsystems.NonAutonomousShooterCamera;
import org.usfirst.frc.team1922.robot.subsystems.Shooter;
import org.usfirst.frc.team1922.robot.subsystems.ShooterLateralUtilities;
import org.usfirst.frc.team1922.robot.subsystems.DriveTrain.PIDMode;

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
	public static String mCfgFileName = "/home/lvuser/TinManXI.cfg.xml";
	public static String mCsvRangeAngleName = "/home/lvuser/RangeAngleTable.csv";
	public static Climber mClimber = new Climber();
	public static DriverCamera mDriverCamera = new DriverCamera();
	public static NonAutonomousShooterCamera mNonAutoShootCam = new NonAutonomousShooterCamera();
    CameraServer server;

    Command autonomousCommand;
    SendableChooser chooser;
    
    //Command mJoyCtrlAngle;
    //Command mSetAngle;
    
    //store variable to make sure robot state is good
    boolean mSuccessfulInit = false;

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
		mCfgLoader.RegisterCfgClass(mDriverCamera);
		mCfgLoader.RegisterCfgClass(oi);
		
		//load the xml file here
		mSuccessfulInit = mCfgLoader.LoadFile(mCfgFileName, true);
		if(!mSuccessfulInit)
		{
			System.out.println("Something went REALLY wrong loading the .xml config file");
		}
		
		//System.out.println("TEST");
		

        chooser = new SendableChooser();
        chooser.addDefault("Forward Auto", new AutoDrive(AutoDrive.Direction.kForward));
        chooser.addObject("Reverse Auto", new AutoDrive(AutoDrive.Direction.kReverse));
        chooser.addObject("Shovel the Fries", new ShovelTheFries());
        //chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);

        /*server = CameraServer.getInstance();
        server.setQuality(50);
        //the camera name (ex "cam0") can be found through the roborio web interface
        server.startAutomaticCapture("cam0");*/
		
		//startGRIP();
    }

	//TODO: Make this Good
    @Deprecated
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
        
        //autonomousCommand = new AutoDrive();
        autonomousCommand.start();
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
    	if(!mSuccessfulInit)
    		return;
    	
    	//in order to make sure the network tables update first, it is not a command
    	mGlobShooterLatUtils.UpdateCycle();
    	
        Scheduler.getInstance().run();
        

        
        //SmartDashboard.putString("DT Mode", Robot.mDriveTrain.GetPIDModeString());
        //SmartDashboard.putNumber("DT Position Inches", mDriveTrain.GetEncPosInches());
        //SmartDashboard.putNumber("DT Setpoint", mDriveTrain.GetSetpoint());
    }

    TestAimingPID testAim = new TestAimingPID();
    TestMovementPID testMovement = new TestMovementPID();
    TestRotationPID testRotation = new TestRotationPID();
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
        /*SmartDashboard.putNumber("Wheels P", Robot.mShooter.GetShooterWheels().GetP());
        SmartDashboard.putNumber("Wheels I", Robot.mShooter.GetShooterWheels().GetI());
        SmartDashboard.putNumber("Wheels D", Robot.mShooter.GetShooterWheels().GetD());
        SmartDashboard.putNumber("Wheels F", Robot.mShooter.GetShooterWheels().GetF());

        SmartDashboard.putNumber("Angle P", Robot.mShooter.GetShooterAngle().GetP());
        SmartDashboard.putNumber("Angle I", Robot.mShooter.GetShooterAngle().GetI());
        SmartDashboard.putNumber("Angle D", Robot.mShooter.GetShooterAngle().GetD());
       
        SmartDashboard.putNumber("Wheels Setpoint", 0);
        SmartDashboard.putNumber("Angle Setpoint", 0);*/
        
        //SmartDashboard.putNumber("Drive Train Setpoint Inches", 0);
        //mDriveTrain.PIDSwap(PIDMode.kRotational);

        //SmartDashboard.putNumber("Drive Train P", Robot.mDriveTrain.GetP());
        //SmartDashboard.putNumber("Drive Train I", Robot.mDriveTrain.GetI());
        //SmartDashboard.putNumber("Drive Train D", Robot.mDriveTrain.GetD());
        
        //testAim.start();
        //testMovement.start();
        //testRotation.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	if(!mSuccessfulInit)
    		return;
    	//in order to make sure the network tables update first, it is not a command
    	mGlobShooterLatUtils.UpdateCycle();
    	
        Scheduler.getInstance().run();
        
        /*double DTSetpoint = SmartDashboard.getNumber("Drive Train Setpoint Inches");
        if(DTSetpoint != 0)
        {
        	mDriveTrain.SetSetpoint(DTSetpoint);
        }
        Robot.mDriveTrain.SetPID(
        		SmartDashboard.getNumber("Drive Train P"),
        		SmartDashboard.getNumber("Drive Train I"), 
        		SmartDashboard.getNumber("Drive Train D"));
        SmartDashboard.putNumber("Drive Train Inches", mDriveTrain.GetEncPosInches());
        
        /*Robot.mShooter.GetShooterWheels().SetPID(
        		SmartDashboard.getNumber("Wheels P"),
        		SmartDashboard.getNumber("Wheels I"), 
        		SmartDashboard.getNumber("Wheels D"),
        		SmartDashboard.getNumber("Wheels F"));

        //Robot.mShooter.GetShooterWheels().SetSpeed((float)SmartDashboard.getNumber("Wheels Setpoint"));
        SmartDashboard.putNumber("Wheels Speed", Robot.mShooter.GetShooterWheels().GetSpeed());
        SmartDashboard.putNumber("Wheels Pos", mShooter.GetShooterWheels().GetEncPos());
        
        Robot.mShooter.GetShooterAngle().SetPID(
        		SmartDashboard.getNumber("Angle P"),
        		SmartDashboard.getNumber("Angle I"), 
        		SmartDashboard.getNumber("Angle D"));

        if(SmartDashboard.getNumber("Angle Setpoint") != 0)
        {
        	//Robot.mShooter.GetShooterAngle().SetAngle((float)SmartDashboard.getNumber("Angle Setpoint"));
        }
        SmartDashboard.putNumber("Angle", Robot.mShooter.GetShooterAngle().GetAngle());*/
        
        //SmartDashboard.putNumber("WHeels Raw", mShooter.GetShooterWheels().GetSpeed()); 
        
        //Robot.mShooter.GetShooterWheels().GetController().set(2000);
        //Robot.mShooter.GetShooterWheels().SetSpeed(SmartDashboard.getNumber("Wheels Setpoint"));

        /*SmartDashboard.putNumber("Wheels Setpoint", Robot.mShooter.GetShooterWheels().GetController().get());
        
        SmartDashboard.putNumber("Encoder Position", Robot.mShooter.GetShooterWheels().GetController().getPosition());

        SmartDashboard.putNumber("Encoder Speed", Robot.mShooter.GetShooterWheels().GetController().getSpeed());
    */
        
        //SmartDashboard.putNumber("Ultrasonic", Robot.mShooter.GetShooterAngle().GetUltraDistanceRaw());
        //System.out.println(Robot.mShooter.GetShooterAngle().GetUltraDistanceRaw());
        
        //TESTING
        //mDriveTrain.GetActiveController().setPID(SmartDashboard.getNumber("DT P"), SmartDashboard.getNumber("DT I"), SmartDashboard.getNumber("DT D"));
        SmartDashboard.putNumber("Ultra Distance", mShooter.GetShooterAngle().GetUltraDistance());
        SmartDashboard.putNumber("Ultra Distance RAW", mShooter.GetShooterAngle().GetUltraDistanceRaw());
        SmartDashboard.putNumber("Degrees To Center", mDriveTrain.PixelsToRadians(mGlobShooterLatUtils.GetError(), mGlobShooterLatUtils.GetBestWindow().mCenterX, true) * (180.0/3.1415));
        
        SmartDashboard.putNumber("ANGLE ANGLE", mShooter.GetShooterAngle().GetAngle());
        SmartDashboard.putNumber("Angle RAW", mShooter.GetShooterAngle().GetAngleRaw());
        UpdateSmartDashboardItems();
    }
    
    public void UpdateSmartDashboardItems()
    {
    	SmartDashboard.putBoolean("Shooter Wheels Spun Up", mShooter.GetShooterWheels().IsSpunUp());
    	SmartDashboard.putNumber("Window Alignment Error (Pixels)", mGlobShooterLatUtils.GetError());
    	SmartDashboard.putNumber("Window Alignment Error (Degrees)", mDriveTrain.GetRadianOffsetFromPixels() * (180.0/3.1415));
    	SmartDashboard.putNumber("Aiming Tolerance", mDriveTrain.GetTolerance(PIDMode.kAiming));
    	SmartDashboard.putBoolean("Window Aligned?", mDriveTrain.GetTolerance(PIDMode.kAiming) > mGlobShooterLatUtils.GetError());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
