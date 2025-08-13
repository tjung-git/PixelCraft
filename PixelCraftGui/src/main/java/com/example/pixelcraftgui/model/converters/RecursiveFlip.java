package com.example.pixelcraftgui.model.converters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class RecursiveFlip implements Converter {

    @Override
    public Image convertImage(Image inputImage) {
        int width  = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage output = new WritableImage(width, height);

        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        flipRowRecursive(reader, writer, width, height, 0);
        return output;
    }

    private void flipRowRecursive(PixelReader reader, PixelWriter writer,
                                  int width, int height, int y) {
        if (y >= height) return;
        flipPixelsInRow(reader, writer, width, y, 0);
        flipRowRecursive(reader, writer, width, height, y + 1);
    }

    private void flipPixelsInRow(PixelReader reader, PixelWriter writer,
                                 int width, int y, int x) {
        if (x >= width) return;
        int mirroredX = width - 1 - x;
        writer.setArgb(mirroredX, y, reader.getArgb(x, y));
        flipPixelsInRow(reader, writer, width, y, x + 1);
    }
}
