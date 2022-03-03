package com.example.battleshipgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {


    private int shipsDestroyed;
    private int wins;



    public void resetShipsDestryed(){
        shipsDestroyed = 0;
    }

    public int getWins() {
        return wins;
    }

    public void addWin(){
        wins++;
    }

    public void addDestroyedShip(){
        this.shipsDestroyed++;
    }

    public int getShipsDestroyed(){
        return shipsDestroyed;
    }







}
