package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to path finder!");
        System.out.println("======================================");
        System.out.println();
        System.out.println("Enter the file name:");
        Scanner scanner = new Scanner(System.in);
        MapData mapData = new MapData(scanner.nextLine());

        List<String> path = mapData.findShortestPath();
        if (path != null) {
            System.out.println("Shortest path:");
            for (String point : path) {
                System.out.println(point);
            }
        } else {
            System.out.println("No path found!");
        }
    }
}
