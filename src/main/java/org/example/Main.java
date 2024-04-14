package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
        System.out.println();

        Graph graph = createGraph(mapData);

        List<String> shortestPath = findShortestPath(graph, mapData);

        System.out.println("1. Start at " + "(" + mapData.getStartColumn() + ", " + mapData.getStartRow() + ")");
        for (int i = 0; i < shortestPath.size(); i++) {
            System.out.println(i + 2 + ". " + shortestPath.get(i));

        }

        System.out.println(shortestPath.size() + 2 + ". Done!");
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
                    String label = "(" + (j + 1) + ", " + (i + 1) + ")";
                    graph.addVertex(label);
                }
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] != '0') {
                    String label = "(" + (j + 1) + ", " + (i + 1) + ")";
                    // Add edges to neighbors (up, down, left, right)
                    if (i > 0 && matrix[i - 1][j] != '0') {
                        String neighborLabel = "(" + (j + 1) + ", " + i + ")";
                        graph.addEdge(label, neighborLabel);
                    }
                    if (i < height - 1 && matrix[i + 1][j] != '0') {
                        String neighborLabel = "(" + (j + 1) + ", " + (i + 2) + ")";
                        graph.addEdge(label, neighborLabel);
                    }
                    if (j > 0 && matrix[i][j - 1] != '0') {
                        String neighborLabel = "(" + j + ", " + (i + 1) + ")";
                        graph.addEdge(label, neighborLabel);
                    }
                    if (j < width - 1 && matrix[i][j + 1] != '0') {
                        String neighborLabel = "(" + (j + 2) + ", " + (i + 1) + ")";
                        graph.addEdge(label, neighborLabel);
                    }
                }
            }
        }

        return graph;
    }


    public static List<String> findShortestPath(Graph graph, MapData mapData) {
        String start = "(" + mapData.getStartColumn() + ", " + mapData.getStartRow() + ")";
        String finish = "(" + mapData.getFinishColumn() + ", " + mapData.getFinishRow() + ")";

        Map<String, String> previous = new HashMap<>();
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (Vertex vertex : graph.getVertices()) {
            distances.put(vertex.getLabel(), Integer.MAX_VALUE);
            previous.put(vertex.getLabel(), null);
        }

        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(finish)) {
                break;
            }
            for (Vertex neighbor : graph.getAdjVertices(current)) {
                String neighborLabel = neighbor.getLabel();
                int distanceToNeighbor = calculateDistance(current, neighborLabel, mapData);
                int totalDistance = distances.get(current) + distanceToNeighbor;

                if (totalDistance < distances.get(neighbor.getLabel())) {
                    distances.put(neighborLabel, totalDistance);
                    previous.put(neighborLabel, current);
                    queue.add(neighborLabel);
                }

            }

        }

        List<String> path = new ArrayList<>();
        String current = finish;

        while (!current.equals(start)) {
            String previousVertex = previous.get(current);
            path.add(getDirection(previousVertex, current));
            current = previousVertex;
        }

        Collections.reverse(path);
        return path;

    }


    private static String getDirection(String fromVertex, String toVertex) {
        int fromColumn = Integer.parseInt(fromVertex.substring(1, fromVertex.indexOf(",")));
        int fromRow = Integer.parseInt(fromVertex.substring(fromVertex.indexOf(",") + 1, fromVertex.length() - 1).trim());

        int toColumn = Integer.parseInt(toVertex.substring(1, toVertex.indexOf(",")));
        int toRow = Integer.parseInt(toVertex.substring(toVertex.indexOf(",") + 1, toVertex.length() - 1).trim());

        if (fromRow < toRow) {
            return "Move down to " + toVertex;
        } else if (fromRow > toRow) {
            return "Move up to " + toVertex;
        } else {
            if (fromColumn < toColumn) {
                return "Move right to " + toVertex;
            } else {
                return "Move left to " + toVertex;
            }
        }
    }

    private static int calculateDistance(String current, String neighbor, MapData mapData) {
        // Extract the row and column indices from the vertex labels
        int currentColumn = Integer.parseInt(current.substring(1, current.indexOf(",")));
        int currentRow = Integer.parseInt(current.substring(current.indexOf(",") + 1, current.length() - 1).trim());

        int neighborColumn = Integer.parseInt(neighbor.substring(1, neighbor.indexOf(",")));
        int neighborRow = Integer.parseInt(neighbor.substring(neighbor.indexOf(",") + 1, neighbor.length() - 1).trim());

        // Calculate the distance based on sliding movement
        int distance = 0;

        // Determine the direction of movement (up, down, left, or right)
        if (currentRow == neighborRow) { // Movement is horizontal
            distance = Math.abs(currentColumn - neighborColumn);
        } else if (currentColumn == neighborColumn) { // Movement is vertical
            distance = Math.abs(currentRow - neighborRow);
        }

        // Check for obstacles (0 cells) in the path and update distance accordingly
        for (int i = 1; i <= distance; i++) {
            int nextRow = currentRow;
            int nextColumn = currentColumn;
            if (currentRow == neighborRow) { // Movement is horizontal
                nextColumn = currentColumn < neighborColumn ? currentColumn + i : currentColumn - i;
            } else { // Movement is vertical
                nextRow = currentRow < neighborRow ? currentRow + i : currentRow - i;
            }

            // Check if the next position contains an obstacle
            if (mapData.getMap()[nextRow - 1][nextColumn - 1] == '0') {
                distance = i - 1; // Adjust distance to stop at the obstacle
                break;
            }
        }

        return distance;
    }

}