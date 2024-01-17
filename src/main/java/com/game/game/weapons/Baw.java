package com.game.game.weapons;

import java.util.Timer;
import java.util.TimerTask;

public class Baw implements Weapon {
    private String name = "Baw";
    private int damage = 3;
    private double fireRate = 1500;
    private boolean readyToFire = true;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public double getFireRate() {
        return fireRate;
    }

    @Override
    public boolean isReadyToFire() {
        return readyToFire;
    }

    @Override
    public void reload() {
        if (!isReadyToFire()) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(name + " reloaded");
                    readyToFire = true;
                }
            }, (long) fireRate);
        }
    }

    @Override
    public double enhanceFireRate() {
        return fireRate;
    }

    @Override
    public void setReadyToFire(boolean readyToFire) {
        this.readyToFire = readyToFire;
    }

    @Override
    public void fire() {
        if (isReadyToFire()) {
            System.out.println(name + " fired!");
            readyToFire = false;
            reload();
        } else {
            System.out.println(name + " is still reloading. Cannot fire yet.");
        }
    }
}
