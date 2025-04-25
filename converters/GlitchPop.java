import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * GlitchPop applies a recursive glitch effect inspired by Valorant's GlitchPop skin series.
 * This uses only recursion to shrink, copy, and paste patches.
 */
public class GlitchPop extends Converter {

    @Override
    protected BufferedImage applyEffect(BufferedImage input) {
        Random r = new Random();
        return glitchPop(input, 90, 0.1, 0.5, r); 
    }

    /**
     * Recursively applies a glitch effect by shrinking and pasting random patches onto the image.
     *
     * @param img         The original BufferedImage to apply glitches to.
     * @param depth       Number of recursive glitch layers 
     * @param patchFrac   mage size used for each random patch (e.g., 0.05 = 5% of width/height).
     * @param shrinkFrac  Patch is scaled down before pasting (e.g., 0.3 = 30% of original size).
     * @param r           Random positioning and effect.
     * @return A new BufferedImage with GlitchPop effect.
     */
    private BufferedImage glitchPop(BufferedImage img, int depth, double patchFrac, double shrinkFrac, Random r) {
        if (depth == 0) return img;

        int w = img.getWidth();
        int h = img.getHeight();

        // Random patch size and position
        int pw = Math.max(1, (int)(w * patchFrac));
        int ph = Math.max(1, (int)(h * patchFrac));
        int x1 = r.nextInt(w - pw);
        int y1 = r.nextInt(h - ph);

        // Shrink patch target size
        int newPw = Math.max(1, (int)(pw * shrinkFrac));
        int newPh = Math.max(1, (int)(ph * shrinkFrac));

        BufferedImage smallPatch = new BufferedImage(newPw, newPh, BufferedImage.TYPE_INT_ARGB);
        shrink(img, smallPatch, x1, y1, pw, ph, 0, 0);

        // New paste location
        int x2 = r.nextInt(w - newPw);
        int y2 = r.nextInt(h - newPh);

        BufferedImage newCanvas = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        copyimg(img, newCanvas, 0); // recursive row copy
        paste(newCanvas, smallPatch, x2, y2, 0, 0); // recursive patch paste

        return glitchPop(newCanvas, depth - 1, patchFrac, shrinkFrac, r);
    }

    // Recursively copy each row
    private void copyimg(BufferedImage src, BufferedImage dst, int y) {
        if (y >= src.getHeight()) return;
        copyRow(src, dst, y, 0);
        copyimg(src, dst, y + 1);
    }

    // Recursively copy one row
    private void copyRow(BufferedImage src, BufferedImage dst, int y, int x) {
        if (x >= src.getWidth()) return;
        dst.setRGB(x, y, src.getRGB(x, y));
        copyRow(src, dst, y, x + 1);
    }

    // Recursively shrink a patch into smallPatch
    private void shrink(BufferedImage src, BufferedImage dst, int x1, int y1, int pw, int ph, int sx, int sy) {
        if (sy >= dst.getHeight()) return;
        if (sx >= dst.getWidth()) {
            shrink(src, dst, x1, y1, pw, ph, 0, sy + 1);
            return;
        }

        int srcX = x1 + (sx * pw / dst.getWidth());
        int srcY = y1 + (sy * ph / dst.getHeight());
        dst.setRGB(sx, sy, src.getRGB(srcX, srcY));

        shrink(src, dst, x1, y1, pw, ph, sx + 1, sy);
    }

    // Recursively paste the patch into canvas
    private void paste(BufferedImage canvas, BufferedImage patch, int xOffset, int yOffset, int sx, int sy) {
        if (sy >= patch.getHeight()) return;
        if (sx >= patch.getWidth()) {
            paste(canvas, patch, xOffset, yOffset, 0, sy + 1);
            return;
        }

        int dstX = xOffset + sx;
        int dstY = yOffset + sy;

        if (dstX < canvas.getWidth() && dstY < canvas.getHeight()) {
            canvas.setRGB(dstX, dstY, patch.getRGB(sx, sy));
        }

        paste(canvas, patch, xOffset, yOffset, sx + 1, sy);
    }
}
