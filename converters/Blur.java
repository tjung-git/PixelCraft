import java.awt.image.BufferedImage;

public class Blur extends Converter {
    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();
        BufferedImage blurred = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        int radius = 10;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int r = 0, g = 0, b = 0, a = 0, ct = 0;

                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dx = -radius; dx <= radius; dx++) {
                        int nx = x + dx;
                        int ny = y + dy;

                        if (nx >= 0 && nx < w && ny >= 0 && ny < h) {
                            ARGB pixel = new ARGB(input.getRGB(nx, ny));
                            r += pixel.red;
                            g += pixel.green;
                            b += pixel.blue;
                            a += pixel.alpha;
                            ct++;
                        }
                    }
                }

                ARGB avgPixel = new ARGB(a / ct, r / ct, g / ct, b / ct);
                blurred.setRGB(x, y, avgPixel.toInt());
            }
        }
        return blurred;
    }
}
