package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterSpeed extends CommandBase{
    private final ShooterSubsystem _shooterSubsystem;
    private double speed;
    private double x = 0;

    public ShooterSpeed(ShooterSubsystem _subsystem, double spd){
        _shooterSubsystem = _subsystem;
        addRequirements(_subsystem);
        speed = spd;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        _shooterSubsystem.setShooterSpeed(speed);
        x = Math.abs((speed - _shooterSubsystem.getShooterSpeed()) + 22.5) - 46;
        System.out.println(x);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("finished");
    }

    @Override
    public boolean isFinished() {
        if(x < 3.5d){
            return true;
        } else {
            return false;
        }
    }
}
