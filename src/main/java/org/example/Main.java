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

        char[][] matrix = mapData.getMap();

        Graph graph = new Graph();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] != '0') {
                    String label = "(" + (i + 1) + ", " + (j + 1) + ")";
                    graph.addVertex(label);
                }
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] != '0') {
                    String label = "(" + (i + 1) + ", " + (j + 1) + ")";
                    // Add edges to neighbors (up, down, left, right)
                    if (i > 0 && matrix[i - 1][j] != '0') {
                        String neighborLabel = "(" + i + ", " + (j + 1) + ")";
                        graph.addEdge(label, neighborLabel);
                    }
                    if (i < height - 1 && matrix[i + 1][j] != '0') {
                        String neighborLabel = "(" + (i + 2) + ", " + (j + 1) + ")";
                        graph.addEdge(label, neighborLabel);
                    }
                    if (j > 0 && matrix[i][j - 1] != '0') {
                        String neighborLabel = "(" + (i + 1) + ", " + j + ")";
                        graph.addEdge(label, neighborLabel);
                    }
                    if (j < width - 1 && matrix[i][j + 1] != '0') {
                        String neighborLabel = "(" + (i + 1) + ", " + (j + 2) + ")";
                        graph.addEdge(label, neighborLabel);
                    }
                }
            }
        }

        return graph;


    }


}