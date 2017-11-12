package com.sneakerate.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class ImageUtil {
    private static final int DEFAULT_IMAGE_WIDTH = 1200;
    private static final int DEFAULT_IMAGE_HEIGHT = 628;
    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);

    public static File prepareImageFile(String imageUrl) {
        try {
            BufferedImage image = prepareImage(imageUrl);

            return saveToFile(image);
        } catch (IOException e) {
            throw new RuntimeException("Wrong image URL " + imageUrl, e);
        }
    }

    public static File saveToFile(BufferedImage image) throws IOException {
        String fileName = "output-" + System.nanoTime() + ".jpg";
        File preparedImageFile = new File(fileName);
        ImageIO.write(image, "jpg", preparedImageFile);
        return preparedImageFile;
    }

    public static BufferedImage prepareImage(String imageUrl) {
        return prepareImage(imageUrl, DEFAULT_IMAGE_WIDTH);
    }

    public static BufferedImage prepareImage(String imageUrl, int targetWidth) {
        try {
            URL url = new URL(imageUrl);
            return prepareImage(url, targetWidth);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Wrong image URL " + imageUrl, e);
        }
    }

    public static BufferedImage prepareImage(URL url, int targetWidth) {
        try {
            BufferedImage image = ImageIO.read(url);
            image = cropImage(image);
            image = adjustImage(image);
            return scaleImage(image, targetWidth);
        } catch (IOException e) {
            throw new RuntimeException("Cannot prepare file", e);
        }
    }

    private static BufferedImage cropImage(BufferedImage image) {
        Point leftMost = null;
        Point rightMost = null;
        Point topMost = null;
        Point bottomMost = null;

        Color currentBackgroundColor = new Color(image.getRGB(0, 0));

        Queue<Point> q = new LinkedList<>();
        q.add(new Point(0, 0));
        q.add(new Point(0, image.getHeight() - 1));
        q.add(new Point(image.getWidth() - 1, 0));
        q.add(new Point(image.getWidth() - 1, image.getHeight() - 1));

        Set<Point> visited = new HashSet<>();
        while (!q.isEmpty()) {
            Point p = q.poll();
            int x = p.x;
            int y = p.y;

            if (visited.contains(p)) {
                continue;
            } else {
                visited.add(p);
            }

            if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
                continue;
            }

            int rgb = image.getRGB(x, y);
            if (areAlmostTheSameColor(currentBackgroundColor, new Color(rgb))) {
                q.add(new Point(x - 1, y));
                q.add(new Point(x + 1, y));
                q.add(new Point(x, y - 1));
                q.add(new Point(x, y + 1));
            } else {
                if (leftMost == null || leftMost.x > x) {
                    leftMost = p;
                }
                if (rightMost == null || rightMost.x < x) {
                    rightMost = p;
                }
                if (topMost == null || topMost.y > y) {
                    topMost = p;
                }
                if (bottomMost == null || bottomMost.y < y) {
                    bottomMost = p;
                }
            }
        }

        if (leftMost == null || rightMost == null || topMost == null || bottomMost == null) {
            throw new RuntimeException("Unexpected state: couldn't recognize any meaningful info on image");
        }

        return image.getSubimage(leftMost.x, topMost.y, rightMost.x - leftMost.x, bottomMost.y - topMost.y);
    }

    private static boolean areAlmostTheSameColor(Color c1, Color c2) {
        if (Math.abs(c1.getRed() - c2.getRed()) > 5) {
            return false;
        }
        if (Math.abs(c1.getGreen() - c2.getGreen()) > 5) {
            return false;
        }
        if (Math.abs(c1.getBlue() - c2.getBlue()) > 5) {
            return false;
        }

        return true;
    }

    private static BufferedImage adjustImage(BufferedImage image) {
        int targetWidth = (int) (image.getHeight() * 1.91);

        BufferedImage adjustedImage = new BufferedImage((int)(targetWidth * 1.5), (int)(image.getHeight() * 1.5), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = adjustedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.Src);
        g.setColor(BACKGROUND_COLOR);
        g.setBackground(BACKGROUND_COLOR);
        g.fillRect(0, 0, adjustedImage.getWidth(), adjustedImage.getHeight());
        g.drawImage(image, (adjustedImage.getWidth() - image.getWidth()) / 2, (adjustedImage.getHeight() - image.getHeight()) / 2, BACKGROUND_COLOR, null);
        g.dispose();

        return adjustedImage;
    }

    public static BufferedImage scaleImage(BufferedImage image, int targetWidth) {
        int targetHeight = (int) (image.getHeight() * ((double) targetWidth / image.getWidth()));
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.Src);
        g.setColor(BACKGROUND_COLOR);
        g.setBackground(BACKGROUND_COLOR);
        g.fillRect(0, 0, resizedImage.getWidth(), resizedImage.getHeight());
        Image scaled = image.getScaledInstance(resizedImage.getWidth(), resizedImage.getHeight(), Image.SCALE_SMOOTH);
        g.drawImage(scaled, 0, 0, BACKGROUND_COLOR, null);
        g.dispose();

        return resizedImage;
    }
}
