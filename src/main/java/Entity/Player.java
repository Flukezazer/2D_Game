package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 52;
        worldY = gp.tileSize * 53;
        speed = 5;
        direction = "down";

        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        try {
            up = ImageIO.read(getClass().getResourceAsStream("/player/player_up.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/player/player_down.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/player/player_right.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/player/player_left.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            }
            if (keyH.downPressed) {
                direction = "down";
            }
            if (keyH.leftPressed) {
                direction = "left";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int monsterIndex = gp.cChecker.checkMonster(this); // Check for monster collision
            contactMonster(monsterIndex); // Handle collision with monster

            if (!collisionOn) {
                switch (direction) {
                    case "up" ->
                        worldY -= speed;
                    case "down" ->
                        worldY += speed;
                    case "left" ->
                        worldX -= speed;
                    case "right" ->
                        worldX += speed;
                }
            }
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (life <= 0) {
            // Game over state
            gp.gameState = gp.gameOverState;
            gp.ui.showMessage("Game Over!");
            // You can call the restart method here if you want to automatically restart the game
            // restartGame();
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key" -> {
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                }
                case "Door" -> {
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                    } else {
                        gp.ui.showMessage("You need a key!");
                        this.collisionOn = true;
                    }
                }
                case "Exit" -> {
                    gp.ui.gameFinished = true;
                }
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) { // Assuming 999 means no collision
            if (!invincible) {
                life -= 1;
                invincible = true;
                gp.ui.showMessage("You've been hit!");
            }
        }
    }

    public void resetPlayer() {
        // Reset player's position
        worldX = gp.tileSize * 52;
        worldY = gp.tileSize * 53;

        // Reset player's speed, direction, etc.
        speed = 4;
        direction = "down";

        // Reset player's health and keys
        life = maxLife;
        hasKey = 0;

        // Optionally reset invincibility state
        invincible = false;
        invincibleCounter = 0;

        // If you need to reset other attributes, like score, add them here
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" ->
                up;
            case "down" ->
                down;
            case "right" ->
                right;
            case "left" ->
                left;
            default ->
                null;
        };
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
