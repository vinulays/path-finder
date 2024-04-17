package org.example;

import java.util.*;

public class Graph {
    private final int V;
    private final int startPosition;
    private final int finishPosition;

    private final int width;

    private final char[][] map;
    private final Map<Integer, LinkedList<Integer>> adjList;

    public Graph(int vertices, int startPosition, int finishPosition, int width, char[][] map) {
        V = vertices;
        this.startPosition = startPosition;
        this.finishPosition = finishPosition;
        this.width = width;
        this.map = map;

        adjList = new HashMap<>();

        for (int i = 0; i < vertices; i++) {
            adjList.put(i, new LinkedList<>());
        }
    }

    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
    }

    public List<Integer> getNeighbors(int vertex) {
        return adjList.get(vertex);
    }

    public int getV() {
        return V;
    }

    void printMap() {
        for (char[] row : map) {
            System.out.println(Arrays.toString(row));
        }
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getFinishPosition() {
        return finishPosition;
    }

    public int getWidth() {
        return width;
    }

    public char[][] getMap() {
        return map;
    }
}
