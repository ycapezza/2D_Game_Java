package com.game.game;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu extends Application {
    private static Stage menuStage;
    private static Pane root;

    private static Image startGameImage = new Image("file:src/main/java/com/game/game/images/startGame.png");
    private static ImageView startGameImageView = new ImageView(startGameImage);
    private static Button startGameButton;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        menuStage = primaryStage;

        root = new Pane();
        Scene scene = new Scene(root, 1254, 706);

        initializeMenu(scene, root);

        primaryStage.setTitle("Votre Titre");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void initializeMenu(Scene scene, Pane root) {

        Image backgroundImage = new Image("file:src/main/java/com/game/game/images/pageStart.png");
        ImageView backgroundImageView = new ImageView(backgroundImage);

        backgroundImageView.setFitWidth(1254);
        backgroundImageView.setFitHeight(706);
        root.getChildren().add(backgroundImageView);

        startGameImageView.setFitWidth(1000);
        startGameImageView.setFitHeight(500);
        startGameButton = new Button("", startGameImageView);

        startGameButton.setBackground(new Background(new BackgroundFill(
                Color.TRANSPARENT, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));

        startGameButton.setPrefSize(1000, 500);
        startGameButton.setTranslateX(90);
        startGameButton.setTranslateY(100);



        startGameButton.setOnAction(event -> {
            String videoFile = "src/main/java/com/game/game/images/homeMovie.mp4";
            Media videoMedia = new Media(new File(videoFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(videoMedia);

            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setFitWidth(1254);
            mediaView.setFitHeight(706);
            mediaView.setPreserveRatio(true);

            root.getChildren().add(mediaView);

            mediaPlayer.setOnEndOfMedia(() -> {
                root.getChildren().remove(startGameImageView);
                root.getChildren().removeAll(startGameButton);
                menuStage.close();
                GameEngine gameEngine = new GameEngine();
                gameEngine.start(new Stage());
            });

            mediaPlayer.play();
        });

        root.getChildren().add(startGameButton);
    }
}
