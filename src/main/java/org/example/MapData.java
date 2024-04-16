package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MapData {
    private final int width;
    private final int height;
    private final char[][] map;
    private final Graph graph;

    public MapData(String fileName) {
        map = parseMap(fileName);
        if (map == null) {
            System.err.println("Failed to parse map from file.");
            System.exit(1);
        }
        this.width = map[0].length;
        this.height = map.length;
        this.graph = new Graph(width * height);
        buildGraph();
    }

    private char[][] parseMap(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            List<char[]> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().toCharArray());
            }
            char[][] map = new char[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                map[i] = lines.get(i);
            }
            return map;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void buildGraph() {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//                Skipping vertices with 0 (walls).
                if (map[i][j] == '0') continue;
                int from = i * width + j;
//                Checking for adjacent vertices by checking four cardinal directions (up,down,right,left)
                for (int k = 0; k < 4; k++) {
                    int neighborX = i + dx[k];
                    int neighborY = j + dy[k];

                    if (neighborX >= 0 && neighborX < height && neighborY >= 0 && neighborY < width && map[neighborX][neighborY] != '0') {
                        int to = neighborX * width + neighborY;
                        graph.addEdge(from, to);
                    }
                }
            }
        }
    }

    public List<String> findShortestPath() {
        int start = -1, finish = -1;

//        finding the start and finish positions
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'S') {
                    start = i * width + j;
                } else if (map[i][j] == 'F') {
                    finish = i * width + j;

                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> parent = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == finish) {
                return constructPath(parent, finish);
            }

            List<Integer> neighbors = graph.getNeighbors(current);

            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    if(map[neighbor / width][neighbor % width] == 'F'){
                        System.out.println("F found!");
                    }

                    if (map[neighbor / width][neighbor % width] == '.') {
                        // Slide in the same direction until hitting a wall or a rock
                        int dx = (neighbor / width) - (current / width);
                        int dy = (neighbor % width) - (current % width);

                        int nextX = neighbor / width;
                        int nextY = neighbor % width;


                        while (nextX >= 0 && nextX < height && nextY >= 0 && nextY < width && map[nextX][nextY] == '.') {
                            visited.add(nextX * width + nextY);

                            nextX += dx;
                            nextY += dy;
                        }

                        int slidingCell = (nextX - dx) * width + (nextY - dy);

                        if (slidingCell != current) {
                            queue.offer(slidingCell);
                            visited.add(slidingCell);
                            parent.put(slidingCell, current);
                        }

                    } else {
                        // Normal BFS traversal for non-ice cells
                        queue.offer(neighbor);
                        visited.add(neighbor);
                        parent.put(neighbor, current);
                    }
                }
            }
        }
        return null;//        return null; // No path found
    }



     private List<String> constructPath(Map<Integer, Integer> parent, int finish) {
         System.out.println(parent.size());

        List<String> path = new ArrayList<>();
        int current = finish;

        while (parent.containsKey(current)) {
            int currentX = current % width;
            int currentY = current / width;

            int parentX = parent.get(current) % width;
            int parentY = parent.get(current) / width;

            int diffX = currentX - parentX;
            int diffY = currentY - parentY;

            String direction;
            if (diffX > 0) {
                direction = "right";
            } else if (diffX < 0) {
                direction = "left";
            } else if (diffY > 0) {
                direction = "down";
            } else if (diffY < 0) {
                direction = "up";
            } else {
                direction = "unknown";
            }

            path.add("Move " + direction + " to (" + (currentX + 1) + ", " + (currentY + 1) + ")");
            current = parent.get(current);
        }

        int x = current % width;
        int y = current / width;
        path.add("Start at (" + (x + 1) + ", " + (y + 1) + ")");

        Collections.reverse(path);
        path.add("Done!");

        List<String> numberedPath = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            numberedPath.add((i + 1) + ". " + path.get(i));
        }

        return numberedPath;
    }


}
