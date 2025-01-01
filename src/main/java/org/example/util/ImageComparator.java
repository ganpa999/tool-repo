package org.example.util;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
public class ImageComparator {
    private int differencesCount = 0;
    private List<Point> differenceCoordinates = new ArrayList<>();

    public BufferedImage compareImages(BufferedImage img1, BufferedImage img2) {
        int width = Math.min(img1.getWidth(), img2.getWidth());
        int height = Math.min(img1.getHeight(), img2.getHeight());

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!isPixelEqual(img1.getRGB(x, y), img2.getRGB(x, y))) {
                    resultImage.setRGB(x, y, Color.RED.getRGB());
                    differencesCount++;
                    differenceCoordinates.add(new Point(x, y));
                } else {
                    resultImage.setRGB(x, y, img1.getRGB(x, y));
                }
            }
        }

        return resultImage;
    }

    private boolean isPixelEqual(int pixel1, int pixel2) {
        return pixel1 == pixel2;
    }

    public int getDifferencesCount() {
        return differencesCount;
    }

    public List<Point> getDifferenceCoordinates() {
        return differenceCoordinates;
    }

}
