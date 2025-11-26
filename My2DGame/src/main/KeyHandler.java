package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // --- KEY PRESS LOGIC ---
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();    

        if (gp.gameState == gp.titleState) {
            titleStateInput(code);
        }
        else if (gp.gameState == gp.levelSelectState) {
            levelSelectInput(code);
        }
        else if (gp.gameState == gp.playState) {
            playStateInput(code);
        }
        else if (gp.gameState == gp.pauseState) {
            pauseStateInput(code);
        }
        else if (gp.gameState == gp.gameFinishedState) {
            gameFinishedInput(code);
        }
        else if (gp.gameState == gp.gameOverState) {
            gameOverInput(code);
        }
    }

    // --- KEY RELEASE LOGIC ---
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) upPressed = false;
        if (code == KeyEvent.VK_S) downPressed = false;
        if (code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_D) rightPressed = false;
    }

    // --- STATE SPECIFIC INPUT METHODS ---

    private void titleStateInput(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum = (gp.ui.commandNum == 0) ? 1 : 0;
            gp.playSE(6); 
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum = (gp.ui.commandNum == 1) ? 0 : 1;
            gp.playSE(6); 
        }
        if (code == KeyEvent.VK_ENTER){
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.levelSelectState;
                gp.ui.commandNum = 0; 
                gp.playSE(7); 
            } 
            if (gp.ui.commandNum == 1) {
                System.exit(0);
            }
        }
    }

    private void levelSelectInput(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum = (gp.ui.commandNum == 0) ? 2 : gp.ui.commandNum - 1; 
            gp.playSE(6); 
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum = (gp.ui.commandNum == 2) ? 0 : gp.ui.commandNum + 1;
            gp.playSE(6); 
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.playSE(7); 
            
            if (gp.ui.commandNum == 0) loadLevel(1, "/maps/level1.txt");
            if (gp.ui.commandNum == 1) loadLevel(2, "/maps/level2.txt");
            // Level 3 is locked (commandNum == 2), so no action.
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.titleState;
            gp.ui.commandNum = 0; 
            gp.playSE(7); 
        }
    }

    private void playStateInput(int code) {
        if (code == KeyEvent.VK_W) upPressed = true;
        if (code == KeyEvent.VK_S) downPressed = true;
        if (code == KeyEvent.VK_A) leftPressed = true;
        if (code == KeyEvent.VK_D) rightPressed = true;
        if (code == KeyEvent.VK_P) gp.gameState = gp.pauseState;
    }

    private void pauseStateInput(int code) {
        if (code == KeyEvent.VK_P) gp.gameState = gp.playState;
    }

    private void gameFinishedInput(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.titleState; 
            gp.resetGame(); 
        }
    }
    
    private void gameOverInput(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum = (gp.ui.commandNum == 0) ? 1 : 0;
            gp.playSE(6); 
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum = (gp.ui.commandNum == 1) ? 0 : 1;
            gp.playSE(6); 
        }
        if (code == KeyEvent.VK_ENTER){
            gp.playSE(7); 
            
            if (gp.ui.commandNum == 0) {
                // RETRY
                gp.stopMusic(); 
                gp.resetGame(); 
                gp.gameState = gp.playState;
                gp.playMusic(0); 
            } 
            if (gp.ui.commandNum == 1) {
                // QUIT
                gp.stopMusic(); 
                gp.gameState = gp.titleState;
                gp.ui.commandNum = 0; 
                gp.resetGame(); 
            }
        }
    }

    // --- HELPER ---
    
    private void loadLevel(int level, String mapPath) {
        gp.currentLevel = level;
        gp.tileM.switchMap(mapPath); 
        gp.resetGame(); 
        gp.gameState = gp.playState;
        gp.playMusic(0); 
    }
}