import java.awt.image.BufferedImage;
import java.awt.Color;

public class InvertColors extends Converter {
    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth(), h = input.getHeight();
        BufferedImage inverted = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                ARGB p = new ARGB(input.getRGB(x, y));
                ARGB invertedPixel = new ARGB(p.alpha, 255 - p.red, 255 - p.green, 255 - p.blue);
                inverted.setRGB(x, y, invertedPixel.toInt());
            }
        }
        return inverted;
    }
}
