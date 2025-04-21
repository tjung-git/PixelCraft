import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * GlitchPop applies a recursive glitch effect inspirted by Valorant's GlitchPop series
 * It manually copies and shrinks patches by pixel manipulation.
 */
public class GlitchPop extends Converter {

    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        Random rand = new Random();
        return glitchPop(input, 5, 0.2, 0.5, rand);
    }

    private BufferedImage glitchPop(BufferedImage img, int depth, double patchFrac, double shrinkFrac, Random rand) {
        if (depth == 0) {
            return img;
        }

        int w = img.getWidth();
        int h = img.getHeight();

        // Pick random patch
        int pw = (int)(w * patchFrac);
        int ph = (int)(h * patchFrac);
        int x1 = rand.nextInt(w - pw);
        int y1 = rand.nextInt(h - ph);

        // Shrink patch manually
        int newPw = Math.max(1, (int)(pw * shrinkFrac));
        int newPh = Math.max(1, (int)(ph * shrinkFrac));

        BufferedImage smallPatch = new BufferedImage(newPw, newPh, BufferedImage.TYPE_INT_ARGB);

        for (int sy = 0; sy < newPh; sy++) {
            for (int sx = 0; sx < newPw; sx++) {
                int srcX = x1 + (int)((sx / (double)newPw) * pw);
                int srcY = y1 + (int)((sy / (double)newPh) * ph);

                srcX = Math.min(srcX, w - 1);
                srcY = Math.min(srcY, h - 1);

                smallPatch.setRGB(sx, sy, img.getRGB(srcX, srcY));
            }
        }

        // Pick random new location
        int x2 = rand.nextInt(w - newPw);
        int y2 = rand.nextInt(h - newPh);

        // Copy original into new image
        BufferedImage newCanvas = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                newCanvas.setRGB(x, y, img.getRGB(x, y));
            }
        }

        // Paste the small patch
        for (int sy = 0; sy < newPh; sy++) {
            for (int sx = 0; sx < newPw; sx++) {
                newCanvas.setRGB(x2 + sx, y2 + sy, smallPatch.getRGB(sx, sy));
            }
        }

        // Recurse
        return glitchPop(newCanvas, depth - 1, patchFrac, shrinkFrac, rand);
    }
}
