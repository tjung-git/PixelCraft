package com.example.pixelcraftgui.model.converters;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConverterFactory {

    private static final Map<String, Class<? extends Converter>> converters = new LinkedHashMap<>();

    static {
        register("Grayscale", Grayscale.class);
        register("Rotate", Rotate.class);
        register("Brighten", Brighten.class);
        register("ColorSort", ColorSort.class);
        register("GlitchPop", GlitchPop.class);
        register("InvertColors", InvertColors.class);
        register("RecursiveFlip", RecursiveFlip.class);
    }

    private static void register(String name, Class<? extends Converter> clazz) {
        converters.put(name, clazz);
    }

    public static Map<String, Class<? extends Converter>> getConverters() {
        return converters;
    }

    public static Converter create(String name) {
        Class<? extends Converter> clazz = converters.get(name);
        if (clazz != null) {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create converter: " + name, e);
            }
        }
        throw new IllegalArgumentException("No such converter: " + name);
    }
}
