package com.example.pixelcraftgui.model.converters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class InvertColors implements Converter {

    @Override
    public Image convertImage(Image inputImage) {
        int width  = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage output = new WritableImage(width, height);

        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ARGB p = new ARGB(reader.getArgb(x, y));
                ARGB inv = new ARGB(
                    p.alpha,
                    255 - p.red,
                    255 - p.green,
                    255 - p.blue
                );
                writer.setArgb(x, y, inv.toInt());
            }
        }

        return output;
    }
}
