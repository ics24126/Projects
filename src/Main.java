import util.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

void main() {

    //PixelComparisonThingy comparitior = new PixelComparisonThingy();
    FrameComparisonThingy f = new FrameComparisonThingy();
    ObjectOverlayRenderer renderer = new ObjectOverlayRenderer();
    VideoToFrameThingy videoProcessor = new VideoToFrameThingy();
    FrameLoader frameLoader = new FrameLoader();


    String framesBeforeObjectTracking = "C:\\Users\\fungi\\framesFromVideo";
    String framesAfterObjectTracking = "C:\\Users\\fungi\\framesAfterObjectTracking";
    String inputVideoPath = "C:\\Users\\fungi\\OneDrive\\Υπολογιστής\\video.mp4";
    String outputVideoPath = "C:\\Users\\fungi\\OneDrive\\Υπολογιστής\\outputTrackedVideo.mp4";


    try {
        videoProcessor.VideoToFrame(inputVideoPath, framesBeforeObjectTracking);
    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (InterruptedException e) {
        Logger.error(e.getMessage());
    }


    List<BufferedImage> framesFromVideo = new ArrayList<>();
    try {
        framesFromVideo = frameLoader.loadFrames(framesBeforeObjectTracking);
    } catch (IOException e) {
        Logger.error(e.getMessage());
    }
    Logger.info("2");
    f.setImages(framesFromVideo);
    Logger.info("3");

//    List<List<Point>> a = f.generateAllMovementMaps();
    File processedDir = new File("C:\\Users\\fungi\\framesAfterObjectTracking");
    if (!processedDir.exists()) {
        processedDir.mkdirs();
    }
    for (int i = 1; i < f.images.size(); i++) {
        Logger.info("4");
        BufferedImage prev = f.images.get(i - 1);
        BufferedImage curr = f.images.get(i);


        List<Point> movement = f.getMovementMap(prev, curr, 20);


        BFS bfs = new BFS(movement, curr.getWidth(), curr.getHeight());
        List<List<Point>> clusters = bfs.findClusters();
        List<List<Point>> objects = bfs.filterClusters(clusters, 250);

        // System.out.println("Frame " + i + " has " + clusters.size() + " clusters");
        //  System.out.println("Frame " + i + " movement pixels = " + movement.size());
        System.out.println("Frame " + i + " has " + objects.size() + " objects");

        //  BufferedImage painted = AE.paintObjects(objects, curr.getWidth(), curr.getHeight());
        BufferedImage painted = renderer.paintObjects(curr, objects);


        try {
            ImageIO.write(painted, "png",
                    new File(framesAfterObjectTracking + "\\frame_"+ String.format("%04d", i) + ".png"));
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }

    try {
        videoProcessor.ProcessedFramesToVideo(outputVideoPath, framesAfterObjectTracking, 30);
    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (InterruptedException e) {
        Logger.error(e.getMessage());
    }

}


//List<BufferedImage> framesList ;


