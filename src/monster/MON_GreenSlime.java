package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_GreenSlime extends Entity {

    public MON_GreenSlime(GamePanel gp) {
        super(gp);

        type = 2; // Monster Type
        name = "Regular Green Slime";
        speed = 2;
        maxLife = 4;
        life = maxLife;
        damage = 1;
        
        // Collision Area setup
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 40;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        // DOWN Direction
        down1 = setup("/monster/greenslime/down_1");
        down2 = setup("/monster/greenslime/down_2");
        down3 = setup("/monster/greenslime/down_3");
        down4 = setup("/monster/greenslime/down_4");

        // UP Direction
        up1 = setup("/monster/greenslime/up_1");
        up2 = setup("/monster/greenslime/up_2");
        up3 = setup("/monster/greenslime/up_3");
        up4 = setup("/monster/greenslime/up_4");

        // LEFT Direction
        left1 = setup("/monster/greenslime/left_1");
        left2 = setup("/monster/greenslime/left_2");
        left3 = setup("/monster/greenslime/left_3");
        left4 = setup("/monster/greenslime/left_4");

        // RIGHT Direction
        right1 = setup("/monster/greenslime/right_1");
        right2 = setup("/monster/greenslime/right_2");
        right3 = setup("/monster/greenslime/right_3");
        right4 = setup("/monster/greenslime/right_4");
    }

    public void setAction(){
        
        actionLockCounter++; 
        
        // Change direction every 3 seconds (180 frames at 60 FPS)
        if (actionLockCounter == 180) {
            
            Random random = new Random();
            int i = random.nextInt(100) + 1; // 1 to 100

            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) { 
                direction = "down";
            } else if (i <= 75) { 
                direction = "left";
            } else { 
                direction = "right";
            }
            
            actionLockCounter = 0;
        }
    }
}