import util.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FrameComparisonThingy {
   // private final int Red = 0xFF0000;
   // private final int Black = 0;


List<BufferedImage> images = new ArrayList<>();
    PixelComparisonThingy comparator = new PixelComparisonThingy();
//    FrameLoder l = new FrameLoder();
//    List<BufferedImage> framesFromVideo=new ArrayList<>();
//
//    {
//        try {
//            framesFromVideo = l.loadFrames(l.FramesLocation);
//        } catch (IOException e) {
//            Logger.error(e.getMessage());
//        }
//    }


    public List<BufferedImage> getImages() {
        return images;
    }

    public void setImages(List<BufferedImage> images) {
        this.images = images;
    }

    public List<Point> getMovementMap(BufferedImage prev, BufferedImage curr, int threshold){

       // BufferedImage I = new BufferedImage(curr.getWidth(), curr.getHeight(), BufferedImage.TYPE_INT_RGB);

        List<Point> movement = new ArrayList<>();
        int diff;
        for(int j = 0; j < curr.getWidth(); j++){

            for(int k = 0; k < curr.getHeight(); k++){
                int rgb1 = prev.getRGB(j,k);
                int rgb2 = curr.getRGB(j,k);

               diff = comparator.comparePixels(rgb1,rgb2);
                boolean moved = diff>threshold;


               if(moved) movement.add(new Point(j,k));

//                if(moved){
//                    I.setRGB(j,k,Red);
//                }
//                else{
//                    I.setRGB(j,k,Black);
//
//                }

            }
        }
        //return I;
        return movement;
    }
    public List<List<Point>>generateAllMovementMaps() {
        List<List<Point>> a = new ArrayList<>();
        for(int i = 1; i < images.size(); i++){
            a.add((List<Point>) getMovementMap(images.get(i-1), images.get(i), 20));
        }
        return a;
    }

}
