package com.example.battleshipgame;

import java.util.Arrays;

public class Board {

    private boolean[][] isShottedArray;
    private boolean[][] containsShipArray;

    private int ships;



    public Board() {
        /*
        dla testu robimy tablice o 2 wieksze
        to jest wersja standardowa
         isShottedArray = new boolean[10][10];
        containsShipArray = new boolean[10][10];
         */

        //niby dziala poki co
        isShottedArray = new boolean[12][12];
        containsShipArray = new boolean[12][12];



    }

    public void addShotPosition(Position shotPosition){
        isShottedArray[shotPosition.getRow()][shotPosition.getColumn()] = true;
    }

    public boolean isShipsLimitReached(){
        return ships == 10;
    }

    public boolean addShipPosition(Position shipPosition){
        //trzeba tutaj dodac algorytmy zmieniajace sie w zaleznosci od wyboru gracza
        //wybor gracza polega na wybraniu rodzaju statku
        //do metody przekazujemy numer od 1-4 ktory mowi nam o rozmiarze statku

        if (ships < 10){
            if (!containsShipArray[shipPosition.getRow() - 1][shipPosition.getColumn() - 1] &&
                    !containsShipArray[shipPosition.getRow() - 1][shipPosition.getColumn()]     &&
                    !containsShipArray[shipPosition.getRow() - 1][shipPosition.getColumn() + 1] &&
                    !containsShipArray[shipPosition.getRow()][shipPosition.getColumn() + 1]     &&
                    !containsShipArray[shipPosition.getRow() + 1][shipPosition.getColumn() + 1] &&
                    !containsShipArray[shipPosition.getRow() + 1][shipPosition.getColumn()]     &&
                    !containsShipArray[shipPosition.getRow() + 1][shipPosition.getColumn() - 1] &&
                    !containsShipArray[shipPosition.getRow()][shipPosition.getColumn() - 1]){

                containsShipArray[shipPosition.getRow()][shipPosition.getColumn()] = true;
                ships++;


                return true;

            }else {
                return false;
            }
        }

        return false;


    }

    public void clearBoards(){
        isShottedArray = new boolean[12][12];
        containsShipArray = new boolean[12][12];
        ships = 0;
    }


    public boolean containsShip(Position position){
        return containsShipArray[position.getRow()][position.getColumn()];
    }

    public boolean checkIfShotted(Position shotPosition){
        return isShottedArray[shotPosition.getRow()][shotPosition.getColumn()];
    }



    @Override
    public String toString() {

        String isShottedString = "";
        String containsShipString = "";

        for (int i = 0; i < isShottedArray.length; i++){
            for (int j = 0; j < isShottedArray.length; j++){
                isShottedString += " " + isShottedArray[i][j] + " ";
            }
            isShottedString += "\n";
        }
        for (int i = 0; i < containsShipArray.length; i++){
            for (int j = 0; j < containsShipArray.length; j++){
                containsShipString += " " + containsShipArray[i][j] + " ";
            }
            containsShipString += "\n";
        }



       return isShottedString + "\n\n" + containsShipString;
    }
}
