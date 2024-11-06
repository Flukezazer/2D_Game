package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage KeyImage;
    public boolean messageOn = false;
    BufferedImage heart_full, heart_half, heart_blank;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        KeyImage = key.image;

        //create HUD object
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;

    }
    
    public void drawPlayerLife(Graphics g2){
        
        
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        
        //draw current Life
        
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y ,null);
            }
            i++;
            x += gp.tileSize;
        }
        
    
    }
    
    public void resetUI() {
        
    }

    
    public void draw(Graphics2D g2) {

        if (gameFinished == true) {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the exit!!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulations!!!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);

            gp.gameThread = null;

        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(KeyImage, gp.tileSize * 12, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 625, 65);

            playTime += (double) 1 / 60;
            g2.drawString("Time:" + dFormat.format(playTime), gp.tileSize * 11, gp.tileSize * 11);

            if (messageOn == true) {

                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                messageCounter++;

                if (messageCounter > 120) { //text disapear 2 sec
                    messageCounter = 0;
                    messageOn = false;
                }

            }
        }

    }
}
