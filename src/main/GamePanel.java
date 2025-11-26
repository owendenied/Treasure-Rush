package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import entity.Entity; 
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    
    // --- SCREEN SETTINGS ---
    final int originalTileSize = 16; 
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; 
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; 
    public final int screenHeight = tileSize * maxScreenRow; 
    
    // --- WORLD SETTINGS ---
    public int maxWorldCol;
    public int maxWorldRow;
    public int currentLevel = 1;

    // --- FPS ---
    int FPS = 60;

    // --- SYSTEM COMPONENTS ---
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Sound se = new Sound();
    public Sound music = new Sound();
    Thread gameThread;

    // --- ENTITIES AND OBJECTS ---
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[30]; 
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    // --- GAME STATES ---
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameFinishedState = 3;
    public final int levelSelectState = 4; 
    public final int gameOverState = 5;
    

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // --- SETUP & THREADING ---

    public void setupGame(){
        aSetter.setObject();
        aSetter.setMonster();
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    } 

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; 
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();
            
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) remainingTime = 0;
                
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // --- GAME LOOP ---

    public void update() {
        if(gameState == playState) {
            player.update();

            // Update Monsters
            for (int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    monster[i].update();
                }
            }
        } 
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        // UI Screens
        if (gameState == titleState || 
            gameState == gameFinishedState || 
            gameState == levelSelectState ||
            gameState == gameOverState) {
            ui.draw(g2); 
        } 
        // Gameplay Screen
        else {
            // Draw Tiles
            tileM.draw(g2);

            // Add all entities to list for sorting
            entityList.add(player);
            for(int i = 0; i < obj.length; i++) {
                if (obj[i] != null) entityList.add(obj[i]);
            }
            for(int i = 0; i < monster.length; i++) {
                if (monster[i] != null) entityList.add(monster[i]);
            }

            // Sort by Y position for proper rendering depth
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare (Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            // Draw Entities
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // Clean up list
            entityList.clear();

            // Draw UI
            ui.draw(g2);
        }
        g2.dispose();
    }

    // --- AUDIO ---
    
    public void playMusic(int i) {
        music.stop();
        music.setFile(i);
        if (music.clip != null) music.clip.setFramePosition(0); 
        music.setVolume(0.25);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        // Adjust volume for specific effects
        if (i == 2) se.setVolume(1.0); 
        else se.setVolume(0.5);   
        
        se.play();
    }

    // --- GAME MANAGEMENT ---
    
    public void resetGame() {
        player.setDefaultValues();
        player.hasAxe = 0;
        // Reset Speed Boost state
        player.speed = player.defaultSpeed; 
        player.speedBoost = false; 
    
        // Clear and Respawn assets
        for(int i = 0; i < obj.length; i++) { obj[i] = null; }
        for(int i = 0; i < monster.length; i++) { monster[i] = null; }
        aSetter.setObject();
        aSetter.setMonster();
    
        ui.playtime = 0;
    }
}