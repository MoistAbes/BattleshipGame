package com.example.battleshipgame;

public class BattleshipGame {

    private boolean isSettingShipsStage;
    private boolean isGameOver;
    private boolean gameStarted;





    public BattleshipGame() {
        this.isSettingShipsStage = true;
    }

    public void setIsGameOver(boolean isGameOver){
        this.isGameOver = isGameOver;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public void setGameStarted(boolean gameStarted){
        this.gameStarted = gameStarted;
    }

    public boolean getGameStarted(){
        return gameStarted;
    }

    public boolean getIsSettingShipsStage(){
        return isSettingShipsStage;
    }

    public void setSettingShipsStage(boolean isSettingShipsStage){
        this.isSettingShipsStage = isSettingShipsStage;
    }


}
