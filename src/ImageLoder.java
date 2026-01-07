import util.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoder {
    public BufferedImage loadImage(String path)  {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            Logger.error(e.getMessage());
            return null;
        }
    }

    public void printPixelInfo(BufferedImage img, int x, int y) {
        int rgb = img.getRGB(x, y);
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;

        System.out.println("Pixel at coordinates " + x + " " + y);
        System.out.println("Red = " + r);
        System.out.println("Green = " + g);
        System.out.println("Blue = " + b);
    }
}
