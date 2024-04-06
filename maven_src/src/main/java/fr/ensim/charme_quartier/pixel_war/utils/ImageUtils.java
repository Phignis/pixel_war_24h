package fr.ensim.charme_quartier.pixel_war.utils;

import fr.ensim.charme_quartier.pixel_war.model.EUseableColors;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {

    public static BufferedImage resizeImage(Image originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    public static BufferedImage recolor(BufferedImage originalImage) {
        BufferedImage recoloredImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < originalImage.getHeight(); ++y) {
            for (int x = 0; x < originalImage.getWidth(); ++x) {
                int rgb = originalImage.getRGB(x, y);
                Color pixelColor = new Color(rgb);
                Color nearestColor = findNearestColor(pixelColor);
                recoloredImage.setRGB(x, y, nearestColor.getRGB());
            }
        }

        return recoloredImage;
    }

    private static Color findNearestColor(Color targetColor) {
        Color nearestColor = Color.BLACK;
        double minDistance = Double.MAX_VALUE;

        for (EUseableColors Ucolor : EUseableColors.values()) {
            double distance = calculateDistance(targetColor, Ucolor.getValue());
            if (distance < minDistance) {
                minDistance = distance;
                nearestColor = Ucolor.getValue();
            }
        }

        return nearestColor;
    }

    private static double calculateDistance(Color c1, Color c2) {
        double rDiff = c1.getRed() - c2.getRed();
        double gDiff = c1.getGreen() - c2.getGreen();
        double bDiff = c1.getBlue() - c2.getBlue();
        return Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
    }
}

