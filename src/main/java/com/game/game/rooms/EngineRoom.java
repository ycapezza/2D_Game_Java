package com.game.game.rooms;

import com.game.game.crew.*;

public class EngineRoom extends Room{
    
    public EngineRoom(Crew engineer) {
        super("Engines", engineer);
        setHp(4);
    }

    public double calculateDodgeChance() {
        if (!isCrewActive() || getHp() <= 0) {
            return 0;
        } 
        else if (getHp() < 4) {
            return 10;
        } 
        else {
            return 20;
        }
    }


}