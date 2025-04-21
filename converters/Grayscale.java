import java.awt.image.BufferedImage;

/**
 * Simple Grayscale converter
 */
public class Grayscale extends Converter {

    /**
     * Applies the grayscale effect to the input image.
     *
     * @param input The original BufferedImage to process.
     * @return A new BufferedImage converted to grayscale.
     */
    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth(); 
        int h = input.getHeight();

        // Create output image
        BufferedImage grayed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // Go through each pixel
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                ARGB pixel = new ARGB(input.getRGB(x, y));

                // Average the RGB values to get the grayscale value
                int gray = (pixel.red + pixel.green + pixel.blue) / 3;

                // Set new pixel with original alpha and grayscale color
                ARGB newPixel = new ARGB(pixel.alpha, gray, gray, gray);
                grayed.setRGB(x, y, newPixel.toInt());
            }
        }

        return grayed;
    }
}
