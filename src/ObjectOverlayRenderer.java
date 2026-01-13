import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class ObjectOverlayRenderer  {


public BufferedImage paintObjects(BufferedImage original, List<List<Point>> clusters) {

    BufferedImage output = new BufferedImage(
            original.getWidth(),
            original.getHeight(),
            BufferedImage.TYPE_INT_ARGB
    );

    Graphics2D g2 = output.createGraphics();
    g2.drawImage(original, 0, 0, null);
    g2.dispose();

    for (List<Point> cluster : clusters) {
        int color = randomColor();

        for (Point p : cluster) {
            int originalPixel = output.getRGB(p.x, p.y);
            int blendedPixel = blend(originalPixel, color, 0.5f);
            output.setRGB(p.x, p.y, blendedPixel);
        }
    }

    return output;
}

    private int blend(int baseColor, int overlayColor, float alpha) {

        int r1 = (baseColor >> 16) & 0xFF;
        int g1 = (baseColor >> 8) & 0xFF;
        int b1 = baseColor & 0xFF;

        int r2 = (overlayColor >> 16) & 0xFF;
        int g2 = (overlayColor >> 8) & 0xFF;
        int b2 = overlayColor & 0xFF;

        int r = (int) (r1 * (1 - alpha) + r2 * alpha);
        int g = (int) (g1 * (1 - alpha) + g2 * alpha);
        int b = (int) (b1 * (1 - alpha) + b2 * alpha);

        int a = 255;
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private int randomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        int alpha = 180;

        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
}