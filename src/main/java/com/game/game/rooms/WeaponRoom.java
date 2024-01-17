package com.game.game.rooms;

import java.util.List;

import com.game.game.weapons.*;
import com.game.game.crew.*;

public class WeaponRoom extends Room {

    private List<Weapon> weapons;

    public WeaponRoom(Crew weaponMan, List<Weapon> weapons) {
        super("Weapons", weaponMan);
        setHp(4);
        this.weapons = weapons;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public boolean isReadyToFire() {
        for (Weapon weapon : weapons) {
            if (weapon.isReadyToFire()) {
                return true;
            }
        }
        return false;
    }

    public double enhanceShotRate() {
        if (!isCrewActive() || getHp() == 0) {
            return 1.0;
        } else if (getHp() < 4) {
            return 0.9;
        }
        return 0.8;
    }
}