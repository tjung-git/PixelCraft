package com.example.pixelcraftgui.model.converters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.util.Arrays;

public class ColorSort implements Converter {

    @Override
    public Image convertImage(Image inputImage) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage sortedImage = new WritableImage(width, height);

        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = sortedImage.getPixelWriter();

        int[] px = new int[width * height];
        int idx = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                px[idx++] = reader.getArgb(x, y);
            }
        }

        Arrays.sort(px);

        idx = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                writer.setArgb(x, y, px[idx++]);
            }
        }

        return sortedImage;
    }
}
