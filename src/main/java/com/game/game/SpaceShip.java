package com.game.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import com.game.game.weapons.Weapon;
import com.game.game.crew.Crew;
import com.game.game.rooms.*;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class SpaceShip {

    private int hp;
    private List<Room> rooms;
    private List<Crew> crew;
    protected List<Weapon> weapons;
    protected Image appearance;
    protected ImageView lifeBarsImg = new ImageView();
    protected ImageView cockpit = new ImageView();
    protected Image laser;
    private int dodge;
    protected int y1, y2;
    protected int from, to;

    public SpaceShip(int unDodge) {
        this.rooms = new ArrayList<>();
        this.crew = new ArrayList<>();
        this.weapons = new ArrayList<>();
        this.hp = 10;
        this.dodge = unDodge;

        crew.add(new Crew("Pilot"));
        crew.add(new Crew("Engineer"));
        crew.add(new Crew("Weaponmaster"));

        rooms.add(new CockpitRoom(crew.get(0)));
        rooms.add(new EngineRoom(crew.get(1)));
        rooms.add(new WeaponRoom(crew.get(2), weapons));


        startHpRegeneration();
        startWeaponReload();
    }

    public Image getAppearance() {
        return this.appearance;
    }
    public Image getLaser() {
        return this.laser;
    }
    public List<Room> getRooms() {
        return rooms;
    }

    public int getHp() {
        return hp;
    }
    public int getY1() {
        return this.y1;
    }
    public int getY2() {
        return this.y2;
    }
    public ImageView getLifeBarsImg() {
        return this.lifeBarsImg;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if(this.hp < 0) {
            this.hp = 0;
        }
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public List<String> getCrewNames() {
        List<String> crewNames = new ArrayList<>();
        for (Crew crew : crew) {
            crewNames.add(crew.getName());
        }
        return crewNames;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<String> getWeaponsNames() {
        List<String> weaponsNames = new ArrayList<>();
        for (Weapon weapon : weapons) {
            weaponsNames.add(weapon.getName());
        }
        return weaponsNames;
    }

    public boolean isShipAlive() {
        return getHp() > 0;
    }

    public boolean isShipHit() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        if(randomNum >= this.dodge) {
            return false;
        }
        return true;
    }

    public boolean receivedamage(Room roomHit, Weapon weapon) {
            roomHit.setHp(roomHit.getHp() - weapon.getDamage());
            setHp(getHp() - weapon.getDamage());
            roomHit.getCrew().setHp(getHp() - 25);
            System.out.println(this.getClass().getName() + " : " );
            for(Room room : this.getRooms()) {
                System.out.println(room.getName()+ " hp restant : " + room.getHp());
            }

        return true;
    }

    public void startHpRegeneration() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Room room : rooms) {
                    room.hpRegen();
                }
            }
        }, 0, 30000);
    }

    public void startWeaponReload() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                reloadWeapons();
            }
        }, 0, 10000);
    }

    public void reloadWeapons() {
        for (Weapon weapon : weapons) {
            weapon.reload();
        }
    }

    public boolean attack(SpaceShip spaceShip, Weapon weapon) {
        weapon.setReadyToFire(false);
        weapon.reload();
        if(rooms.get(2).getHp() != 0) {
            if(isShipHit()) {
                int random = ThreadLocalRandom.current().nextInt(0, 3);
                int countRoom = 0;
                for(Room room : spaceShip.getRooms()) {
                    if(countRoom == random) {
                        spaceShip.receivedamage(room, weapon);
                    }
                    countRoom += 1;
                }
               return true;
            }
        }
        return false;
    }

    public void displayLifeBar(Pane root, int x, int y, Image life) {
        lifeBarsImg.setImage(life);
        lifeBarsImg.setLayoutX(x);
        lifeBarsImg.setLayoutY(y);
        lifeBarsImg.setFitHeight(500);
        lifeBarsImg.setFitWidth(1000);
        root.getChildren().add(lifeBarsImg);
    }
    public void displayCockpit(Pane root, int x, int y, Image character) {
        cockpit.setImage(character);
        cockpit.setLayoutX(x);
        cockpit.setLayoutY(y);
        cockpit.setFitHeight(70);
        cockpit.setFitWidth(70);
        root.getChildren().add(cockpit);
    }
    public TranslateTransition transaction(ImageView projectile, int x, int y) {
        TranslateTransition projectileTransition = new TranslateTransition(Duration.seconds(0.5), projectile);
        projectileTransition.setFromX(this.from);
        projectileTransition.setToX(this.to);
        projectile.setTranslateY(y);
        projectile.setTranslateX(x);
        return projectileTransition;
    }
    public abstract void lifeBar(Pane root, int x, int y);
    public abstract void updateLifeBar(Pane root, int x, int y);
}