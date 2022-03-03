package com.example.battleshipgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Position {

    private int column;
    private int row;

    private List<Position> randomPositionList;


    public Position() {
    }

    public Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void buttonIdToPosition(String buttonId){

        String rowString = "";
        String columnString = "";
        boolean isFirst = true;

        try {
            char[] tempCharArray = buttonId.toCharArray();

            for (char letter : tempCharArray){
                if (letter >= 48 && letter <= 57){
                    if (isFirst){
                        rowString += letter;
                        row = Integer.parseInt(rowString) + 1;
                        isFirst = false;
                    }else{
                        columnString += letter;
                        column = Integer.parseInt(columnString) + 1;

                    }

                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public String positionToButtonId(){
        return "boardField" + (row - 1) + "_" + (column - 1);
    }

    public Position getRandomPosition(){
        Random random = new Random();
        return new Position(random.nextInt(10) + 1, random.nextInt(10) + 1);
    }

    public List<Position> randomPositionList(int randomPositionsAmount){
        Random random = new Random();
        randomPositionList = new ArrayList<>();

        for (int i = 0; i <= randomPositionsAmount; i++) {
            Position randomPosition = new Position(random.nextInt(10) + 1, random.nextInt(10) + 1);

            if (!randomPositionList.contains(randomPosition))
                randomPositionList.add(randomPosition);
        }

        return randomPositionList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (column != position.column) return false;
        return row == position.row;
    }

    @Override
    public int hashCode() {
        int result = column;
        result = 31 * result + row;
        return result;
    }

    @Override
    public String toString() {
        List<String> rowCharactersList = new ArrayList<>();
        rowCharactersList.add("A");
        rowCharactersList.add("B");
        rowCharactersList.add("C");
        rowCharactersList.add("D");
        rowCharactersList.add("E");
        rowCharactersList.add("F");
        rowCharactersList.add("G");
        rowCharactersList.add("H");
        rowCharactersList.add("I");
        rowCharactersList.add("J");



        return "" + rowCharactersList.get(row - 1) + column;
    }
}
