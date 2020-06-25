
/**
 * Write a description of GrayScale here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
public class GrayScale {

    public ImageResource grayScaleFilter(ImageResource inImage)
    {
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for(Pixel pixel : outImage.pixels())
        {
            Pixel inpixel = inImage.getPixel(pixel.getX(),pixel.getY());
            int average = (inpixel.getRed() + inpixel.getGreen() + inpixel.getBlue())/3;
            pixel.setRed(average);
            pixel.setGreen(average);
            pixel.setBlue(average);
        }
        return outImage;
    }
    public void testGrayScaleFilter()
    {
        ImageResource inImage = new ImageResource();
        ImageResource outImage = grayScaleFilter(inImage);
        outImage.draw();
    }
    public void testManyGrayScale()
    {
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles())
        {
            ImageResource inImage = new ImageResource(f);
            ImageResource outImage = grayScaleFilter(inImage);
            String fName = inImage.getFileName();
            String newName = "Gray-" + fName;
            outImage.setFileName(newName);
            outImage.draw();
            outImage.save();
        }
    }
}
