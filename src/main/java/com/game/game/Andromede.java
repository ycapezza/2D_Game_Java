package com.game.game;
import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Andromede extends Map {
    public static void createMap(Pane root, Scene scene) {
        addButtons(6);
        final Image[] backgroundImage = new Image[1];
        final ImageView[] backgroundImageViewArray = {backgroundImageView};
        scene.getRoot().requestFocus();
        if(score == 0) {
            hideButton(buttons);
            createButton(buttons.get(0), 155, 40, 272);
            backgroundImage[0] = new Image("file:src/main/java/com/game/game/images/Map1.png");
            startGame(buttons.get(0), scene, root, new EnnemyShip());
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        startGame(scene, root, new EnnemyShip());
                    }
                }
            });
        }
        else if(score == 1) {
            buttons.get(0).setVisible(false);
            hideButton(buttons);
            createButton(buttons.get(1),  50, 227, 385);
            createButton(buttons.get(2), 150, 175, 190);
            backgroundImage[0] = new Image("file:src/main/java/com/game/game/images/Map2.png");
            startGame(buttons.get(2), scene, root, new Q7Ship());
            startGame(buttons.get(1), scene, root, new SlugShip());
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.DOWN) {
                        updateButton(buttons.get(2), 55, 230, 245);
                        updateButton(buttons.get(1), 130, 210, 320);
                        Image newImage = new Image("file:src/main/java/com/game/game/images/Map3.png");
                        updateImage(newImage);
                    }
                    else if(event.getCode() == KeyCode.UP) {
                        updateButton(buttons.get(2), 150, 175, 190);
                        updateButton(buttons.get(1),  50, 227, 385);
                        Image newImage = new Image("file:src/main/java/com/game/game/images/Map2.png");
                        updateImage(newImage);
                    }
                    if (event.getCode() == KeyCode.ENTER) {
                        String normalizedPath = new File(backgroundImageView.getImage().getUrl()).getPath();
                        if (normalizedPath.equals(new File("file:src/main/java/com/game/game/images/Map3.png").getPath())) {
                            startGame(scene, root, new SlugShip());
                        } else {
                            startGame(scene, root, new Q7Ship());
                        }
                    }
                }
            });
        }
        else {
            buttons.get(1).setVisible(false);
            buttons.get(2).setVisible(false);
            hideButton(buttons);
            backgroundImage[0] = new Image("file:src/main/java/com/game/game/images/Map4.png");
            createButton(buttons.get(3), 150, 350, 90);
            createButton(buttons.get(4), 55, 355, 325);
            createButton(buttons.get(5), 55, 357, 450);
            startGame(buttons.get(3), scene, root, new LjanjooShip());
            startGame(buttons.get(4), scene, root, new H5Ship());
            startGame(buttons.get(5), scene, root, new BossShip());
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                int  compteur = 0;
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.DOWN) {
                        if(compteur == 0) {
                            updateButton(buttons.get(3), 60, 355, 184);
                            updateButton(buttons.get(4), 150, 372, 265);
                            Image newImage = new Image("file:src/main/java/com/game/game/images/Map5.png");
                            updateImage(newImage);
                            compteur = 1;
                        }
                        else if(compteur == 1) {
                            updateButton(buttons.get(4), 55, 355, 325);
                            updateButton(buttons.get(5), 175, 365, 460);
                            Image newImage = new Image("file:src/main/java/com/game/game/images/Map6.png");
                            updateImage(newImage);
                            compteur = 2;
                        }
                    }
                    else if(event.getCode() == KeyCode.UP) {
                        if(compteur == 1) {
                            updateButton(buttons.get(3), 150, 350, 90);
                            updateButton(buttons.get(4), 55, 355, 325);
                            Image newImage = new Image("file:src/main/java/com/game/game/images/Map4.png");
                            updateImage(newImage);
                            compteur = 0;
                        }
                        else if(compteur == 2) {
                            updateButton(buttons.get(4), 150, 372, 265);
                            updateButton(buttons.get(5), 55, 357, 450);
                            Image newImage = new Image("file:src/main/java/com/game/game/images/Map5.png");
                            updateImage(newImage);
                            compteur = 1;
                        }
                    }
                    if (event.getCode() == KeyCode.ENTER) {
                        String normalizedPath = new File(backgroundImageView.getImage().getUrl()).getPath();
                        if (normalizedPath.equals(new File("file:src/main/java/com/game/game/images/Map4.png").getPath())) {
                            startGame(scene, root, new LjanjooShip());
                        } else if (normalizedPath.equals(new File("file:src/main/java/com/game/game/images/Map5.png").getPath())) {
                            startGame(scene, root, new H5Ship());
                        } else {
                            startGame(scene, root, new BossShip());
                        }
                    }
                }
            });
        }
        backgroundImageView = new ImageView(backgroundImage[0]);

        backgroundImageView.setFitWidth(1254);
        backgroundImageView.setFitHeight(706);
        root.getChildren().add(backgroundImageView);
        root.getChildren().addAll(buttons);
        finish(root, scene);
    }
    public static void finish(Pane root, Scene scene) {
        if(score >= 3) {
            root.getChildren().removeAll(buttons);
            Image finish = new Image("file:src/main/java/com/game/game/images/Finish.png");
            ImageView win = new ImageView(finish);
            win.setFitWidth(scene.getWidth());
            win.setFitHeight(scene.getHeight());
            win.setX((scene.getWidth() - win.getBoundsInLocal().getWidth()) / 2);
            win.setY((scene.getHeight() - win.getBoundsInLocal().getHeight()) / 2);
            root.getChildren().add(win);
        }
    }
}
