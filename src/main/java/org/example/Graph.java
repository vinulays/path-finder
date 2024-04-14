package org.example;

import java.util.*;

public class Graph {
    private final Map<Vertex, Set<Vertex>> adjacencyVertices;
    private final Set<Vertex> vertices;

    public Graph() {
        this.adjacencyVertices = new HashMap<>();
        this.vertices = new HashSet<>();
    }

    void addVertex(String label) {
        vertices.add(new Vertex(label));
        adjacencyVertices.putIfAbsent(new Vertex(label), new HashSet<>());

    }

    public Set<Vertex> getVertices() {
        return vertices;
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

        Set<Vertex> eV1 = adjacencyVertices.get(v1);
        Set<Vertex> eV2 = adjacencyVertices.get(v2);

        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);
    }

    Set<Vertex> getAdjVertices(String label) {
        return adjacencyVertices.get(new Vertex(label));
    }

    public void printGraph() {
        System.out.println("Graph:");
        for (Map.Entry<Vertex, Set<Vertex>> entry : adjacencyVertices.entrySet()) {
            Vertex vertex = entry.getKey();
            Set<Vertex> adjacentVertices = entry.getValue();
            System.out.print("Vertex " + vertex.getLabel() + ": ");
            for (Vertex adjacentVertex : adjacentVertices) {
                System.out.print(adjacentVertex.getLabel() + " ");
            }
            System.out.println();
        }
    }
}
