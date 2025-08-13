package com.example.pixelcraftgui.model.converters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.util.Random;

public class GlitchPop implements Converter {

    @Override
    public Image convertImage(Image inputImage) {
        Random r = new Random();
        return glitchPop(inputImage, 90, 0.1, 0.5, r);
    }

    private Image glitchPop(Image img, int depth, double patchFrac, double shrinkFrac, Random r) {
        if (depth == 0) return img;

        int w = (int) img.getWidth();
        int h = (int) img.getHeight();
        PixelReader reader = img.getPixelReader();

        WritableImage canvas = new WritableImage(w, h);
        PixelWriter canvasWriter = canvas.getPixelWriter();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                canvasWriter.setArgb(x, y, reader.getArgb(x, y));
            }
        }

        int pw = Math.max(1, (int) (w * patchFrac));
        int ph = Math.max(1, (int) (h * patchFrac));
        int x1 = r.nextInt(w - pw);
        int y1 = r.nextInt(h - ph);

        int newPw = Math.max(1, (int) (pw * shrinkFrac));
        int newPh = Math.max(1, (int) (ph * shrinkFrac));
        WritableImage patch = new WritableImage(newPw, newPh);
        PixelWriter patchWriter = patch.getPixelWriter();
        for (int sy = 0; sy < newPh; sy++) {
            for (int sx = 0; sx < newPw; sx++) {
                int srcX = x1 + (sx * pw / newPw);
                int srcY = y1 + (sy * ph / newPh);
                patchWriter.setArgb(sx, sy, reader.getArgb(srcX, srcY));
            }
        }

        int x2 = r.nextInt(w - newPw);
        int y2 = r.nextInt(h - newPh);
        PixelReader patchReader = patch.getPixelReader();
        for (int sy = 0; sy < newPh; sy++) {
            for (int sx = 0; sx < newPw; sx++) {
                canvasWriter.setArgb(x2 + sx, y2 + sy, patchReader.getArgb(sx, sy));
            }
        }

        return glitchPop(canvas, depth - 1, patchFrac, shrinkFrac, r);
    }
}
