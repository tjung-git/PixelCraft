import java.awt.image.BufferedImage;
import java.awt.Color;

public class Rotate extends Converter {
    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();
        BufferedImage rotated = new BufferedImage(h, w, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                rotated.setRGB(h - y - 1, x, input.getRGB(x, y));
            }
        }
        return rotated;
    }
}
