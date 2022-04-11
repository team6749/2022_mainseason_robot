package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterSpeed extends CommandBase{
    private final ShooterSubsystem _shooterSubsystem;
    private double speed;

    public ShooterSpeed(ShooterSubsystem _subsystem, double spd){
        _shooterSubsystem = _subsystem;
        addRequirements(_subsystem);
        speed = spd;
    }

    @Override
    public void initialize() {
        if(speed < 0 || speed > 120){
            speed = 60;
        }
    }

    @Override
    public void execute() {
        _shooterSubsystem.setShooterSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
        return (_shooterSubsystem.getShooterSpeed() >= speed) ? true : false;
    }
}
