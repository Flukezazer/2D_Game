package monster;

import Entity.Entity;
import java.awt.Rectangle;
import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import object.SuperObject;

public class MON_Ghost extends Entity {

    private int moveCounter = 0;
    private Random random = new Random();

    public MON_Ghost(GamePanel gp) {
        super(gp);

        this.solidArea = new Rectangle(0, 0, 32, 32);

        name = "Ghost";
        speed = 70;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        try {
            up1 = setup("/monsters/ghost_1");
            up2 = setup("/monsters/ghost1_1");
            down1 = setup("/monsters/ghost_1");
            down2 = setup("/monsters/ghost1_1");
            left1 = setup("/monsters/ghost_1");
            left2 = setup("/monsters/ghost1_1");
            right1 = setup("/monsters/ghost_1");
            right2 = setup("/monsters/ghost1_1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage setup(String imagePath) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkCollision(Entity entity) {

        Rectangle entityArea = entity.solidArea;

        if (entityArea.x < 0 || entityArea.y < 0 || entityArea.x + entityArea.width > gp.screenWidth || entityArea.y + entityArea.height > gp.screenHeight) {
            return true;
        }

        for (SuperObject obj : gp.obj) {
            if (obj != null && obj.solidArea != null) {

                if (entityArea.intersects(obj.solidArea)) {
                    return true;
                }
            }
        }

        for (Entity other : gp.monster) {
            if (other != null && other != entity) {
                if (entityArea.intersects(other.solidArea)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void update() {
        moveCounter++;

        if (moveCounter % 60 == 0) {
            randomMovement();
            System.out.println("Ghost moved to: " + worldX + ", " + worldY); // Debugging
        }

        // Animation logic
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        image = (spriteNum == 1) ? up1 : up2;
    }

    public void randomMovement() {
        int direction = random.nextInt(4); // Choose a random direction as an int
        String movementDirection = ""; // Store the direction as a string for collision checks
        collisionOn = false; // Reset collision flag

        // Set movementDirection based on the chosen int direction
        switch (direction) {
            case 0 ->
                movementDirection = "up";
            case 1 ->
                movementDirection = "down";
            case 2 ->
                movementDirection = "left";
            case 3 ->
                movementDirection = "right";
        }

        // Set the entity's direction for collision checking
        this.direction = movementDirection;

        // Check for collision in the chosen direction
        gp.cChecker.checkTile(this);

        // Move only if there’s no collision
        if (!collisionOn) {
            switch (movementDirection) {
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

}
