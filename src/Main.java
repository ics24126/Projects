    import util.Logger;

    import javax.imageio.ImageIO;
    import java.awt.*;
    import java.awt.image.BufferedImage;
    import java.util.List;

    public static void main(String[] args) {

        //PixelComparisonThingy comparitior = new PixelComparisonThingy();
            FrameComparisonThingy f = new FrameComparisonThingy();
            ObjectOverlayRenderer renderer = new ObjectOverlayRenderer();
            VideoToFrameThingy videoProcessor = new VideoToFrameThingy();
            FrameLoader frameLoader = new FrameLoader();


            String framesBeforeObjectTracking = "C:\\Users\\fungi\\Project\\framesFromVideo";
            String framesAfterObjectTracking = "C:\\Users\\fungi\\Project\\framesAfterObjectTracking";
            String inputVideoPath = "C:\\Users\\fungi\\Project\\video.mp4";
            String outputVideoPath = "C:\\Users\\fungi\\Project\\outputTrackedVideo.mp4";


            float start = System.nanoTime();
            try {
                videoProcessor.VideoToFrame(inputVideoPath, framesBeforeObjectTracking);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                Logger.error(e.getMessage());
            }
            float end = System.nanoTime();
            System.out.println("It took " + (end - start)/1000000000 + " Seconds for the frame extraction\n");

            start = System.nanoTime();
        List<BufferedImage> framesFromVideo = new ArrayList<>();
            try {
                framesFromVideo.addAll(
                        frameLoader.loadFrames(framesBeforeObjectTracking)
                );
            } catch (IOException e) {
                Logger.error(e.getMessage());
            }
        end = System.nanoTime();


        File processedDir = new File("C:\\Users\\fungi\\Project\\framesAfterObjectTracking");
        if (!processedDir.exists()) {
            processedDir.mkdirs();
        }

        f.setImages(framesFromVideo);
            System.out.println("It took " + (end - start)/1000000000  + " Seconds to put the extracted frames in the List of Buffered images\n");

    //    List<List<Point>> a = f.generateAllMovementMaps();



            long totalRenderTime = 0;
            long totalBfsTime = 0;
            long totalMovementMapTime = 0;
            long totalForLoopTime=0;

            ExecutorService writerPool = Executors.newFixedThreadPool(2);

            long startForLoop = System.nanoTime();

            for (int i = 1; i < f.images.size(); i++) {

                BufferedImage prev = f.images.get(i - 1);
                BufferedImage curr = f.images.get(i);

                start=System.nanoTime();
                List<Point> movement = f.getMovementMap(prev, curr, 20);
                end=System.nanoTime();
                totalMovementMapTime += (long) (end - start);

                start = System.nanoTime();
                BFS bfs = new BFS(movement, curr.getWidth(), curr.getHeight());
                List<List<Point>> clusters = bfs.findClusters();
                List<List<Point>> objects = bfs.filterClusters(clusters, 250);
                end = System.nanoTime();
                totalBfsTime += (long) (end - start);


                // System.out.println("Frame " + i + " has " + clusters.size() + " clusters");
                //  System.out.println("Frame " + i + " movement pixels = " + movement.size());
                //System.out.println("Frame " + i + " has " + objects.size() + " objects");

                //  BufferedImage painted = AE.paintObjects(objects, curr.getWidth(), curr.getHeight());
                start = System.nanoTime();
                BufferedImage painted = renderer.paintObjects(curr, objects);
                end = System.nanoTime();
                totalRenderTime += (long) (end - start);

                final BufferedImage imageToWrite = painted;
                final String outputPath =
                        framesAfterObjectTracking + "\\frame_" + String.format("%04d", i) + ".png";

                writerPool.submit(() -> {
                    try {
                        ImageIO.write(imageToWrite, "png", new File(outputPath));
                    } catch (IOException e) {
                        Logger.error(e.getMessage());
                    }
                });

            }
            float endForLoop = System.nanoTime();

            long totalPutRenderedFramesToFileTime=0;

            start = System.nanoTime();
            writerPool.shutdown();
            try {
                writerPool.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                Logger.error(e.getMessage());
            }
            end = System.nanoTime();
            totalPutRenderedFramesToFileTime += (long) (end - start);


        totalForLoopTime += (long) ((endForLoop - startForLoop)/ 1000000000.0);
            System.out.println("It took " + totalPutRenderedFramesToFileTime/ 1000000000.0 + " Seconds Put Rendered Images To File\n");
            System.out.println("It took " + totalRenderTime/ 1000000000.0 + " Seconds to render all the images\n");
            System.out.println("It took " + totalBfsTime/1000000000.0 + " Seconds BFS all the images\n");
            System.out.println("It took " + totalMovementMapTime/1000000000.0 + " Seconds to make the MovementMaps\n");
            System.out.println("It took " + totalForLoopTime + " Seconds to get out of the for Loop\n" );

             start = System.nanoTime();
            try {
                videoProcessor.ProcessedFramesToVideo(outputVideoPath, framesAfterObjectTracking, 30);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                Logger.error(e.getMessage());
            }
            end = System.nanoTime();
            System.out.println("It took " + (end - start)/1000000000  + " Seconds to make the video out of the processed frames");

        }


    //List<BufferedImage> framesList ;


