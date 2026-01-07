import util.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VideoToFrameThingy {


    void VideoToFrame(String inputVideoPath, String outputFrameFile) throws IOException, InterruptedException {

        String outputPattern = outputFrameFile+"\\frame_%04d.png";

        Files.createDirectories(Paths.get(outputFrameFile));

        ProcessBuilder process =new ProcessBuilder("ffmpeg", "-i", inputVideoPath, outputPattern);


        Process p = process.start();
        int result = p.waitFor();

        if (result != 0) {
            Logger.error("Problem in VideoToFrameThingy");
        }

    }

    void ProcessedFramesToVideo(String outputVideoPath, String inputFramesFile, int fps) throws IOException, InterruptedException {
        String inputPattern = inputFramesFile+"\\frame_%04d.png";
        String FramesPerSecond =String.valueOf(fps) ;

        ProcessBuilder process = new ProcessBuilder("ffmpeg","-framerate",FramesPerSecond,"-i",inputPattern,"-pix_fmt","yuv420p",outputVideoPath);

        Process p = process.start();
        int result = p.waitFor();
        if (result != 0) {
            Logger.error("Problem in ProcessedFramesToVideo");
        }
    }




}

