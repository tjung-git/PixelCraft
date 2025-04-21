import java.awt.image.BufferedImage;

public class Grayscale extends Converter {
    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();
        BufferedImage grayed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                ARGB pixel = new ARGB(input.getRGB(x, y));
                int gray = (pixel.red + pixel.green + pixel.blue) / 3;
                ARGB newPixel = new ARGB(pixel.alpha, gray, gray, gray);
                grayed.setRGB(x, y, newPixel.toInt());
            }
        }
        return grayed;
    }
}
