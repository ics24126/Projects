import util.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VideoToFrameThingy {


    void VideoToFrame(String inputVideoPath, String outputFrameFile) throws IOException, InterruptedException {

        String outputPattern = outputFrameFile+"\\frame_%04d.png";

        Files.createDirectories(Paths.get(outputFrameFile));

        ProcessBuilder process =
                new ProcessBuilder("ffmpeg",
                                    "-i", inputVideoPath,
                                    outputPattern);

        process.redirectError(ProcessBuilder.Redirect.INHERIT);
        process.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process p = process.start();
        int result = p.waitFor();

        if (result != 0) {
            Logger.error("Problem in VideoToFrameThingy");
        }

    }

    void ProcessedFramesToVideo(String outputVideoPath, String inputFramesFile, int fps) throws IOException, InterruptedException {
        String inputPattern = inputFramesFile+"\\frame_%04d.png";
        String FramesPerSecond =String.valueOf(fps) ;

        ProcessBuilder process =
                new ProcessBuilder("ffmpeg",
                                    "-framerate",
                                    FramesPerSecond,
                                    "-i",inputPattern,
                                    "-pix_fmt",
                                    "yuv420p",
                                    outputVideoPath);

        process.redirectError(ProcessBuilder.Redirect.INHERIT);
        process.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        Process p = process.start();
        if (p.waitFor() != 0) {
            Logger.error("Error creating video from frames");
        }
    }




}

