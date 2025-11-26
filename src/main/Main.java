package main;

import javax.swing.JFrame;

public class Main {
    
    public static void main(String[] args) {

        JFrame window  = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Treasure Rush");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); 
        
        // Size the window to fit the GamePanel
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Boot the Game
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}   