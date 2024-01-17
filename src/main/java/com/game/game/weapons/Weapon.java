package com.game.game.weapons;
public interface Weapon {
    String getName();

    int getDamage();

    double getFireRate();

    boolean isReadyToFire();

    void reload();

    double enhanceFireRate();

    void setReadyToFire(boolean readyToFire);

    void fire();
}
