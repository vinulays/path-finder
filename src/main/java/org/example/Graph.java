package org.example;

import java.util.*;

public class Graph {

    private static final int INF = Integer.MAX_VALUE;

    int noOfVertices; // Number of vertices
    List<List<Node>> adj; // Adjacency list
    int[] dist; // Shortest distances from the source
    int[] prev; // Previous vertex in the shortest path

    Graph(int V) {
        this.noOfVertices = V;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++)
            adj.add(new ArrayList<>());

        dist = new int[V];
        prev = new int[V];
    }

    static class Node {
        int vertex, weight;

        Node(int v, int w) {
            vertex = v;
            weight = w;
        }
    }

    // Add edge to the graph
    void addEdge(int u, int v, int w) {
        adj.get(u).add(new Node(v, w));
    }

    // Dijkstra's algorithm to find the shortest path
    void dijkstra(int src) {
        Arrays.fill(dist, INF);
        Arrays.fill(prev, -1);
        dist[src] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
        pq.offer(new Node(src, 0));

        while (!pq.isEmpty()) {
            int u = pq.poll().vertex;

            for (Node neighbor : adj.get(u)) {
                int v = neighbor.vertex;
                int weight = neighbor.weight;

                if (dist[u] != INF && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                    pq.offer(new Node(v, dist[v]));
                }
            }
        }
    }

    // Get the shortest path from source to destination
    List<int[]> getShortestPath(int src, int dest, int rows, int cols) {
        List<int[]> path = new ArrayList<>();
        int current = dest;
        while (current != -1) {
            int row = current / cols;
            int col = current % cols;
            path.add(new int[]{row, col});
            current = prev[current];
        }
        Collections.reverse(path);
        return path;
    }
}
