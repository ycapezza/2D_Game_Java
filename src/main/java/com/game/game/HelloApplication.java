package com.game.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import javafx.scene.layout.Pane;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        primaryStage.setTitle("Space Battle Game");
        Image backgroundImage = new Image("file:src/images/fondSpatial.png");
        ImageView backgrounImageView = new ImageView(backgroundImage);
        backgrounImageView.setFitWidth(1254);
        backgrounImageView.setFitHeight(706);
        Pane root = new Pane();
        root.getChildren().add(backgrounImageView);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}