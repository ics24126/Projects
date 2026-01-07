import com.sun.tools.javac.Main;
import util.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class FrameLoder {
//    public String FramesLocation = "C:\\Users\\fungi\\framesFromVideo";;
//
//    File folder = new File(FramesLocation);
//
//    File[] files = folder.listFiles();


    public List<BufferedImage> loadFrames(String folderPath) throws IOException {

            File folder = new File(folderPath);

    File[] files = folder.listFiles();
    if (files == null) {
        Logger.error("Files not found");
       // return null;
        return new ArrayList<>();
    }


        ArrayList<BufferedImage> images = new ArrayList<>();

        //Arrays.sort(files);
        Arrays.sort(files, (f1, f2) -> f1.getName().compareTo(f2.getName()));

        for(File file : files) {
            if (file.getName().toLowerCase().endsWith("png")){
               // System.out.println("Loading: " + file.getName());
                BufferedImage img = ImageIO.read(file);
                images.add(img);
            }
        }
        return images;
    }



}
