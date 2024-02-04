package frc.robot.DiscoModeHandler;

import edu.wpi.first.wpilibj2.command.Command;
import java.util.random.*;

public class DiscoRainbow {
    private int x = 0, y = 0, z = 0; // X, Y, Z helper variables for R, G, B
    private int change = 1;

public DiscoRainbow(){

}

private boolean checker(int x){
    if (x > 254){
        return false;
    } else {
        return true;
    }
}

public Command discoRainbow(){
    return null;
}


}
