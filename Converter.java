import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public abstract class Converter {
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage inputImage = ImageIO.read(new File(inputFileName));
        BufferedImage outputImage = applyEffect(inputImage);
        ImageIO.write(outputImage, "PNG", new File(outputFileName));
    }
    protected abstract BufferedImage applyEffect(BufferedImage inputImage);
}
