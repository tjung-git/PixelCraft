import java.awt.image.BufferedImage;

public class RecursiveFlip extends Converter {

    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        BufferedImage flipped = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        flipRowRecursive(input, flipped, 0);
        return flipped;
    }

    // Recursive by rows, not pixels.
    private void flipRowRecursive(BufferedImage original, BufferedImage flipped, int y) {
        if (y >= original.getHeight()) {
            return; // base case: all rows done
        }

        flipPixelsInRow(original, flipped, y, 0); // handle current row
        flipRowRecursive(original, flipped, y + 1); // recursive call for next row
    }

    // Recursive for each pixel in a row.
    private void flipPixelsInRow(BufferedImage original, BufferedImage flipped, int y, int x) {
        if (x >= original.getWidth()) {
            return; // base case: end of row
        }

        int mirroredX = original.getWidth() - 1 - x;
        flipped.setRGB(mirroredX, y, original.getRGB(x, y));

        flipPixelsInRow(original, flipped, y, x + 1); // recursive call next pixel
    }
}
