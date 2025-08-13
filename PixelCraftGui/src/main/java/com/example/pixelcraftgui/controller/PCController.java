package com.example.pixelcraftgui.controller;

import com.example.pixelcraftgui.model.PCModel;
import com.example.pixelcraftgui.model.converters.Converter;
import com.example.pixelcraftgui.model.converters.ConverterFactory;
import com.example.pixelcraftgui.view.PCView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PCController {

    private final PCModel model;
    private final PCView view;

    public PCController(PCModel model, PCView view) {
        this.model = model;
        this.view = view;
    }

    public void installControllers() {
        view.getLoadButton().setOnAction(e -> { loadImage(); refreshUndoRedo(); });
        view.getSaveButton().setOnAction(e -> saveImage());

        view.getUndoButton().setOnAction(e -> { model.undo(); refreshUndoRedo(); });
        view.getRedoButton().setOnAction(e -> { model.redo(); refreshUndoRedo(); });

        // Auto-bind all converter buttons
        view.getAllConverterButtons().forEach((name, btn) ->
                btn.setOnAction(e -> { applyConverter(name); refreshUndoRedo(); })
        );

        // Keyboard shortcuts
        var accel = view.getScene().getAccelerators();
        accel.put(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN), this::loadImage);
        accel.put(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN), this::saveImage);
        accel.put(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN), () -> { model.undo(); refreshUndoRedo(); });
        accel.put(new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN), () -> { model.redo(); refreshUndoRedo(); });

        refreshUndoRedo(); // initialize disabled state
    }

    private void refreshUndoRedo() {
        view.setUndoRedoEnabled(model.canUndo(), model.canRedo());
    }

    private void loadImage() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Image");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fc.showOpenDialog(view.getStage());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            model.load(image);
        }
    }

    private void saveImage() {
        Image image = model.getImage();
        if (image == null) return;

        FileChooser fc = new FileChooser();
        fc.setTitle("Save Image");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));
        File chosen = fc.showSaveDialog(view.getStage());
        if (chosen == null) return;

        File file = chosen.getName().toLowerCase().endsWith(".png")
                ? chosen : new File(chosen.getParentFile(), chosen.getName() + ".png");

        try { writePng(image, file); } catch (IOException ex) { ex.printStackTrace(); }
    }

    private void applyConverter(String name) {
        Image img = model.getImage();
        if (img == null) return;
        Converter converter = ConverterFactory.create(name);
        model.apply(converter.convertImage(img));
    }

    // Pure JavaFX -> BufferedImage -> ImageIO (no SwingFXUtils)
    private static void writePng(Image fxImage, File file) throws IOException {
        int w = (int) fxImage.getWidth(), h = (int) fxImage.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        var reader = fxImage.getPixelReader();
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                bi.setRGB(x, y, reader.getArgb(x, y));
        ImageIO.write(bi, "png", file);
    }
}
