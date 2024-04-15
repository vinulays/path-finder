package org.example;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<Integer, LinkedList<Integer>> adjList;

    public Graph(int vertices) {
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

}
