package main;

import Entity.Entity;
import tile.TileManager;
import Entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import object.OBJ_Key;
import object.SuperObject;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public Font titleFont;
    public FontMetrics titleMetrics;
    public String title = "Maze Runner";
    public String instruction = "Press SPACEBAR to Start";
    public int gameState;
    public final int titleState = 0;
    public final int gameOverState = 6;

    // World settings
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;

    // FPS
    int FPS = 60;

    // Game objects
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[11];
    public Entity monster[] = new Entity[20];

    // New field to store the image for the game over screen
    private Image gameOverImage;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        titleFont = new Font("Arial", Font.BOLD, 80);
        gameState = titleState;

        // Load the image for the game over screen
        gameOverImage = loadImage("/player/player_dead.png"); // Path to your image
    }

    // Helper method to load images
    private Image loadImage(String imagePath) {
        try {
            return javax.imageio.ImageIO.read(getClass().getResource(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // If image fails to load, return null
        }
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setMonster();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        // Handle different game states
        if (gameState == gameOverState) {
            if (keyH.enterPressed) {
                // Reset game and restart
                restartGame();
            }

            if (keyH.escPressed) {
                // Return to the title screen
                System.exit(0); // Optionally reset the UI if necessary
            }
        }

        if (ui.gameFinished && keyH.enterPressed) {
            restartGame();// Restart the game when Enter is pressed on game finish screen
            
            
        }

        // Handle title screen logic (when gameState is titleState)
        if (gameState == titleState && keyH.spacePressed) {
            // Change to the game state
            gameState = 1;
            setupGame();  // Initialize game objects (objects, monsters)
        }

        // When the game is playing
        if (gameState == 1) {
            player.update();
            for (Entity monster : monster) {
                if (monster != null) {
                    monster.update();  // Update each monster
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // If we're not in title state or game over state, draw the game world
        if (gameState == titleState) {
            // Draw title screen
            drawTitleScreen(g2, this);
        } else if (gameState == gameOverState) {
            // Draw the end screen when game is over
            drawEndScreen(g2, this);
        } else {
            tileM.draw(g2);  // Draw the map

            // Draw objects in the world
            for (SuperObject superObject : obj) {
                if (superObject != null) {
                    superObject.draw(g2, this);
                }
            }

            // Draw monsters
            for (Entity monster : monster) {
                if (monster != null) {
                    monster.draw(g2, this);  // Draw each monster
                }
            }

            // Draw player life and player
            drawGradient(g2, screenWidth, screenHeight, tileSize, player.screenX, player.screenY);
            ui.drawPlayerLife(g2);
            player.draw(g2);
            ui.draw(g2);
        }

        g2.dispose();
    }

    public void drawGradient(Graphics2D g2, int screenWidth, int screenHeight, int tileSize, int playerX, int playerY) {
        // Center the gradient around the player's position
        int centerX = playerX + tileSize / 2;
        int centerY = playerY + tileSize / 2;

        // Define the radius and colors of the gradient
        int radius = 200;
        Color[] colors = {new Color(0, 0, 0, 0), new Color(0, 0, 0, 255)}; // Increased alpha to 220 for more darkness
        float[] fractions = {0.0f, 1.0f};

        // Create the radial gradient paint
        RadialGradientPaint gradientPaint = new RadialGradientPaint(
                new Point(centerX, centerY),
                radius,
                fractions,
                colors,
                CycleMethod.NO_CYCLE
        );

        // Enable anti-aliasing for smoothness
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the gradient paint and apply it
        g2.setPaint(gradientPaint);
        g2.fillRect(0, 0, screenWidth, screenHeight);
    }

    private void drawTitleScreen(Graphics2D g2, GamePanel gp) {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setFont(titleFont);
        g2.setColor(Color.WHITE);

        int x = screenWidth / 2 - (gp.tileSize * 2) / 2; // Center the image horizontally
        int y = gp.tileSize * 8; // Set the y position

        // Assuming gp.player.down1 is a valid image
        g2.drawImage(gp.player.down, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // Draw title
        titleMetrics = g2.getFontMetrics(titleFont);
        int titleX = (screenWidth - titleMetrics.stringWidth(title)) / 2; // Center the title horizontally
        int titleY = screenHeight / 2 - titleMetrics.getHeight(); // Position title vertically

        // Draw title
        g2.drawString(title, titleX, titleY);

        // Draw instruction text
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        int instructionX = (screenWidth - g2.getFontMetrics().stringWidth(instruction)) / 2;
        int instructionY = titleY + titleMetrics.getHeight() + 20; // Position below the title
        g2.drawString(instruction, instructionX, instructionY);
    }

    private void drawEndScreen(Graphics2D g2, GamePanel gp) {
        // Set background color
        g2.setColor(new Color(0, 0, 0));  // Black background
        g2.fillRect(0, 0, screenWidth, screenHeight);

        // Set font for "Game Over" message
        Font endFont = new Font("Arial", Font.BOLD, 50);
        g2.setFont(endFont);
        g2.setColor(Color.WHITE);

        // Draw "Game Over" text
        String gameOverText = "Game Over!";
        int gameOverX = (screenWidth - g2.getFontMetrics().stringWidth(gameOverText)) / 2;
        int gameOverY = screenHeight / 3;
        g2.drawString(gameOverText, gameOverX, gameOverY);

        // Draw Restart Instructions
        String restartText = "Press ENTER to Restart";
        int restartX = (screenWidth - g2.getFontMetrics().stringWidth(restartText)) / 2;
        int restartY = gameOverY + 90;
        g2.drawString(restartText, restartX, restartY);

        // Draw Quit Instructions
        String quitText = "Press ESC to Quit";
        int quitX = (screenWidth - g2.getFontMetrics().stringWidth(quitText)) / 2;
        int quitY = restartY + 60;
        g2.drawString(quitText, quitX, quitY);

        // Draw the Game Over image if it exists
        if (gameOverImage != null) {
            // Scale the image to a desired size
            int imageWidth = 200; // Set the desired width
            int imageHeight = 150; // Set the desired height

            // Center the image horizontally and place it below the quit text
            int imageX = (screenWidth - imageWidth) / 2;
            int imageY = quitY + 50; // Position the image below the quit text

            // Draw the image with the new width and height
            g2.drawImage(gameOverImage, imageX, imageY, imageWidth, imageHeight, null);
        }
    }

    public void restartGame() {
        // Set the game state to the active playing state
        gameState = 1;  // Ensure this corresponds to the correct "playing" state in your game

        // Reset player attributes (health, position, etc.)
        player.resetPlayer();

        // Reset UI elements, including messages and timers
        ui.resetUI();

        // Reset objects like keys and doors to their initial positions
        aSetter.setObject();

        // Reset monsters to their initial positions and states
        aSetter.setMonster();

        // Reset any other game-specific variables or flags
        ui.gameFinished = false;   // Close the congratulations screen
        ui.playTime = 0;           // Reset playtime

        // Restart the game thread, if necessary
        startGameThread();
    }

}
