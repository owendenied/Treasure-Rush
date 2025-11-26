package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // 1. Load Tile Definitions (Names & Collision info)
        loadTileData("/maps/tiledata.txt");

        // 2. Create the Tile Array
        tile = new Tile[fileNames.size()];
        getTileImages();

        // 3. Calculate Map Size & Initialize Array
        calculateMapDimensions("/maps/level1.txt"); 

        // 4. Load the initial map layout
        loadMap("/maps/level1.txt"); 
    }
    
    // --- DATA LOADING ---

    private void loadTileData(String filePath) {
        InputStream is = getClass().getResourceAsStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); 

        String line;
        try {
            while((line = br.readLine()) != null) {
                fileNames.add(line); 
                collisionStatus.add(br.readLine()); 
            } 
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getTileImages() {    
        for (int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            boolean collision = collisionStatus.get(i).equals("true");

            setup(i, fileName, collision);
        }
    }

    private void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    // --- MAP LOADING ---

    private void calculateMapDimensions(String mapPath) {
        int colCount = 0;
        int rowCount = 0;

        try {
            InputStream is = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                if (colCount == 0) {
                    String[] parts = line.split(" "); 
                    colCount = parts.length;
                }
                rowCount++;
            }
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set dimensions in GamePanel
        gp.maxWorldCol = colCount;
        gp.maxWorldRow = rowCount;
        
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
    }

    public void loadMap(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            
            String line;
            while(row < gp.maxWorldRow && (line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String numbers[] = line.split(" ");
                col = 0; 
                
                while (col < gp.maxWorldCol) {
                    if (col < numbers.length) {
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                    }
                    col++;
                }
                row++;
            }
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void switchMap(String filePath) {
        calculateMapDimensions(filePath);
        loadMap(filePath);
    }

    // --- DRAWING ---

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];
           
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // CAMERA CULLING: Only draw tiles visible on screen
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
    
                // Layer 1: The Grass 
                if(tile.length > 1) {
                     g2.drawImage(tile[001].image, screenX, screenY, null); 
                }

                // Layer 2: The actual map tile
                if (tileNum < tile.length) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            }
            
            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}