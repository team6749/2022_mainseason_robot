package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
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
    
    public void Off() {
		r3.set(Relay.Value.kOn);
    	r2.set(Relay.Value.kOn);
    } 
    
    public void White() {
		r3.set(Relay.Value.kOff);
    	r2.set(Relay.Value.kReverse);
    }
    
    public void Red() {
		r3.set(Relay.Value.kOn);
		r2.set(Relay.Value.kReverse);
	}
    
    public void Blue() {
		r3.set(Relay.Value.kReverse);
		r2.set(Relay.Value.kOn);
	}
    
	public void Green() {
		r3.set(Relay.Value.kForward);
		r2.set(Relay.Value.kOn);
	}
    
    public void Yellow() {
    	r3.set(Relay.Value.kOff);
    	r2.set(Relay.Value.kOn);
    }
    
    public void Cyan() {
    	r3.set(Relay.Value.kForward);
    	r2.set(Relay.Value.kReverse);
    }
    
    public void Magenta() {
    	r3.set(Relay.Value.kReverse);
    	r2.set(Relay.Value.kReverse);
    }
}
