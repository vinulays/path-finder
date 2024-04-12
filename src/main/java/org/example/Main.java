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

        Graph graph = createGraph(mapData);
        graph.printGraph();

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
            for (String line : lines) {
//                Getting the width of the map
                width = Math.max(width, line.length());
                height++;

//                Getting coordinates of starting & ending positions
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == 'S') {
                        startRow = height;
                        startColumn = i + 1;
                    } else if (c == 'F') {
                        finishRow = height;
                        finishColumn = i + 1;
                    }
                }
            }
        }

        char[][] grid = new char[height][width];

        for (int i = 0; i < height; i++) {
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

    public static Graph createGraph(MapData mapData) {
        int height = mapData.getHeight();
        int width = mapData.getWidth();

        Graph graph = new Graph(height * width);

        for (int i = 1; i <= height; i++) {
            for (int j = 1; j <= width; j++) {
                int index = (i - 1) * width + j;
                if (mapData.getMap()[i][j] != '0') {
                    // Add edges to neighbors (up, down, left, right)
                    if (i > 1 && mapData.getMap()[i - 1][j] != '0') {
                        graph.addEdge(index, index - width);
                    }
                    if (i < height && mapData.getMap()[i + 1][j] != '0') {
                        graph.addEdge(index, index + width);
                    }
                    if (j > 1 && mapData.getMap()[i][j - 1] != '0') {
                        graph.addEdge(index, index - 1);
                    }
                    if (j < width && mapData.getMap()[i][j + 1] != '0') {
                        graph.addEdge(index, index + 1);
                    }
                }
            }
        }

        return graph;
    }
}