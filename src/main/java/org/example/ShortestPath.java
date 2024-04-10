package org.example;

import java.util.*;

public class ShortestPath {

    // Node class to represent vertices and their weights

    public static void main(String[] args) {
        char[][] grid = {
                {'.', '.', '.', '0', '.', '.', '.', '.', 'S'},
                {'.', '.', '.', '.', '0', '.', '.', '.', '.'},
                {'0', '.', '.', '.', '.', '0', '.', '0', '0'},
                {'.', '.', '.', '0', '.', '.', '.', '0', '.'},
                {'.', 'F', '.', '.', '.', '.', '.', '0', '.'},
                {'.', '0', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '0', '.'},
                {'.', '0', '.', '0', '.', '.', '0', '.', '0'},
                {'0', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '0', '0', '.', '.', '.', '.', '.', '0'}
        };

        int noOfRows = grid.length;
        int noOfColumns = grid[0].length;
        int noOfVertices = noOfRows * noOfColumns;

        // creating the graph
        Graph graph = new Graph(noOfVertices);

        // Convert grid into a graph
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right
        for (int i = 0; i < noOfRows; i++) {
            for (int j = 0; j < noOfColumns; j++) {
                if (grid[i][j] != '0') {
                    for (int[] dir : directions) {
                        int newRow = i + dir[0];
                        int newCol = j + dir[1];
                        if (newRow >= 0 && newRow < noOfRows && newCol >= 0 && newCol < noOfColumns && grid[newRow][newCol] != '0') {
                            graph.addEdge(i * noOfColumns + j, newRow * noOfColumns + newCol, 1);
                        }
                    }
                }
            }
        }

        int sourceVertex = -1, destinationVertex = -1; // Source and destination vertices
        // Find source and destination vertices
        for (int i = 0; i < noOfRows; i++) {
            for (int j = 0; j < noOfColumns; j++) {
                if (grid[i][j] == 'S') {
                    sourceVertex = i * noOfColumns + j;
                } else if (grid[i][j] == 'F') {
                    destinationVertex = i * noOfColumns + j;
                }
            }
        }

        if (sourceVertex == -1 || destinationVertex == -1) {
            System.out.println("Source or destination not found!");
            return;
        }

        // Find the shortest path
        graph.dijkstra(sourceVertex);

        // Get the shortest path as a list of coordinates
        List<int[]> shortestPath = graph.getShortestPath(sourceVertex, destinationVertex, noOfRows, noOfColumns);

        // Print navigation-like output
        System.out.println("Navigation:");
        for (int i = 1; i < shortestPath.size(); i++) {
            int[] current = shortestPath.get(i);
            int[] prev = shortestPath.get(i - 1);
            System.out.println((i) + ". Move to (" + (current[1] + 1) + "," + (current[0] + 1) + ")");
        }
        System.out.println("Done!");
    }
}
