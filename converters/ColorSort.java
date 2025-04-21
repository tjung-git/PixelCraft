import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * ColorSort was a funny idea where we just sort the pixels of the image.
 * Grouped by RGB value
 */
public class ColorSort extends Converter {

    /**
     * Applies the color sorting effect to the input image.
     *
     * @param input The original BufferedImage to sort.
     * @return A new BufferedImage with pixels sorted by color value.
     */
    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();

        BufferedImage sortedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // Flatten image pixels into an array
        int[] px = new int[w * h];
        int i = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                px[i++] = input.getRGB(x, y);
            }
        }

        // Sort the pixel array by color value (ARGB integer)
        Arrays.sort(px);

        // Write the sorted pixels back into the new image
        i = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                sortedImage.setRGB(x, y, px[i++]);
            }
        }

        return sortedImage;
    }
}
