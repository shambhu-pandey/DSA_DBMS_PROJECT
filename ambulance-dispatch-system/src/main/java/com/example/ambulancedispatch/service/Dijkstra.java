package com.example.ambulancedispatch.service;

import java.util.*;

public class Dijkstra {
    private Graph graph;

    public Dijkstra(Graph graph) {
        this.graph = graph;
    }

    public static class Result {
        public int distance;
        public List<String> path;

        public Result(int distance, List<String> path) {
            this.distance = distance;
            this.path = path;
        }
    }

    public Result shortestPath(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Initialize distances
        for (String vertex : graph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            int currentDistance = distances.get(current);

            if (currentDistance == Integer.MAX_VALUE) break;

            for (Graph.Edge edge : graph.getNeighbors(current)) {
                int newDistance = currentDistance + edge.weight;
                if (newDistance < distances.get(edge.destination)) {
                    distances.put(edge.destination, newDistance);
                    previous.put(edge.destination, current);
                    queue.add(edge.destination);
                }
            }
        }

        // Reconstruct path
        List<String> path = new ArrayList<>();
        String current = end;
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }

        if (path.size() == 1 && !start.equals(end)) {
            // No path found
            return new Result(Integer.MAX_VALUE, new ArrayList<>());
        }

        return new Result(distances.get(end), path);
    }

    // Calculate distance between two locations using the graph
    public int calculateDistance(String from, String to) {
        if (from.equals(to)) return 0;

        Result result = shortestPath(from, to);
        return result.distance == Integer.MAX_VALUE ? -1 : result.distance;
    }

    // Get the shortest path as a list of locations
    public List<String> getShortestPath(String from, String to) {
        if (from.equals(to)) return Arrays.asList(from);

        Result result = shortestPath(from, to);
        return result.path.isEmpty() ? new ArrayList<>() : result.path;
    }
}
