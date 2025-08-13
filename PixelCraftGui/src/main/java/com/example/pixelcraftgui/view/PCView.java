package com.example.pixelcraftgui.view;

import com.example.pixelcraftgui.model.PCModelObserver;
import com.example.pixelcraftgui.model.converters.ConverterFactory;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class PCView implements PCModelObserver {

    private final Stage stage;
    private final Scene scene;
    private final ImageView imageView = new ImageView();
    private final Group imageGroup = new Group(imageView);
    private final ScrollPane scrollPane = new ScrollPane(imageGroup);
    private final Button loadButton = new Button("Load Image");
    private final Button saveButton = new Button("Save Image");
    private final Button undoButton = new Button("Undo  ⌘Z / Ctrl+Z");
    private final Button redoButton = new Button("Redo  ⇧⌘Z / Ctrl+Y");
    private final Slider zoomSlider = new Slider(0.25, 4.0, 1.0);
    private final Label  zoomLabel  = new Label("Zoom: 100%");

    private final Map<String, Button> converterButtons = new HashMap<>();

    public PCView(Stage stage) {
        this.stage = stage;

        VBox controls = new VBox(10, loadButton, saveButton,
                new Separator(), undoButton, redoButton,
                new Separator(), zoomLabel, zoomSlider);
        controls.setPadding(new Insets(10));

        ConverterFactory.getConverters().forEach((name, clazz) -> {
            Button b = new Button(name);
            b.setMaxWidth(Double.MAX_VALUE);
            converterButtons.put(name, b);
            controls.getChildren().add(b);
        });

        scrollPane.setPannable(true);
        imageView.setPreserveRatio(true);

        BorderPane root = new BorderPane();
        root.setLeft(controls);
        root.setCenter(scrollPane);

        this.scene = new Scene(root, 1100, 750);
        stage.setTitle("PixelCraft Part II");
        stage.setScene(scene);
        stage.show();

        zoomSlider.valueProperty().addListener((obs, oldV, newV) -> {
            double z = newV.doubleValue();
            imageView.setScaleX(z);
            imageView.setScaleY(z);
            zoomLabel.setText(String.format("Zoom: %d%%", Math.round(z * 100)));
        });
    }

    @Override
    public void onImageUpdated(Image image) {
        imageView.setImage(image);
        if (image != null) {
            zoomSlider.setValue(1.0);
            imageView.setScaleX(1.0);
            imageView.setScaleY(1.0);
        }
    }

    public Button getLoadButton() { return loadButton; }
    public Button getSaveButton() { return saveButton; }
    public Button getUndoButton() { return undoButton; }
    public Button getRedoButton() { return redoButton; }
    public Map<String, Button> getAllConverterButtons() { return converterButtons; }
    public Stage getStage() { return stage; }
    public Scene getScene() { return scene; }

    public void setUndoRedoEnabled(boolean canUndo, boolean canRedo) {
        undoButton.setDisable(!canUndo);
        redoButton.setDisable(!canRedo);
    }
}
