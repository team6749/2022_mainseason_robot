package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LightsSubsystem extends SubsystemBase {
	
	private static Relay r2 = new Relay(Constants.lights1); //SPK3 +red -green
	private static Relay r1 = new Relay(Constants.lights2); //SPK2 +blue -common
	public LightsSubsystem() {}
	    
    public Alliance getAlliance() {
    	return DriverStation.getAlliance();
    }
    
	public void setAllianceColors() {
    	if (getAlliance() == Alliance.Red) {
    		Red();
    	}
    	if (getAlliance() == Alliance.Blue) {
    		Blue();
    	}
    	if (getAlliance() == Alliance.Invalid) {
    		Green();
    	}
    }
    
    public static void Off() {//works
		r1.set(Relay.Value.kOn);
    	r2.set(Relay.Value.kOn);
    } 
    
    public static void White() { //works
		r1.set(Relay.Value.kReverse);
    	r2.set(Relay.Value.kOff);
    }
    
    public static void Red() {//works
		r1.set(Relay.Value.kOn);
		r2.set(Relay.Value.kReverse);
	}
    
    public static void Blue() {// work
		r1.set(Relay.Value.kReverse);
		r2.set(Relay.Value.kOn);
	}
    
	public void Green() {//works
		r1.set(Relay.Value.kOn);
		r2.set(Relay.Value.kForward);
	}
    
    public static void Yellow() {//works
    	r1.set(Relay.Value.kOn);
    	r2.set(Relay.Value.kOff);
    }
    
    public void Cyan() {//works
    	r1.set(Relay.Value.kReverse);
    	r2.set(Relay.Value.kForward);
    }
    
    public void Magenta() {//works
    	r1.set(Relay.Value.kReverse);
    	r2.set(Relay.Value.kReverse);
    }
	
	// public static void randomLights(double x){
	// 	System.out.println(x);
	// 	if(x <= -1.0){
	// 	  White();
	// 	} else if(x < -1.0 && x <= -0.75){
	// 	  Cyan();
	// 	} else if(x < -0.75 && x <= -0.5){
	// 	  Red();
	// 	} else if(x < -0.5 && x <= -0.25){
	// 	  Blue();
	// 	}  else if(x < -0.25 && x <= 0){
	// 	  Yellow();
	// 	}  else if(x < 0 && x <= 0.25){
	// 	  Magenta();
	// 	}  else if(x < 0.25 && x <= 0.5){
	// 	  Green();
	// 	}  else if(x < 0.5 && x <= 0.75){
	// 	  Yellow();
	// 	}  else if(x < 0.75 && x <= 1){
	// 	  Cyan();
	// 	}  
	//   }

	  @Override
	  public void periodic() {}
}
