package com.game.game;

import com.game.game.rooms.Room;
import static javafx.application.Application.launch;

import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.nio.channels.Pipe;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.File;
import java.net.MalformedURLException;

import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.animation.PauseTransition;
import javafx.scene.text.Text;
import javafx.scene.media.*;

import com.game.game.weapons.Weapon;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

public class GameEngine extends Application {

    private static SpaceShip playerShip; // représentent les vaisseaux du joueur
    private static SpaceShip ennemyShip; // représentent les vaisseaux de l'enemie
    private static Pane root; // contient tous les éléments graphiques de la scène
    private static Scene scene;

    private static Weapon weapon;
    private static ImageView projectile;
    private static TranslateTransition projectileTransition;
    private static ImageView explosion;

    private static ImageView projectile2;
    private static ImageView projectile3;
    private static ImageView projectile4;
    private static TranslateTransition projectileTransition2;
    private static TranslateTransition projectileTransition3;
    private static TranslateTransition projectileTransition4;
    private static ImageView explosion2;
    private static ImageView explosion3;
    private static ImageView explosion4;
    private static Image fireImage = new Image("file:src/main/java/com/game/game/images/firePlayer.png");
    private static ImageView fireImageView = new ImageView(fireImage);
    private static ImageView fireImageView2 = new ImageView(fireImage);
    private static Button fireButton = new Button("", fireImageView);// utilisé pour déclencher l'attaque du joueur
    private static  Button fireButton2 = new Button("", fireImageView2); // utilisé pour déclencher l'attaque du joueur

    private static Image explosionImage = new Image("file:src/main/java/com/game/game/images/explosion.gif");

    private static Image gameOver = new Image("file:src/main/java/com/game/game/images/gameOver.png");
    private static Image win = new Image("file:src/main/java/com/game/game/images/win.png");
    private static ScheduledExecutorService executor;
    private static int compteur;
    private static Button buttonContinue = new Button("Continue >");
    private static MediaPlayer mediaPlayer;

    public static void main(String[] args) {
        launch(args);
    }

    // ***MÉTHODE START: Elle configure et initialise l'interface graphique***//
    @Override
    public void start(Stage primaryStage) {
        String musicFile = "src/main/java/com/game/game/images/music.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

        primaryStage.setTitle("Space Battle Game");
        root = new Pane();

        scene = new Scene(root, 1254, 706);
        primaryStage.setScene(scene);

        Andromede.createMap(root, scene);
        mediaPlayer.play();

        primaryStage.show();
    }

    // ***initializeGame: crée les vaisseaux du joueur et de l'ennemi, puis ajoute
    // les images de leurs "rooms" (pièces) à la scène***//
    private static void initializeGame(SpaceShip ennemy) {
        playerShip = new PlayerShip();
        ennemyShip = ennemy;
        weapon = playerShip.getWeapons().get(0);
        playerShip.lifeBar(root, -200, (-175));
        ennemyShip.lifeBar(root, 450, (-175));
    }

    private static void enemyShoot() {
        executor = Executors.newSingleThreadScheduledExecutor();
        projectile4 = new ImageView(ennemyShip.getLaser());
        projectile3 = new ImageView(ennemyShip.getLaser());
        projectileTransition4 = new TranslateTransition(Duration.seconds(0.5), projectile4);
        explosion4 = new ImageView(explosionImage);
        explosion4.setVisible(false);
        explosion3 = new ImageView(explosionImage);
        explosion3.setVisible(false);
        compteur = 0;
        Runnable addNewNumber = () -> {
            Platform.runLater(()->{
                if(playerShip.getHp() <= ennemyShip.getWeapons().get(0).getDamage()) {
                    ImageView lost = new ImageView(gameOver);
                    root.getChildren().add(lost);
                    displayMessage(lost);
                    executor.shutdown();
                }
                shoot();
                if(ennemyShip.attack(playerShip, ennemyShip.getWeapons().get(0))) {
                    if (compteur % 2 == 0) {
                        projectileTransition3 = ennemyShip.transaction(projectile3, 0, ennemyShip.getY1());
                        root.getChildren().add(projectile3);
                        root.getChildren().add(explosion4); // Ajouter l'explosion à la scène avant le début de la transition
                        projectileTransition3.play();

                        projectileTransition3.setOnFinished(e -> {
                            // Déplacez l'explosion à la fin du chemin du projectile
                            explosion4.setVisible(true);
                            explosion4.setTranslateX(131);
                            explosion4.setTranslateY(150);
                            System.out.println("explosion !");
                            // Lancez l'animation de l'explosion
                            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.4), explosion4);
                            scaleTransition.play();

                            scaleTransition.setOnFinished(scaleFinished -> {
                                // Retirez le projectile et l'explosion
                                root.getChildren().remove(projectile3);
                                root.getChildren().remove(explosion4);
                            });
                        });
                    } else {
                        projectileTransition4 = ennemyShip.transaction(projectile4, 0, ennemyShip.getY2());
                        root.getChildren().add(projectile4);
                        root.getChildren().add(explosion3); // Ajouter l'explosion à la scène avant le début de la transition
                        projectileTransition4.play();

                        projectileTransition4.setOnFinished(e -> {
                            // Déplacez l'explosion à la fin du chemin du projectile
                            explosion3.setVisible(true);
                            explosion3.setTranslateX(131);
                            explosion3.setTranslateY(400);
                            System.out.println("explosion !");
                            // Lancez l'animation de l'explosion
                            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.4), explosion3);
                            scaleTransition.play();

                            scaleTransition.setOnFinished(scaleFinished -> {
                                // Retirez le projectile et l'explosion
                                root.getChildren().remove(projectile4);
                                root.getChildren().remove(explosion3);
                            });
                        });
                    }

                    playerShip.updateLifeBar(root, -200, -175);
                    compteur++;
                } else {
                    System.out.println("Esquive du player");
                    dodge();
                }
            });
        };
        executor.scheduleAtFixedRate(addNewNumber, 0, 5, TimeUnit.SECONDS);
    }

    // ***initializeUI: crée un bouton "Fire" qui permet au joueur de tirer. Ce
    // bouton est initialement désactivé ***//
    private static void initializeUI() {

        fireImageView.setFitWidth(131);
        fireImageView.setFitHeight(73);

        fireImageView2.setFitWidth(131);
        fireImageView2.setFitHeight(73);

        projectile = new ImageView(playerShip.getLaser());
        projectile2 = new ImageView(playerShip.getLaser());

        explosion = new ImageView(explosionImage);
        // explosion.setCache(true);
        explosion.setVisible(false);

        explosion2 = new ImageView(explosionImage);
        // explosion.setCache(true);
        explosion2.setVisible(false);

        Image engineImage = new Image ("file:src/main/java/com/game/game/images/engine.png");
        ImageView engineImageView = new ImageView(engineImage);
        engineImageView.setFitWidth(131);
        engineImageView.setFitHeight(73);

        //***Button de l'arme 1 ***//

        fireButton.setTranslateX(90);
        fireButton.setTranslateY(430);
        fireButton.setOnAction(event -> {
            lifeImg();
            System.out.println("fireeeeeeeeeeee");
            if (weapon.isReadyToFire()) {
                if(playerShip.attack(ennemyShip, weapon)) {
                    shoot();
                    projectileTransition = playerShip.transaction(projectile, 0, playerShip.getY1());
                    ennemyShip.updateLifeBar(root, 450, -175);
                    if (!root.getChildren().contains(projectile)) {
                        root.getChildren().add(projectile);
                    }
                    if (!root.getChildren().contains(explosion)) {
                        root.getChildren().add(explosion);
                    }
                    projectile.setTranslateX(0);
                    projectileTransition.play();

                    fireButton.setDisable(true);

                    projectileTransition.setOnFinished(e -> {
                        // Déplacez l'explosion à la fin du chemin du projectile
                        explosion.setTranslateX(projectile.getTranslateX() + projectile.getBoundsInLocal().getWidth());
                        explosion.setVisible(true);
                        explosion.setTranslateX(900);
                        explosion.setTranslateY(400);
                        System.out.println("explosion !");
                        // Lancez l'animation de l'explosion
                        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.4), explosion);
                        scaleTransition.play();

                        scaleTransition.setOnFinished(scaleFinished -> {
                            // Retirez le projectile et l'explosion
                            root.getChildren().remove(projectile);
                            root.getChildren().remove(explosion);
                            fireButton.setDisable(false);
                        });

                    });
                    playerShip.reloadWeapons();
                }
                else{
                    System.out.println("Esquive de l'ennemi");
                    dodge();
                }
            }
        });


        //***Button de l'arme 2 ***//

        fireButton2.setDisable(true);
        fireButton2.setTranslateX(90);
        fireButton2.setTranslateY(200);
        fireButton2.setOnAction(event -> {
            lifeImg();
            System.out.println("fireeeeeeeeeeee 2");
            if (weapon.isReadyToFire()) {
                if(playerShip.attack(ennemyShip, weapon)) {
                    shoot();
                    projectileTransition2 = playerShip.transaction(projectile2, 0, playerShip.getY2());
                    ennemyShip.updateLifeBar(root, 450, -175);
                    if (!root.getChildren().contains(projectile2)) {
                        root.getChildren().add(projectile2);
                    }
                    if (!root.getChildren().contains(explosion2)) {
                        root.getChildren().add(explosion2);
                    }
                    projectile2.setTranslateX(0);
                    projectileTransition2.play();

                    fireButton2.setDisable(true);

                    projectileTransition2.setOnFinished(e -> {
                        // Déplacez l'explosion à la fin du chemin du projectile
                        explosion2.setTranslateX(projectile.getTranslateX() + projectile.getBoundsInLocal().getWidth());
                        explosion2.setVisible(true);
                        explosion2.setTranslateX(900);
                        explosion2.setTranslateY(150);
                        System.out.println("explosion !");
                        // Lancez l'animation de l'explosion
                        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.4), explosion2);
                        scaleTransition.play();

                        scaleTransition.setOnFinished(scaleFinished -> {
                            // Retirez le projectile et l'explosion
                            root.getChildren().remove(projectile2);
                            root.getChildren().remove(explosion2);
                            fireButton.setDisable(false);
                        });

                    });

                    playerShip.reloadWeapons();
                } else{
                    System.out.println("Esquive de l'ennemi");
                    dodge();
                }
            }
        });

        root.getChildren().add(fireButton);
        root.getChildren().add(fireButton2);
    }

    // ***updateGame: est appelée à chaque itération de la boucle de jeu
    // (AnimationTimer). Pour l'instant, elle ne fait que appeler updateUI ***//
    private static void updateGame() {
        updateUI();
    }

    // *** updateUI: active ou désactive le bouton "Fire" en fonction de la
    // disponibilité de l'arme du joueur ***//
    private static void updateUI() {
        if (weapon.isReadyToFire()) {
            fireButton.setDisable(false);
            fireButton2.setDisable(false);
            //fireButton.setStyle("-fx-background-color: green;");
        } else {
            fireButton.setDisable(true);
            fireButton2.setDisable(true);
            //fireButton.setStyle("-fx-background-color: grey;");
        }
    }
    public static void startGame(Scene scene, Pane root, SpaceShip ennemy) {

        buttonContinue.setDisable(false);
        fireButton.setVisible(true);
        fireButton2.setVisible(true);
        Image backgroundImage = new Image("file:src/main/java/com/game/game/images/fondSpatial.png");
        ImageView backgrounImageView = new ImageView(backgroundImage);

        backgrounImageView.setFitWidth(1254);
        backgrounImageView.setFitHeight(706);
        root.getChildren().add(backgrounImageView);
        initializeGame(ennemy);

        Image playerShipImage = new Image("file:src/main/java/com/game/game/images/playerShip.png");
        ImageView playerShipImageView = new ImageView(playerShipImage);

        playerShipImageView.setFitWidth(605);
        playerShipImageView.setFitHeight(336);

        playerShipImageView.setX(10);
        playerShipImageView.setY((scene.getHeight() - playerShipImageView.getBoundsInLocal().getHeight()) / 2);

        root.getChildren().add(playerShipImageView);

        Image enemyShipImage = new Image("file:src/main/java/com/game/game/images/enemyShip.png");
        ImageView enemyShipImageView = new ImageView(ennemy.getAppearance());

        enemyShipImageView.setFitWidth(715);
        enemyShipImageView.setFitHeight(397);

        enemyShipImageView.setX(scene.getWidth() - enemyShipImageView.getBoundsInLocal().getWidth() - -80);
        enemyShipImageView.setY((scene.getHeight() - enemyShipImageView.getBoundsInLocal().getHeight()) / 2);
        root.getChildren().add(enemyShipImageView);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        }.start();

        initializeUI();
        enemyShoot();
    }
    public static void displayMessage(ImageView img) {
        fireButton.setVisible(false);
        fireButton2.setVisible(false);
        img.setFitWidth(scene.getWidth());
        img.setFitHeight(scene.getHeight());
        img.setX((scene.getWidth() - img.getBoundsInLocal().getWidth()) / 2);
        img.setY((scene.getHeight() - img.getBoundsInLocal().getHeight()) / 2);
        buttonContinue.setStyle(
                "-fx-background-color: #2ecc71; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 22px; " +
                        "-fx-padding: 10px 20px;" +
                        "-fx-background-radius: 15px; "
        );

        buttonContinue.setLayoutX(560);
        buttonContinue.setLayoutY(353);
        root.getChildren().add(buttonContinue);
        buttonContinue.setOnAction(event -> {
            buttonContinue.setDisable(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            executor = Executors.newSingleThreadScheduledExecutor();
            AtomicInteger compteur = new AtomicInteger(3);
            AtomicReference<Text> previousText = new AtomicReference<>(null);

            Runnable timer = () -> {
                Platform.runLater(() -> {
                    Text textToRemove = previousText.getAndSet(null);
                    if (textToRemove != null) {
                        root.getChildren().remove(textToRemove);
                    }
                    Text newText = new Text(Integer.toString(compteur.get()));
                    newText.setStyle("-fx-font-family: Arial; -fx-font-size: 40; " +
                            "-fx-background-color: #FFFFFF; " +
                            "-fx-border-radius: 10; " +
                            "-fx-padding: 10; " +
                            "-fx-border-color: #FFD700; ");
                    newText.setLayoutX(627);
                    newText.setLayoutY(250);
                    root.getChildren().add(newText);

                    previousText.set(newText);
                    compteur.decrementAndGet();
                    if(compteur.get() == -1) {
                        executor.shutdown();
                    }
                });
            };
            executor.scheduleAtFixedRate(timer, 0, 1, TimeUnit.SECONDS);
            pause.setOnFinished(pauseEvent -> {
                root.getChildren().clear();
                Andromede.createMap(root, scene);
            });
            pause.play();
        });
    }
    public static void lifeImg() {
        if(playerShip.getHp() <= 1 && ennemyShip.getHp() <= 0 || ennemyShip.getHp() <= 1) {
            Map.setScore(1);
            ImageView winGame = new ImageView(win);
            root.getChildren().add(winGame);
            displayMessage(winGame);
            executor.shutdown();
        }
    }
    public static void dodge() {
        Image dodge = new Image("file:src/main/java/com/game/game/images/Dodge.png");
        ImageView dodgeImg = new ImageView(dodge);
        dodgeImg.setLayoutX(600);
        dodgeImg.setLayoutY(200);
        dodgeImg.setFitHeight(75);
        dodgeImg.setFitWidth(125);
        root.getChildren().add(dodgeImg);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(pauseEvent -> {
            root.getChildren().remove(dodgeImg);
        });
        pause.play();
    }

    public static void shoot() {
        String music = "src/main/java/com/game/game/images/shoot.mp3";
        Media tir = new Media(new File(music).toURI().toString());
        MediaPlayer shoot = new MediaPlayer(tir);
        shoot.play();
    }
}