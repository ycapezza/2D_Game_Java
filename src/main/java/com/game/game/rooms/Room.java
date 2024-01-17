package com.game.game.rooms;

import com.game.game.crew.*;

public abstract class Room {

    private String name;
    private int hp;
    private Crew crew;
    private boolean crewInRoom;

    public Room(String name, Crew crew) {
        this.name = name;
        this.hp = 4;
        this.crew = crew;
        this.crewInRoom = true;
    }

    public String getName() {
        return name;
    }

    public Crew getCrew() {
        return crew;
    }

    public int getHp() {
        if (this.hp < 0) {
            hp = 0;
        }
        if(this.hp > 4) {
            hp = 4;
        }
        return hp;
    }

    public boolean isCrewActive() {
        return crew.isAlive();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void hpRegen() {
        hp += 1;
    }
}