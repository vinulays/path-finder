package org.example;

public class MapData {
    private char[][] map;
//    Height of the map
    private int height;
//    Width of the map
    private int width;
//    Coordinates of the starting position
    private int startRow;
    private int startColumn;
//    Coordinates of the ending position
    private int finishRow;
    private int finishColumn;

    public MapData(char[][] map, int height, int width, int startRow, int startColumn, int finishRow, int finishColumn) {
        this.map = map;
        this.height = height;
        this.width = width;
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.finishRow = finishRow;
        this.finishColumn = finishColumn;
    }

    public char[][] getMap() {
        return map;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    public int getFinishRow() {
        return finishRow;
    }

    public void setFinishRow(int finishRow) {
        this.finishRow = finishRow;
    }

    public int getFinishColumn() {
        return finishColumn;
    }

    public void setFinishColumn(int finishColumn) {
        this.finishColumn = finishColumn;
    }
}
