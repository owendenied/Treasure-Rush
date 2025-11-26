package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{

    public OBJ_Axe(GamePanel gp){
        super(gp);
        
        name = "Axe";
        
        // Visuals
        down1 = setup("/objects/axe"); 
        image = setup("/objects/axe"); 
        
        // Physics
        collision = false; 
    }
}