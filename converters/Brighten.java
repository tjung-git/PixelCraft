import java.awt.image.BufferedImage;

public class Brighten extends Converter {
    private final int INCREMENT = 30;

    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth(), h = input.getHeight();
        BufferedImage brighter = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                ARGB p = new ARGB(input.getRGB(x, y));
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
