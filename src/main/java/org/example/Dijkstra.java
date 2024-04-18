package org.example;

import java.util.*;

public class Dijkstra {
    static List<String> findShortestPath(Graph graph, char[][] map, int start, int finish, int width) {
        String startPosition = "(" + ((start / width) + 1) + ", " + ((start % width) + 1) + ")";
        String finishPosition = "(" + ((finish / width) + 1) + ", " + ((finish % width) + 1) + ")";
        System.out.println("Start Position: " + startPosition);
        System.out.println("Finish Position: " + finishPosition);
        System.out.println();

        int height = map.length;

        int V = graph.getV();
        int[] distances = new int[V];
        int[] prev = new int[V];

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(V, Comparator.comparingInt(i -> distances[i]));

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;
        priorityQueue.add(start);

// todo F found, stop exploring
        while (!priorityQueue.isEmpty()) {
            int currentNode = priorityQueue.poll();

            for (int neighborNode : graph.getNeighbors(currentNode)) {

                int neighborX = neighborNode / width;
                int neighborY = neighborNode % width;

                int[] finalPosition = slideOnIce(neighborX, neighborY, graph.getDx(currentNode, neighborNode), graph.getDy(currentNode, neighborNode), width, height, map, finish);
                int slideStopX = finalPosition[0];
                int slideStopY = finalPosition[1];

                int slideStopNode = slideStopX * width + slideStopY;

                if (slideStopX >= 0 && slideStopX < width && slideStopY >= 0 && slideStopY < height && map[slideStopY][slideStopX] != '0') {

                    int alternatePathDistance = distances[currentNode] + 1; // Assuming edge weight is 1
                    if (alternatePathDistance < distances[slideStopNode]) {
                        distances[slideStopNode] = alternatePathDistance;
                        prev[slideStopNode] = currentNode;
                        priorityQueue.add(slideStopNode);
                    }
                }

            }
        }

        List<String> path = new ArrayList<>();

        int at = finish;
        while (at != start) {
            path.add(getDirection(prev[at], at, width));
            at = prev[at];
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

    private static int[] slideOnIce(int desiredX, int desiredY, int dx, int dy, int width, int height, char[][] map, int finish) {
        int lastValidX = desiredX;  // Track last valid position
        int lastValidY = desiredY;


        while (desiredX >= 0 && desiredX < width && desiredY >= 0 && desiredY < height && map[desiredY][desiredX] != '0') {
            lastValidX = desiredX;  // Update last valid position if space is valid
            lastValidY = desiredY;

//            Retuning the finish position if found during sliding
            if (desiredX * width + desiredY == finish) {

                return new int[]{desiredX, desiredY};
            }

            desiredX += dx;
            desiredY += dy;


        }

        return new int[]{lastValidX, lastValidY};
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

