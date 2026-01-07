import util.Logger;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class FrameLoader {

    public List<BufferedImage> loadFrames(String folderPath) throws IOException {

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files == null) {
            Logger.error("Frame directory not found");
            return new ArrayList<>();
        }

        Arrays.sort(files, Comparator.comparing(File::getName));

        List<BufferedImage> frames = new ArrayList<>();

        for (File file : files) {
            if (file.getName().toLowerCase().endsWith("png")) {
                frames.add(ImageIO.read(file));
            }
        }

        return frames;
    }
}
