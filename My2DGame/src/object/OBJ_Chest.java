package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{

    public OBJ_Chest(GamePanel gp){
        super(gp);
        
        name = "Chest";
        
        // Visuals
        down1 = setup("/objects/chest"); 
        image = setup("/objects/chest");
        
        // Physics
        collision = false; 
    }
}