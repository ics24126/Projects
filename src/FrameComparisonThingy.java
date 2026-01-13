import util.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FrameComparisonThingy {

List<BufferedImage> images = new ArrayList<>();
    PixelComparisonThingy comparator = new PixelComparisonThingy();


    public void setImages(List<BufferedImage> images) {
        this.images = images;
    }

    public List<Point> getMovementMap(BufferedImage prev, BufferedImage curr, int threshold){


        List<Point> movement = Collections.synchronizedList(new ArrayList<>());
        int diff;
        for(int j = 0; j < curr.getWidth(); j++){

            for(int k = 0; k < curr.getHeight(); k++){
                int rgb1 = prev.getRGB(j,k);
                int rgb2 = curr.getRGB(j,k);

               diff = comparator.comparePixels(rgb1,rgb2);
                boolean moved = diff>threshold;


               if(moved) movement.add(new Point(j,k));

            }
        }
        return movement;
    }}

