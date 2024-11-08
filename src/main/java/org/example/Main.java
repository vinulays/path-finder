/**
 * Student ID: w1871349 / 20212078
 * Name: Vinula Senarathne
 */
package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to path finder!");
        System.out.println("======================================");
        System.out.println();
        System.out.println("Enter the file name:");
        Scanner scanner = new Scanner(System.in);

        try {
            Graph graph = MapParser.parseMap(scanner.nextLine());
            long startTime = System.currentTimeMillis();

            List<String> path = Dijkstra.findShortestPath(graph, graph.getMap(), graph.getStartPosition(), graph.getFinishPosition(), graph.getWidth());
            if (path.isEmpty()) {
                System.out.println("No Path Found!");
            } else {
                for (String instruction : path) {
                    System.out.println(instruction);
                }
            }
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Time to execute: " + elapsedTime + "ms");

        } catch (IOException e) {
            System.err.println("Error parsing map file: " + e.getMessage());

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }
}
