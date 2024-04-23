/**
 * Student ID: w1871349 / 20212078
 * Name: Vinula Senarathne
 */

package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapParser {

    public static Graph parseMap(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int width, height;
        int startX = -1, startY = -1, finishX = -1, finishY = -1;


        List<String> mapLines = new ArrayList<>();

        int y = 0;
        while ((line = reader.readLine()) != null) {
            mapLines.add(line);

            if (line.contains("S")) {
                startX = line.indexOf("S");
                startY = y;
            }
            if (line.contains("F")) {
                finishX = line.indexOf("F");
                finishY = y;
            }
            y++;
        }

        reader.close();
        width = mapLines.get(0).length();
        height = mapLines.size();

        char[][] map = new char[height][];
        for (int i = 0; i < mapLines.size(); i++) {
            map[i] = mapLines.get(i).toCharArray();

        }

        int startPosition = startX * width + startY;
        int finishPosition = finishX * width + finishY;


//      Creating the graph
        Graph graph = new Graph(height * width, startPosition, finishPosition, width, map);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j > 0) {
                    graph.addEdge(i * width + j, i * width + j - 1); // left
                }
                if (j < width - 1) {
                    graph.addEdge(i * width + j, i * width + j + 1); // right
                }
                if (i > 0) {
                    graph.addEdge(i * width + j, (i - 1) * width + j); // up
                }
                if (i < height - 1) {
                    graph.addEdge(i * width + j, (i + 1) * width + j); // down
                }

            }
        }

        return graph;
    }


}
