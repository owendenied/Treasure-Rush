package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import entity.Entity;
import object.OBJ_Axe;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    
    // --- FONTS & IMAGES ---
    Font dungeonMode;     
    BufferedImage axeImage;
    
    // --- HUD DATA ---
    public double playtime; 
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public int commandNum = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        loadFonts();
        loadImages();
    }

    // --- SETUP ---
    
    private void loadFonts() {
        try {
            InputStream is = getClass().getResourceAsStream("/font/dungeonMode.ttf");
            dungeonMode = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            dungeonMode = new Font("Arial", Font.PLAIN, 40); 
        }
    }

    private void loadImages() {
        // Load Object Images for HUD
        Entity axe = new OBJ_Axe(gp); 
        axeImage = axe.image;
    }

    // --- MAIN DRAW METHOD ---

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(dungeonMode.deriveFont(Font.PLAIN, 40F)); 
        g2.setColor(Color.white);

        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        else if (gp.gameState == gp.levelSelectState) {
            drawLevelSelectScreen();
        }
        else if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        else if (gp.gameState == gp.playState) {
            drawAxeCount(); 
            drawPlayTime(); 
        } 
        else if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        else if (gp.gameState == gp.gameFinishedState) {
            drawGameFinishedScreen();
        }
    } 
    
    // --- SCREEN DRAW METHODS ---

    public void drawTitleScreen(){ 
        // Background
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Title Name
        g2.setFont(dungeonMode.deriveFont(Font.BOLD, 70F));
        String text = "Treasure Rush";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // Shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);
        // Main Color
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Player Image
        x = gp.screenWidth/2 - (gp.tileSize*2) / 2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize*2, null);

        // Menu Options
        g2.setFont(dungeonMode.deriveFont(Font.BOLD, 40F));

        // START
        text = "START";
        y += gp.tileSize * 3.5;
        drawMenuOption(text, y, 0);

        // QUIT
        text = "QUIT";
        y += gp.tileSize;
        drawMenuOption(text, y, 1);
    }

    public void drawLevelSelectScreen() {
        g2.setColor(Color.black);
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        g2.setColor(Color.white);
        g2.setFont(dungeonMode.deriveFont(Font.BOLD, 40F));

        String text = "Select Level";
        int y = gp.tileSize * 3;
        g2.drawString(text, getXforCenteredText(text), y);

        // Menu Items
        y += gp.tileSize * 3;
        drawMenuOption("Level 1", y, 0, Color.white);
        
        y += gp.tileSize;
        drawMenuOption("Level 2", y, 1, Color.white);
        
        y += gp.tileSize;
        drawMenuOption("Level 3 (Locked)", y, 2, Color.gray);
        
        g2.setColor(Color.white);
        
        // Instruction
        y += gp.tileSize * 2;
        g2.setFont(dungeonMode.deriveFont(Font.BOLD, 20F));
        text = "Press ESC to Back";
        g2.drawString(text, getXforCenteredText(text), y);
    }
    
    public void drawPauseScreen() {
        g2.setColor(new Color(0,0,0,150)); // Darken screen
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        g2.setColor(Color.white);
        g2.setFont(dungeonMode.deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        
        g2.drawString(text, x, y);
    }

    public void drawGameFinishedScreen() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        g2.setFont(dungeonMode.deriveFont(Font.PLAIN, 40F));
        
        int y = gp.screenHeight/2 - (gp.tileSize*3);

        drawShadowText("You found the treasure!", y, Color.white);
        drawShadowText("Congratulations!", y + gp.tileSize, Color.yellow);
        drawShadowText("Your time: " + dFormat.format(playtime), y + gp.tileSize*2, Color.yellow);
        
        // Return Instruction
        g2.setFont(dungeonMode.deriveFont(Font.BOLD, 20F));
        drawShadowText("Press ENTER to Return to Title", gp.screenHeight/2 + (gp.tileSize * 2), Color.white);
    }
    
    public void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0,150)); 
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        Color crimsonRed = new Color(210, 50, 50); 

        // 1. Game Over Title
        Font titleFont = dungeonMode.deriveFont(Font.BOLD,110f);
        g2.setFont(titleFont);
        text = "Game Over";

        // Shadow 
        g2.setColor(new Color(100, 20, 20)); 
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text,x,y);
        
        // Text 
        g2.setColor(crimsonRed);
        g2.drawString(text,x-4,y-4);
        
        // 2. Secondary Message
        Font messageFont = dungeonMode.deriveFont(Font.BOLD, 40f);
        g2.setFont(messageFont);
        
        String message = "You Died :(";
        x = getXforCenteredText(message);
        y += gp.tileSize; 

        // Shadow
        g2.setColor(new Color(100, 20, 20)); 
        g2.drawString(message, x + 2, y + 2);
        
        // Text 
        g2.setColor(Color.white); 
        g2.drawString(message, x, y);


        // 3. Menu Options
        Font menuFont = dungeonMode.deriveFont(50f);
        g2.setFont(menuFont);
        
        // RETRY 
        y += gp.tileSize * 2; 
        drawMenuOption("Retry", y, 0);

        // QUIT
        y += 55;
        drawMenuOption("Quit", y, 1);
    }


    // --- HUD DRAWING HELPERS ---

    private void drawAxeCount() {
        g2.setFont(dungeonMode.deriveFont(Font.PLAIN, 27F));
        g2.drawImage(axeImage, gp.tileSize/2, gp.tileSize - 18, gp.tileSize, gp.tileSize, null);
        
        String text = "- " + gp.player.hasAxe;
        int x = gp.tileSize + 30;
        int y = 65;
        
        drawShadowText(text, x, y, Color.white);
    }

    private void drawPlayTime() {
        g2.setFont(dungeonMode.deriveFont(Font.PLAIN, 27F)); 
        playtime += (double)1/60; 
        
        String text = "Time: " + dFormat.format(playtime);
        int x = gp.tileSize * 14;
        int y = 65;
        
        drawShadowText(text, x, y, Color.white);
    }

    // --- GENERIC DRAWING UTILS ---

    private void drawMenuOption(String text, int y, int index) {
        drawMenuOption(text, y, index, Color.white);
    }

    private void drawMenuOption(String text, int y, int index, Color color) {
        int x = getXforCenteredText(text);
        g2.setColor(color);
        g2.drawString(text, x, y);
        if (commandNum == index) {
            g2.setColor(Color.white);
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    private void drawShadowText(String text, int y, Color color) {
        int x = getXforCenteredText(text);
        g2.setColor(Color.black);
        g2.drawString(text, x + 3, y + 3);
        g2.setColor(color);
        g2.drawString(text, x, y);
    }

    private void drawShadowText(String text, int x, int y, Color color) {
        g2.setColor(Color.black);
        g2.drawString(text, x + 2, y + 2); // Shadow offset
        g2.setColor(color);
        g2.drawString(text, x, y);
    }
    
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return gp.screenWidth / 2 - length/2;
    }
}