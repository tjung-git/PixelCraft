package com.example.pixelcraftgui.model.converters;

public class ARGB {
    public int alpha, red, green, blue;

    public ARGB(int pixel) {
        this.alpha = (pixel >> 24) & 0xff;
        this.red = (pixel >> 16) & 0xff;
        this.green = (pixel >> 8) & 0xff;
        this.blue = pixel & 0xff;
    }

    public ARGB(int a, int r, int g, int b) {
        this.alpha = a;
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public int toInt() {
        return (this.alpha << 24) | (this.red << 16) | (this.green << 8) | blue;
    }
}