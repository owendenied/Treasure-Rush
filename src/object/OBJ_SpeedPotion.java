package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_SpeedPotion extends Entity{

    public OBJ_SpeedPotion(GamePanel gp){
        super(gp);
        
        name = "Speed Potion";
        
        // Visuals
        down1 = setup("/objects/speedpotion"); 
        image = setup("/objects/speedpotion");
        
        // Physics
        collision = false; 
    }
}