package com.game.game.rooms;

import com.game.game.*;
import com.game.game.crew.*;

public class CockpitRoom extends Room{
        
    public CockpitRoom(Crew pilot) {
        super("Cockpit", pilot);
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