import java.awt.image.BufferedImage;

/**
 * Simply inverts the colors of an image to create a negative effect.
 */
public class InvertColors extends Converter {

    /**
     * Applies the invert color effect to the input image.
     *
     * @param input The original BufferedImage to process.
     * @return A new BufferedImage with inverted colors.
     */
    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();

        // Create output image
        BufferedImage inverted = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // Go through each pixel
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                ARGB p = new ARGB(input.getRGB(x, y));

                // Invert RGB values, keep alpha the same
                ARGB invertedPixel = new ARGB(p.alpha, 255 - p.red, 255 - p.green, 255 - p.blue);

                inverted.setRGB(x, y, invertedPixel.toInt());
            }
        }

        return inverted;
    }
}
