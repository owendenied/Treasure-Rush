package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

    protected GamePanel gp;
    
    // --- WORLD POSITION & MOVEMENT ---
    public int worldX, worldY;
    public int speed;
    public String direction = "down";

    // --- ATTRIBUTES & STATE ---
    public String name;
    public int type; // 0 = player, 1 = npc, 2 = monster
    public int maxLife, life;
    public int damage; 
    
    // --- SPRITES & ANIMATION ---
    public BufferedImage image; // Default image (for static objects)
    public BufferedImage up1, up2, up3, up4;
    public BufferedImage down1, down2, down3, down4;
    public BufferedImage left1, left2, left3, left4;
    public BufferedImage right1, right2, right3, right4;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int actionLockCounter = 0; 

    // --- COLLISION & COMBAT ---
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean collision = false; // Default collision status for objects
    public boolean invincible = false;
    public int invincibleCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // --- MONSTER AI METHOD (Override this for moving entities) ---
    public void setAction() {
        // Default empty implementation for static objects
    }

    // --- CORE UPDATE LOOP ---
    public void update () {
        
        // 1. MONSTER ACTION (For type 2 entities)
        if (type == 2) { 
            setAction();
        }

        // 2. CHECK COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.monster);
        
        // Monsters check for player collision
        if (type == 2) {
            gp.cChecker.checkPlayer(this);
        }

        // 3. MOVE IF NO COLLISION
        if (!collisionOn) {
            switch(direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
        
        // 4. UPDATE SPRITE ANIMATION
        spriteCounter++;
        if (spriteCounter > 10) { 
            if (spriteNum == 1) spriteNum = 2;
            else if (spriteNum == 2) spriteNum = 3;
            else if (spriteNum == 3) spriteNum = 4;
            else if (spriteNum == 4) spriteNum = 1;
            spriteCounter = 0;
        } 
        
        // 5. HANDLE I-FRAMES (Generic Invincibility logic)
        if(invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    // --- RENDERING ---
    public void draw(Graphics2D g2){
        
        BufferedImage imageToDraw = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // OPTIMIZATION: Only draw entities that are visible on screen (Camera Culling)
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                
            // 1. Select Directional Sprite (for moving entities)
            switch(direction) {
                case "up":
                    if (spriteNum == 1) imageToDraw = up1;
                    else if (spriteNum == 2) imageToDraw = up2;
                    else if (spriteNum == 3) imageToDraw = up3; 
                    else if (spriteNum == 4) imageToDraw = up4; 
                    break;
                case "down":
                    if (spriteNum == 1) imageToDraw = down1;
                    else if (spriteNum == 2) imageToDraw = down2;
                    else if (spriteNum == 3) imageToDraw = down3; 
                    else if (spriteNum == 4) imageToDraw = down4; 
                    break;
                case "left":
                    if (spriteNum == 1) imageToDraw = left1;
                    else if (spriteNum == 2) imageToDraw = left2;
                    else if (spriteNum == 3) imageToDraw = left3; 
                    else if (spriteNum == 4) imageToDraw = left4; 
                    break;
                case "right":
                    if (spriteNum == 1) imageToDraw = right1;
                    else if (spriteNum == 2) imageToDraw = right2;
                    else if (spriteNum == 3) imageToDraw = right3; 
                    else if (spriteNum == 4) imageToDraw = right4; 
                    break;
            }
            
            // 2. Fallback to static image (for objects)
            if (imageToDraw == null) {
                imageToDraw = image;
            }
            
            // 3. Draw the final image
            if (imageToDraw != null) {
                g2.drawImage(imageToDraw, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        } 
    }
    
    // --- IMAGE LOADING UTILITY ---
    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}