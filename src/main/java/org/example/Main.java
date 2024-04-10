package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to path finder!");
        System.out.println("===================================");

        System.out.println("Enter the file name:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = reader.readLine();

        MapData mapData = parseMap(fileName);
        System.out.println("Width: " + mapData.getWidth());
        System.out.println("Height: " + mapData.getHeight());
        System.out.println("Start Location: (" + mapData.getStartColumn() + ", " + mapData.getStartRow() + ")");
        System.out.println("Finish Location: (" + mapData.getFinishColumn() + ", " + mapData.getFinishRow() + ")");

//        System.out.println("Map:");
//        for (char[] row : mapData.getMap()) {
//            for (char cell : row) {
//                System.out.print(cell + " ");
//            }
//            System.out.println();
//        }

    }

    public static MapData parseMap(String filename) throws IOException {
        String[] lines;
        int height = 0;
        int width = 0;
        int startRow = 0;
        int startColumn = 0;
        int finishRow = 0;
        int finishColumn = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            lines = reader.lines().toArray(String[]::new);
            height = lines.length;
            for (String line : lines) {
                width = Math.max(width, line.length());
            }
        }

        char[][] grid = new char[height][width];

        // Binary search to find start and finish locations
        for (int i = 0; i < height; i++) {
            int left = 0;
            int right = width - 1;

            // Binary search for start location
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (lines[i].charAt(mid) == 'S') {
                    startRow = i;
                    startColumn = mid;
                    break;
                } else if (lines[i].charAt(mid) < 'S') {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            // Binary search for finish location
            left = 0;
            right = width - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (lines[i].charAt(mid) == 'F') {
                    finishRow = i;
                    finishColumn = mid;
                    break;
                } else if (lines[i].charAt(mid) < 'F') {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            // Construct the grid
            for (int j = 0; j < width; j++) {
                if (j < lines[i].length()) {
                    grid[i][j] = lines[i].charAt(j);
                } else {
                    grid[i][j] = '.';
                }
            }


        }
        return new MapData(grid, height, width, startRow, startColumn, finishRow, finishColumn);


    }
}