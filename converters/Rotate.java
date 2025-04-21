import java.awt.image.BufferedImage;

/**
 * Rotates an image 90 degrees clockwise.
 */
public class Rotate extends Converter {

    /**
     * Rotates an image 90 degrees clockwise.
     *
     * @param input The original BufferedImage to rotate.
     * @return A new rotated BufferedImage.
     */
    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();

        // Create output image with swapped width and height
        BufferedImage rotated = new BufferedImage(h, w, BufferedImage.TYPE_INT_ARGB);

        // Move pixels to their new rotated positions
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                rotated.setRGB(h - y - 1, x, input.getRGB(x, y));
            }
        }

        return rotated;
    }
}
