import java.awt.image.BufferedImage;

/**
 * Increase the brightness of an image.
 * Each pixel's RGB values are boosted by a small amount.
 */
public class Brighten extends Converter {

    private final int INCREMENT = 30; // fixed brightness increase

    /**
     * Applies the brighten effect to the input image.
     *
     * @param input The original BufferedImage to brighten.
     * @return A new BufferedImage with increased brightness.
     */
    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();

        // Create output image
        BufferedImage brighter = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // Go through each pixel
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                ARGB p = new ARGB(input.getRGB(x, y));

                // Inrcease brightness via RGB, capped at 255. 
                ARGB brightPixel = new ARGB(p.alpha,
                    Math.min(255, p.red + INCREMENT),
                    Math.min(255, p.green + INCREMENT),
                    Math.min(255, p.blue + INCREMENT));

                brighter.setRGB(x, y, brightPixel.toInt());
            }
        }

        return brighter;
    }
}
