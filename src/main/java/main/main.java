package main;

import javax.swing.JFrame;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Maze Runner");

        // Set the icon for the application
        try {
            Image icon = ImageIO.read(main.class.getResourceAsStream("/icon/icon.png")); // Replace with your icon path
            window.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); // Add the gamePanel to the JFrame
        
        window.pack(); // Pack the frame to fit the preferred size of the panel
        window.setLocationRelativeTo(null); // Center the window
        window.setVisible(true); // Make the window visible

        gamePanel.setupGame();
        gamePanel.startGameThread(); // Start the game thread after making the window visible
    }
}
