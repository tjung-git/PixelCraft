import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.Random;

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

        int pw = (int)(w * patchFrac);
        int ph = (int)(h * patchFrac);
        int x1 = rand.nextInt(w - pw);
        int y1 = rand.nextInt(h - ph);

        BufferedImage patch = img.getSubimage(x1, y1, pw, ph);

        int newPw = (int)(pw * shrinkFrac);
        int newPh = (int)(ph * shrinkFrac);
        BufferedImage smallPatch = new BufferedImage(newPw, newPh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = smallPatch.createGraphics();
        g2d.drawImage(patch, 0, 0, newPw, newPh, null);
        g2d.dispose();

        int x2 = rand.nextInt(w - newPw);
        int y2 = rand.nextInt(h - newPh);

        BufferedImage newCanvas = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newCanvas.createGraphics();
        g.drawImage(img, 0, 0, null); // copy original
        g.drawImage(smallPatch, x2, y2, null); // paste shrunken patch
        g.dispose();

        return glitchPop(newCanvas, depth - 1, patchFrac, shrinkFrac, rand);
    }
}
