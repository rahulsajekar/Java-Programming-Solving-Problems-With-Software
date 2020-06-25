
/**
 * Write a description of ImageInversion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
public class ImageInversion {
    public ImageResource imageInversion(ImageResource inImage)
    {
        ImageResource outImage = new ImageResource(inImage.getWidth(),inImage.getHeight());
        for(Pixel pixel : outImage.pixels())
        {
            Pixel inpixel = inImage.getPixel(pixel.getX(),pixel.getY());
            int red = 255 - inpixel.getRed();
            int green = 255 - inpixel.getGreen();
            int blue = 255 - inpixel.getBlue();
            pixel.setRed(red);
            pixel.setGreen(green);
            pixel.setBlue(blue);
        }
        return outImage;
    }
    public void testImageInversion()
    {
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles())
        {
            ImageResource inImage = new ImageResource(f);
            ImageResource outImage = imageInversion(inImage);
            String fName = inImage.getFileName();
            String newName = "inverted-" + fName;
            outImage.setFileName(newName);
            outImage.draw();
            outImage.save();
        }
    }
}
