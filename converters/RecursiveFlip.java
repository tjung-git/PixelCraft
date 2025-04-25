import java.awt.image.BufferedImage;

/**
 * Flips an image horizontally using recursion.
 * Tail recursion to make it simple and reduce workload
 */
public class RecursiveFlip extends Converter {

    /**
     * Horizontal flips image recursively.
     *
     * @param input The original BufferedImage to flip.
     * @return A new horizontally flipped BufferedImage.
     */
    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        BufferedImage flipped = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        flipRowRecursive(input, flipped, 0); // start recursion
        return flipped;
    }

    /**
     * Recursively processes each row in the image.
     * Tried using pixels, went too deep, settled with rows
     *
     * @param original The original image.
     * @param flipped  The output flipped image.
     * @param y        The current row to process.
     */
    private void flipRowRecursive(BufferedImage original, BufferedImage flipped, int y) {
        if (y >= original.getHeight()) {
            return; // base case: all rows processed
        }

        flipPixelsInRow(original, flipped, y, 0); // flip pixels in the current row
        flipRowRecursive(original, flipped, y + 1); // move to the next row
    }

    /**
     * Recursively flips pixels within a single row.
     *
     * @param original The original image.
     * @param flipped  The output flipped image.
     * @param y        The row being processed.
     * @param x        The current column in the row.
     */
    private void flipPixelsInRow(BufferedImage original, BufferedImage flipped, int y, int x) {
        if (x >= original.getWidth()) {
            return; // base case: end of the row
        }

        int mirroredX = original.getWidth() - 1 - x;
        flipped.setRGB(mirroredX, y, original.getRGB(x, y));

        flipPixelsInRow(original, flipped, y, x + 1); // move to next pixel in the row
    }
}
