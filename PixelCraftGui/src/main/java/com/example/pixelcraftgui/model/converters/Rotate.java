package com.example.pixelcraftgui.model.converters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Rotate implements Converter {

    @Override
    public Image convertImage(Image inputImage) {
        int w = (int) inputImage.getWidth();
        int h = (int) inputImage.getHeight();
        WritableImage output = new WritableImage(h, w);

        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = reader.getArgb(x, y);
                writer.setArgb(h - y - 1, x, argb);
            }
        }

        return output;
    }
}
