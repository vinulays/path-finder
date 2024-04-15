package org.example;

import java.util.LinkedList;
import java.util.List;

public class Graph {
    private int V;
    private final LinkedList<Integer>[] adjList;
    public Graph(int vertices) {
        this.V = vertices;
        adjList = new LinkedList[V];

        for (int i = 0; i < V; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int src, int dest) {
        adjList[src].add(dest);
    }

    public List<Integer> getNeighbors(int vertex) {
        return adjList[vertex];
    }

}
