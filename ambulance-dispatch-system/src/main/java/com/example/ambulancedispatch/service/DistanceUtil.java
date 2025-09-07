package com.example.ambulancedispatch.service;

import com.example.ambulancedispatch.model.Ambulance;

public class DistanceUtil {
    private static Graph graph = new Graph();
    private static Dijkstra dijkstra;

    static {
        graph.initializeSampleGraph();
        dijkstra = new Dijkstra(graph);
    }

    public static int calculateDistance(String fromLocation, String toLocation) {
        // Try to use Dijkstra for accurate distance calculation
        int distance = dijkstra.calculateDistance(fromLocation, toLocation);
        if (distance != -1) {
            return distance;
        }

        // Fallback to simple calculation if locations not in graph
        try {
            int from = Integer.parseInt(fromLocation.replaceAll("[^0-9]", ""));
            int to = Integer.parseInt(toLocation.replaceAll("[^0-9]", ""));
            return Math.abs(from - to) * 2; // Rough estimate
        } catch (NumberFormatException e) {
            return 10; // Default fallback distance
        }
    }

    public static Graph getGraph() {
        return graph;
    }

    public static Dijkstra getDijkstra() {
        return dijkstra;
    }
}
