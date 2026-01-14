import util.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrameComparisonThingy {

    List<BufferedImage> images = new ArrayList<>();
    PixelComparisonThingy comparator = new PixelComparisonThingy();


    public void setImages(List<BufferedImage> images) {
        this.images = images;
    }

    public List<Point> getMovementMap(BufferedImage prev, BufferedImage curr, int threshold) {

        int width = curr.getWidth();
        int height = curr.getHeight();

        int threads = Runtime.getRuntime().availableProcessors();
        threads-=1;

        List<Point>[] partialResults = new ArrayList[threads];
        Thread[] workers = new Thread[threads];

        int rowsPerThread = height / threads;

        for (int t = 0; t < threads; t++) {

            final int index = t;
            final int startY = t * rowsPerThread;

            int endY;
            if (t == threads - 1) {
                endY = height;
            } else {
                endY = startY + rowsPerThread;
            }
            partialResults[t] = new ArrayList<>();

            workers[t] = new Thread(() -> {

                List<Point> local = partialResults[index];

                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < width; x++) {

                        int rgb1 = prev.getRGB(x, y);
                        int rgb2 = curr.getRGB(x, y);

                        int diff = comparator.comparePixels(rgb1, rgb2);
                        if (diff > threshold) {
                            local.add(new Point(x, y));
                        }
                    }
                }
            });

            workers[t].start();
        }

        for (Thread w : workers) {
            try {
                w.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        List<Point> movement = new ArrayList<>();
        for (List<Point> part : partialResults) {
            movement.addAll(part);
        }

        return movement;
    }
}