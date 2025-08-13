package com.example.pixelcraftgui.model.converters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Brighten implements Converter {

    private final int INCREMENT = 30;

    @Override
    public Image convertImage(Image inputImage) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage output = new WritableImage(width, height);

        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ARGB pixel = new ARGB(reader.getArgb(x, y));

                ARGB brightPixel = new ARGB(
                    pixel.alpha,
                    Math.min(255, pixel.red + INCREMENT),
                    Math.min(255, pixel.green + INCREMENT),
                    Math.min(255, pixel.blue + INCREMENT)
                );

                writer.setArgb(x, y, brightPixel.toInt());
            }
        }

        return output;
    }
}
