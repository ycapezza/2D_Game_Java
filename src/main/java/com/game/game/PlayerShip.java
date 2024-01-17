package com.game.game;

import java.util.ArrayList;
import java.util.List;

import com.game.game.*;
import com.game.game.weapons.*;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PlayerShip extends SpaceShip {


    public PlayerShip() {
        super(90);
        Weapon piouWeapon = new Piou();

        weapons.add(piouWeapon);
        this.appearance = new Image("file:src/main/java/com/game/game/images/playerShip.png");
        this.laser = new Image("file:src/main/java/com/game/game/images/laser.png");
        this.y1 = 0;
        this.y2 = -290;
    }

    @Override
    public void lifeBar(Pane root, int x, int y) {
        final Image[] life = new Image[1];
        life[0] = new Image("file:src/main/java/com/game/game/images/useLife10.png");
        final Image[] character = new Image[1];
        character[0] = new Image("file:src/main/java/com/game/game/images/playerCockpit5.png");
        displayLifeBar(root, x, y, life[0]);
        displayCockpit(root, 45, 42, character[0]);
    }

    @Override
    public void updateLifeBar(Pane root, int x, int y) {
        root.getChildren().remove(this.lifeBarsImg);
        final Image[] life = new Image[1];
        life[0] = new Image("file:src/main/java/com/game/game/images/useLife" + this.getHp() + ".png");
        if(this.getHp() % 2 == 0 && this.getHp() != 0) {
            root.getChildren().remove(this.cockpit);
            final Image[] character = new Image[1];
            character[0] = new Image("file:src/main/java/com/game/game/images/playerCockpit" + this.getHp() / 2 + ".png");
            displayCockpit(root, 45, 42, character[0]);
        }
        displayLifeBar(root, x, y, life[0]);
    }

    @Override public TranslateTransition transaction(ImageView projectile, int x, int y) {
        TranslateTransition projectileTransition = new TranslateTransition(Duration.seconds(0.5), projectile);
        projectileTransition.setFromX(-620);
        projectileTransition.setToX(100);
        projectile.setTranslateY(y);
        projectile.setTranslateX(x);
        return projectileTransition;
    }
}