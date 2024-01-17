package com.game.game;

import java.util.ArrayList;
import java.util.List;

import com.game.game.*;
import com.game.game.weapons.Piou;
import com.game.game.weapons.Weapon;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class EnnemyShip extends SpaceShip {

    public EnnemyShip(){
        super(90);
        Weapon weapon = new Piou();

        weapons.add(weapon);
        this.appearance = new Image("file:src/main/java/com/game/game/images/enemyShip.png");
        this.laser = new Image("file:src/main/java/com/game/game/images/laserEnemy.png");
        this.y1 = -290;
        this.y2 = 0;
        this.from = 100;
        this.to = -620;
    }
    @Override public void lifeBar(Pane root, int x, int y) {
        final Image[] life = new Image[1];
        life[0] = new Image("file:src/main/java/com/game/game/images/lifeBar10.png");
        final Image[] character = new Image[1];
        character[0] = new Image("file:src/main/java/com/game/game/images/ennemyCockpit5.png");
        displayLifeBar(root, x, y, life[0]);
        displayCockpit(root, 1130, 37, character[0]);
    }
    @Override public void updateLifeBar(Pane root, int x, int y) {
        root.getChildren().remove(this.lifeBarsImg);
        final Image[] life = new Image[1];
        life[0] = new Image("file:src/main/java/com/game/game/images/lifeBar" + this.getHp() + ".png");
        if(this.getHp() % 2 == 0 && this.getHp() != 0) {
            root.getChildren().remove(this.cockpit);
            final Image[] character = new Image[1];
            character[0] = new Image("file:src/main/java/com/game/game/images/ennemyCockpit" + this.getHp() / 2 + ".png");
            displayCockpit(root, 1130, 37, character[0]);
        }
        displayLifeBar(root, x,  y, life[0]);
    }
}