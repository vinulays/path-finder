package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<Vertex, List<Vertex>> adjacencyVertices;

    public Graph() {
        this.adjacencyVertices = new HashMap<>();
    }

    void addVertex(String label) {
        adjacencyVertices.putIfAbsent(new Vertex(label), new ArrayList<>());

    }

    void removeVertex(String label) {
        Vertex vertex = new Vertex(label);

        adjacencyVertices.values().forEach(e -> e.remove(vertex));
        adjacencyVertices.remove(new Vertex(label));
    }

    void addEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);

        adjacencyVertices.get(v1).add(v2);
        adjacencyVertices.get(v2).add(v1);
    }

    void removeEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);

        List<Vertex> eV1 = adjacencyVertices.get(v1);
        List<Vertex> eV2 = adjacencyVertices.get(v2);

        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);
    }

    List<Vertex> getAdjVertices(String label) {
        return adjacencyVertices.get(new Vertex(label));
    }

    public void printGraph() {
        System.out.println("Graph:");
        for (Map.Entry<Vertex, List<Vertex>> entry : adjacencyVertices.entrySet()) {
            Vertex vertex = entry.getKey();
            List<Vertex> adjacentVertices = entry.getValue();
            System.out.print("Vertex " + vertex.getLabel() + ": ");
            for (Vertex adjacentVertex : adjacentVertices) {
                System.out.print(adjacentVertex.getLabel() + " ");
            }
            System.out.println();
        }
    }
}
