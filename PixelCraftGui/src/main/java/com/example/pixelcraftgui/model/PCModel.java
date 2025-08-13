package com.example.pixelcraftgui.model;

import javafx.application.Platform;
import javafx.scene.image.Image;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class PCModel {

    private Image currentImage;
    private final List<PCModelObserver> observers = new ArrayList<>();
    private final Deque<Image> undoStack = new ArrayDeque<>();
    private final Deque<Image> redoStack = new ArrayDeque<>();
    private int historyLimit = 25; // keep memory in check

    public synchronized void addObserver(PCModelObserver observer) {
        if (observer != null && !observers.contains(observer)) observers.add(observer);
    }

    public synchronized void removeObserver(PCModelObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        Image snapshot;
        List<PCModelObserver> listeners;
        synchronized (this) {
            snapshot = currentImage;
            listeners = List.copyOf(observers);
        }
        if (Platform.isFxApplicationThread()) {
            for (PCModelObserver o : listeners) o.onImageUpdated(snapshot);
        } else {
            Platform.runLater(() -> listeners.forEach(o -> o.onImageUpdated(snapshot)));
        }
    }

    public synchronized Image getImage() { return currentImage; }

    public synchronized boolean canUndo() { return !undoStack.isEmpty(); }
    public synchronized boolean canRedo() { return !redoStack.isEmpty(); }
    public synchronized int undoCount() { return undoStack.size(); }
    public synchronized int redoCount() { return redoStack.size(); }

    public synchronized void setHistoryLimit(int limit) {
        historyLimit = Math.max(0, limit);
        trimStacks();
    }

    private void trimStacks() {
        while (undoStack.size() > historyLimit) undoStack.removeFirst();
        while (redoStack.size() > historyLimit) redoStack.removeFirst();
    }

    public void load(Image img) {
        synchronized (this) {
            currentImage = img;
            undoStack.clear();
            redoStack.clear();
        }
        notifyObservers();
    }

    public void apply(Image newImage) {
        if (newImage == null) return;
        synchronized (this) {
            if (currentImage != null) {
                undoStack.addLast(currentImage);
                if (undoStack.size() > historyLimit) undoStack.removeFirst();
            }
            currentImage = newImage;
            redoStack.clear();
        }
        notifyObservers();
    }

    public void undo() {
        Image prev;
        Image cur;
        synchronized (this) {
            if (undoStack.isEmpty()) return;
            prev = undoStack.removeLast();
            cur = currentImage;
            if (cur != null) {
                redoStack.addLast(cur);
                if (redoStack.size() > historyLimit) redoStack.removeFirst();
            }
            currentImage = prev;
        }
        notifyObservers();
    }

    public void redo() {
        Image next;
        Image cur;
        synchronized (this) {
            if (redoStack.isEmpty()) return;
            next = redoStack.removeLast();
            cur = currentImage;
            if (cur != null) {
                undoStack.addLast(cur);
                if (undoStack.size() > historyLimit) undoStack.removeFirst();
            }
            currentImage = next;
        }
        notifyObservers();
    }
}
