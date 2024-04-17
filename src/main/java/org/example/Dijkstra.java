package org.example;

import java.util.*;

public class Dijkstra {
    static List<String> findShortestPath(Graph graph, char[][] map, int start, int finish, int width) {
        String startPosition = "(" + ((start / width) + 1) + ", " + ((start % width) + 1) + ")";
//        graph.printMap();
        int height = map.length;

        int V = graph.getV();
        int[] distances = new int[V];
        int[] prev = new int[V];
        PriorityQueue<Integer> pq = new PriorityQueue<>(V, Comparator.comparingInt(i -> distances[i]));

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;
        pq.add(start);

        while (!pq.isEmpty()) {
            int u = pq.poll();

            int currentX = u / width;
            int currentY = u % width;

            int[] dx = {0, 1, 0, -1};
            int[] dy = {-1, 0, 1, 0};

            for (int k = 0; k < 4; k++) {
                int nx = currentX + dx[k];
                int ny = currentY + dy[k];

                if (nx >= 0 && nx < width && ny >= 0 && ny < height && map[ny][nx] != '0') {
                    int v = nx * width + ny;
                    int alt = distances[u] + 1; // Assuming edge weight is 1
                    if (alt < distances[v]) {
                        distances[v] = alt;
                        prev[v] = u;
                        pq.add(v);
                    }
                }
            }
//            List<Integer> neighbors = graph.getNeighbors(u);
//            for (Integer v : neighbors) {
//                int alt = distances[u] + 1; // Assuming edge weight is 1
//                if (alt < distances[v]) {
//                    distances[v] = alt;
//                    prev[v] = u;
//                    pq.add(v);
//                }
//            }
        }

        List<String> path = new ArrayList<>();
        for (int at = finish; at != start; at = prev[at]) {
            if (at == 0) {
                return new ArrayList<>();
            }
            // No path found

            path.add(getDirection(prev[at], at, width));
        }
        path.add("Start at " + startPosition);

        Collections.reverse(path);
        path.add("Done!");

        List<String> numberedPath = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            numberedPath.add((i + 1) + ". " + path.get(i));

        }
        return numberedPath;
    }

    private static String getDirection(int from, int to, int width) {
        int fromX = from / width;
        int fromY = from % width;

        int toX = to / width;
        int toY = to % width;

        if (toX < fromX) return "Move left to (" + (toX + 1) + "," + (toY + 1) + ")";
        if (toX > fromX) return "Move right to (" + (toX + 1) + "," + (toY + 1) + ")";
        if (toY < fromY) return "Move up to (" + (toX + 1) + "," + (toY + 1) + ")";
        if (toY > fromY) return "Move down to (" + (toX + 1) + "," + (toY + 1) + ")";
        return "";
    }
}
