import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ColorSort extends Converter {

    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();

        BufferedImage sortedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // Flatten image px into array
        int[] px = new int[w * h];
        int i = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                px[i++] = input.getRGB(x, y);
            }
        }

        // Sort px by color value (ARGB)
        Arrays.sort(px);

        // Reassign sorted px back to image
        i = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                sortedImage.setRGB(x, y, px[i++]);
            }
        }

        return sortedImage;
    }
}
