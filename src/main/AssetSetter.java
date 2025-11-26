package main;

import monster.MON_GreenSlime;
import monster.MON_BlueSlime;
import monster.MON_GreenKingSlime;
import monster.MON_BlueKingSlime;
import object.OBJ_Axe; 
import object.OBJ_DeadTree;
import object.OBJ_SpeedPotion;
import object.OBJ_Chest;
import entity.Entity;

public class AssetSetter {
    
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // --- PUBLIC SETTERS (Route by Level) ---

    public void setObject(){
        if (gp.currentLevel == 1) {
            loadLevelOneObjects();
        }
        else if (gp.currentLevel == 2) {
            loadLevelTwoObjects();
        }
    }

    public void setMonster() {
        if (gp.currentLevel == 2) {
            loadLevelTwoMonsters();
        }
    }

    // --- PRIVATE LEVEL DATA ---

    private void loadLevelOneObjects() {
        // Axes
        placeObject(0, new OBJ_Axe(gp), 13, 16);
        placeObject(1, new OBJ_Axe(gp), 18, 23);
        placeObject(2, new OBJ_Axe(gp), 19, 31);
        placeObject(3, new OBJ_Axe(gp), 12, 33);
        placeObject(4, new OBJ_Axe(gp), 20, 38);
        placeObject(5, new OBJ_Axe(gp), 38, 38);

        // Dead Trees (Obstacles)
        placeObject(6, new OBJ_DeadTree(gp), 25, 22);
        placeObject(7, new OBJ_DeadTree(gp), 27, 24);
        placeObject(8, new OBJ_DeadTree(gp), 28, 35);
        placeObject(9, new OBJ_DeadTree(gp), 34, 19);
        placeObject(10, new OBJ_DeadTree(gp), 34, 16);

        // Speed Potions
        placeObject(11, new OBJ_SpeedPotion(gp), 21, 36);
        placeObject(12, new OBJ_SpeedPotion(gp), 24, 14);
        placeObject(13, new OBJ_SpeedPotion(gp), 16, 31);

        // Victory Chest
        placeObject(14, new OBJ_Chest(gp), 34, 14);
    }

    private void loadLevelTwoObjects() {
        // Axes
        placeObject(0, new OBJ_Axe(gp), 21, 31);
        placeObject(1, new OBJ_Axe(gp), 19, 16);
        placeObject(2, new OBJ_Axe(gp), 27, 8);
        placeObject(3, new OBJ_Axe(gp), 23, 22);
        placeObject(4, new OBJ_Axe(gp), 11, 38);
        placeObject(5, new OBJ_Axe(gp), 11, 9   );
        placeObject(6, new OBJ_Axe(gp), 32, 39);
        placeObject(7, new OBJ_Axe(gp), 38, 15);
        placeObject(8, new OBJ_Axe(gp), 26, 30);
        placeObject(9, new OBJ_Axe(gp), 15, 27);

        // Dead Trees
        placeObject(11, new OBJ_DeadTree(gp), 28, 37);
        placeObject(12, new OBJ_DeadTree(gp), 23, 40);
        placeObject(13, new OBJ_DeadTree(gp), 24, 40);
        placeObject(14, new OBJ_DeadTree(gp), 25, 40); 
        placeObject(15, new OBJ_DeadTree(gp), 38, 38);
        placeObject(16, new OBJ_DeadTree(gp), 34, 25);
        placeObject(17, new OBJ_DeadTree(gp), 13, 18);
        placeObject(18, new OBJ_DeadTree(gp), 26, 13);


        // Speed Potions
        placeObject(19, new OBJ_SpeedPotion(gp), 22, 9);
        placeObject(20, new OBJ_SpeedPotion(gp), 31, 35);
        placeObject(21, new OBJ_SpeedPotion(gp), 38, 27);
        placeObject(22, new OBJ_SpeedPotion(gp), 12, 12);
        placeObject(23, new OBJ_SpeedPotion(gp), 19, 22);
        placeObject(24, new OBJ_SpeedPotion(gp), 38, 16);

        // Victory Chest
        placeObject(25, new OBJ_Chest(gp), 21, 39); 
    }

    private void loadLevelTwoMonsters() {
        // Regular Slimes
        placeMonster(0, new MON_GreenSlime(gp), 14, 13); 
        placeMonster(1, new MON_BlueSlime(gp), 38, 18);
        
        // King Slimes
        placeMonster(2, new MON_GreenKingSlime(gp), 30, 10);
        placeMonster(3, new MON_BlueKingSlime(gp), 24, 33);
    }
    
    // --- HELPER METHODS ---
    
    private void placeObject(int index, Entity object, int x, int y) {
        gp.obj[index] = object;
        gp.obj[index].worldX = x * gp.tileSize;
        gp.obj[index].worldY = y * gp.tileSize;
    }
    
    private void placeMonster(int index, Entity monster, int x, int y) {
        gp.monster[index] = monster;
        gp.monster[index].worldX = x * gp.tileSize;
        gp.monster[index].worldY = y * gp.tileSize;
    }
}