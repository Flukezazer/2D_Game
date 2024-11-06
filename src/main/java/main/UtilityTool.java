package main; // Make sure this matches your package structure

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class UtilityTool {

    // Method to scale an image
    public BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(originalImage, 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }
}
