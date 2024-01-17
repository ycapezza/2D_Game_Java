package com.game.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Map {
    protected static ImageView backgroundImageView = new ImageView();
    protected static List<Button> buttons = new ArrayList<>();
    protected static int score = 0;

    public static void setScore(int point) {
        score += point;
    }
    public static void updateImage(Image newImage) {
        backgroundImageView.setImage(newImage);
    }
    public static void startGame(Scene scene, Pane root, SpaceShip ennemy)  {
        GameEngine.startGame(scene, root, ennemy);
        root.getChildren().remove(backgroundImageView);
        root.getChildren().removeAll(buttons);
    }
    public static void startGame(Button button, Scene scene, Pane root, SpaceShip ennemy)  {
        button.setOnAction(event -> {
            GameEngine.startGame(scene, root, ennemy);
            root.getChildren().remove(backgroundImageView);
            root.getChildren().removeAll(buttons);
        });
    }
    public static void addButtons(int number) {
        for(int i = 0; i < number; i++) {
            buttons.add(new Button());
            buttons.get(i).setStyle("-fx-opacity: 0;");
        }
    }
    public static void createButton(Button button, int rayon, int x, int y) {
        button.setStyle("-fx-background-radius: 50em; " +
                "-fx-min-width: " + rayon + "px; " +
                "-fx-min-height: " + rayon + "px; " +
                "-fx-max-width: " + rayon + "px; " +
                "-fx-max-height: " + rayon + "px; " +
                "-fx-opacity: 0;");
        button.setTranslateX(x);
        button.setTranslateY(y);
    }
    public static void updateButton(Button button, int rayon, int x, int y) {
        button.setStyle("-fx-background-radius: 50em; " +
                "-fx-min-width: " + rayon + "px; " +
                "-fx-min-height: " + rayon + "px; " +
                "-fx-max-width: " + rayon + "px; " +
                "-fx-max-height: " + rayon + "px; " +
                "-fx-opacity: 0;");
        button.setTranslateX(x);
        button.setTranslateY(y);
    }
    public static void hideButton(List<Button> buttons) {
        for(int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setStyle("-fx-opacity: 0;");
        }
    }
}
