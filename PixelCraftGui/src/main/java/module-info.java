module com.example.pixelcraftgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports com.example.pixelcraftgui;
    exports com.example.pixelcraftgui.model;
    exports com.example.pixelcraftgui.model.converters;
    exports com.example.pixelcraftgui.view;
    exports com.example.pixelcraftgui.controller;
}
