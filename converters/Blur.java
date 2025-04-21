import java.awt.image.BufferedImage;

/**
 * Blur applies a simple box blur effect to an image.
 * May be simple, but slightly anoying to implement
 * Refer to: https://www.geeksforgeeks.org/box-blur-algorithm-with-python-implementation/
 */
public class Blur extends Converter {

    /**
     * Applies the blur effect to the input image.
     *
     * @param input The original BufferedImage to blur.
     * @return A new blurred BufferedImage.
     */
    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();

        // Create output image
        BufferedImage blurred = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        int radius = 10;  // blur strength

        // Iterate over every pixel in the image
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                // Sums for colors and alpha
                int r = 0, g = 0, b = 0, a = 0, ct = 0;

                // Loop over a square region centered on (x, y) 
                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dx = -radius; dx <= radius; dx++) {
                        int nx = x + dx;  // Neighbor x
                        int ny = y + dy;  // Neighbor y

                        // Boundary check
                        if (nx >= 0 && nx < w && ny >= 0 && ny < h) {
                            // Get the pixel value at (nx, ny)
                            ARGB pixel = new ARGB(input.getRGB(nx, ny));

                            // tick up colors and pixels
                            r += pixel.red;
                            g += pixel.green;
                            b += pixel.blue;
                            a += pixel.alpha;
                            ct++;  
                        }
                    }
                }

                // Blur via avg color value
                ARGB avgPixel = new ARGB(a / ct, r / ct, g / ct, b / ct);
                blurred.setRGB(x, y, avgPixel.toInt());
            }
        }
        return blurred;
    }
}
