import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class AlvanosErgaths {


//    public BufferedImage paintObjects(List<List<Point>> clusters, int width, int height){
//       BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//
//       for (List<Point> cluster : clusters) {
//
//            int color = randomColor();
//
//            // Να θυμιθω να παω στην τραπεζα !!!!!!!!!!!!!
//            for (Point p : cluster) {
//                output.setRGB(p.x, p.y, color);
//            }
//        }
//
//
//        return output ;
//    }


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

                int origPixel = output.getRGB(p.x, p.y);

                int changedPixel = blend(origPixel, color, 0.5f);

                output.setRGB(p.x, p.y, changedPixel);
            }
        }

        return output;
    }


    private int blend(int origXroma, int neoXroma, float alpha) {

        int r1 = (origXroma >> 16) & 0xFF;
        int g1 = (origXroma >> 8) & 0xFF;
        int b1 = origXroma & 0xFF;

        int r2 = (neoXroma >> 16) & 0xFF;
        int g2 = (neoXroma >> 8) & 0xFF;
        int b2 = neoXroma & 0xFF;

        int r = (int) (r1 * (1 - alpha) + r2 * alpha);
        int g = (int) (g1 * (1 - alpha) + g2 * alpha);
        int b = (int) (b1 * (1 - alpha) + b2 * alpha);
        //Αφου εχω 0.5 στο Α ειναι σαν να λεω βαλε μισο απο το ενα χρωμα και μισο απο το αλλο

        int a = 255;
        return (a << 24) | (r << 16) | (g << 8) | b;
    }


        private int randomColor() {
        Random r = new Random();
        int red = r.nextInt(256);
        int green = r.nextInt(256);
        int blue = r.nextInt(256);
        int alpha = 180; //gia na einai semi-diafano

        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
}
