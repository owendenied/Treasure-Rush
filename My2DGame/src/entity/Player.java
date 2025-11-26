package entity;

import main.KeyHandler;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    // --- INVENTORY & STATS ---
    public int hasAxe = 0;
    public int defaultSpeed; 

    // --- SPEED BOOST STATE ---
    public boolean speedBoost = false;
    public int speedBoostCounter = 0;
    public final int speedIncrease = 1; 
    public final int speedBoostDuration = 1000; 

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        // Center Player on Screen
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2- (gp.tileSize / 2);

        // Define Collision Hitbox
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage(); 
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 11; 
        worldY = gp.tileSize * 24;
        
        speed = 4;
        defaultSpeed = speed;
        direction = "down";

        // Player Health
        maxLife = 1; 
        life = maxLife;
        
        // Reset Invincibility Status
        invincible = false;
        invincibleCounter = 0;
    }

    public void getPlayerImage(){
        up1 = setup("/player/walk_up_1");
        up2 = setup("/player/walk_up_2");
        up3 = setup("/player/walk_up_3");
        up4 = setup("/player/walk_up_4");

        down1 = setup("/player/walk_down_1");
        down2 = setup("/player/walk_down_2");
        down3 = setup("/player/walk_down_3");
        down4 = setup("/player/walk_down_4");

        left1 = setup("/player/walk_left_1");
        left2 = setup("/player/walk_left_2");
        left3 = setup("/player/walk_left_3");
        left4 = setup("/player/walk_left_4");

        right1 = setup("/player/walk_right_1");
        right2 = setup("/player/walk_right_2");
        right3 = setup("/player/walk_right_3");
        right4 = setup("/player/walk_right_4");
    }

    public void update() {
        // --- 1. CHECK INPUT & SET DIRECTION ---
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            // --- 2. CHECK COLLISIONS ---
            collisionOn = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // --- 3. MOVE IF NO COLLISION ---
            if (!collisionOn) {
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            // --- 4. UPDATE SPRITE ANIMATION ---
            spriteCounter++;
            if (spriteCounter > 10) { 
                if (spriteNum == 1) spriteNum = 2;
                else if (spriteNum == 2) spriteNum = 3;
                else if (spriteNum == 3) spriteNum = 4;
                else if (spriteNum == 4) spriteNum = 1;
                spriteCounter = 0;
            }   
        }

        // --- 5. HANDLE STATUS EFFECTS & DEATH ---
        updateStatusEffects();
        
        if (life <= 0) {
            gp.playMusic(8); // Start Game Over Theme
            gp.gameState = gp.gameOverState; 
            gp.ui.commandNum = 0; 
        }
    }

    private void updateStatusEffects() {
        // Speed Potion Logic
        if (speedBoost) {
            speedBoostCounter++;
            if (speedBoostCounter > speedBoostDuration) { 
                speedBoost = false;
                speed = defaultSpeed; 
                speedBoostCounter = 0;
            }
        }
        
        // I-Frames Logic (Invincibility)
        if(invincible) {
            invincibleCounter++;
            if (invincibleCounter > 30) { // Duration
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch(objectName) {
                case "Axe":
                    gp.playSE(5);
                    hasAxe++;
                    gp.obj[i] = null;
                    break;
                case "DeadTree":
                    if(hasAxe > 0) {
                        gp.playSE(2);
                        gp.obj[i] = null;
                        hasAxe--;
                    }
                    break;
                case "Speed Potion": 
                    gp.playSE(1);
                    if (!speedBoost) {
                        speed = defaultSpeed + speedIncrease; 
                        speedBoost = true;
                        speedBoostCounter = 0; 
                    }
                    gp.obj[i] = null; 
                    break;
                case "Chest":
                    gp.stopMusic();
                    gp.playSE(4);
                    gp.gameState = gp.gameFinishedState;
                    break;
            }
        }   
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                int damage = gp.monster[i].damage; 
                life -= damage;
                invincible = true;
                gp.playSE(3); // Player Hit sound
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        
        // Select Sprite based on Direction
        switch(direction) {
            case "up":
                if (spriteNum == 1) image = up1;
                else if (spriteNum == 2) image = up2;
                else if (spriteNum == 3) image = up3; 
                else if (spriteNum == 4) image = up4; 
                break;
            case "down":
                if (spriteNum == 1) image = down1;
                else if (spriteNum == 2) image = down2;
                else if (spriteNum == 3) image = down3; 
                else if (spriteNum == 4) image = down4; 
                break;
            case "left":
                if (spriteNum == 1) image = left1;
                else if (spriteNum == 2) image = left2;
                else if (spriteNum == 3) image = left3; 
                else if (spriteNum == 4) image = left4; 
                break;
            case "right":
                if (spriteNum == 1) image = right1;
                else if (spriteNum == 2) image = right2;
                else if (spriteNum == 3) image = right3; 
                else if (spriteNum == 4) image = right4; 
                break;
        }

        // Visual Effect for Invincibility (Flashing)
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.30f));
        }
        
        g2.drawImage(image, screenX, screenY, null);

        // Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}