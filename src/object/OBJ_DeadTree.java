package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_DeadTree extends Entity{

    public OBJ_DeadTree(GamePanel gp){
        super(gp);

        name = "DeadTree";
        
        // Visuals
        down1 = setup("/objects/deadtree");
        image = setup("/objects/deadtree");
        
        // Physics
        collision = true; 
    }
}