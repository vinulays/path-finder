package org.example;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
    private final int V;
    private final int startPosition;
    private final int finishPosition;

    private final int width;
    private final Map<Integer, LinkedList<Integer>> adjList;

    public Graph(int vertices, int startPosition, int finishPosition, int width) {
        V = vertices;
        this.startPosition = startPosition;
        this.finishPosition = finishPosition;
        this.width = width;

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

    public int getStartPosition() {
        return startPosition;
    }

    public int getFinishPosition() {
        return finishPosition;
    }

    public int getWidth() {
        return width;
    }
}
