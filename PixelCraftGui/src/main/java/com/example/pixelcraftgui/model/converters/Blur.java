package com.example.pixelcraftgui.model.converters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Blur implements Converter {

    @Override
    public Image convertImage(Image inputImage) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage output = new WritableImage(width, height);

        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        int radius = 3;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int r = 0, g = 0, b = 0, a = 0, ct = 0;

                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dx = -radius; dx <= radius; dx++) {
                        int nx = x + dx;
                        int ny = y + dy;

                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            ARGB pixel = new ARGB(reader.getArgb(nx, ny));
                            r += pixel.red;
                            g += pixel.green;
                            b += pixel.blue;
                            a += pixel.alpha;
                            ct++;
                        }
                    }
                }

                ARGB avg = new ARGB(a / ct, r / ct, g / ct, b / ct);
                writer.setArgb(x, y, avg.toInt());
            }
        }

        return output;
    }
}
