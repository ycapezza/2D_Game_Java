package com.game.game.crew;

public class Crew {

    private String name;
    private String role;
    private int hp;

    public Crew(String role) {
        this.role= role;
        switch (role) {
            case "Pilot":
                this.name = "yohan";
                break;
            case "Engineer":
                this.name = "rob";
                break;
            case "Weaponmaster":
                this.name = "alex";
                break;
            default:
                break;
        }
        this.hp = 100;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isAlive() {
        return getHp() > 0;
    }
}