package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LightsSubsystem extends SubsystemBase {
	
	private Relay r3 = new Relay(Constants.lights3); //SPK3 +red -green
	private Relay r2 = new Relay(Constants.lights2); //SPK2 +blue -common
	

	
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
    
    public void Off() {//works
		r3.set(Relay.Value.kOn);
    	r2.set(Relay.Value.kOn);
    } 
    
    public void White() { //needs to be tested
		r3.set(Relay.Value.kReverse);
    	r2.set(Relay.Value.kOff);
    }
    
    public void Red() {//works
		r3.set(Relay.Value.kOn);
		r2.set(Relay.Value.kReverse);
	}
    
    public void Blue() {// work
		r3.set(Relay.Value.kReverse);
		r2.set(Relay.Value.kOn);
	}
    
	public void Green() {//works
		r3.set(Relay.Value.kOn);
		r2.set(Relay.Value.kForward);
	}
    
    public void Yellow() {//works
    	r3.set(Relay.Value.kOn);
    	r2.set(Relay.Value.kOff);
    }
    
    public void Cyan() {//need to re order
    	r3.set(Relay.Value.kForward);
    	r2.set(Relay.Value.kReverse);
    }
    
    public void Magenta() {//need to re order
    	r3.set(Relay.Value.kReverse);
    	r2.set(Relay.Value.kReverse);
    }
}
