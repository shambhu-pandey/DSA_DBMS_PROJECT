package com.example.ambulancedispatch.service;

import java.util.*;

public class Graph {
    private Map<String, List<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public static class Edge {
        String destination;
        int weight;

        public Edge(String destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    public void addVertex(String vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(String source, String destination, int weight) {
        addVertex(source);
        addVertex(destination);
        adjacencyList.get(source).add(new Edge(destination, weight));
        adjacencyList.get(destination).add(new Edge(source, weight)); // Undirected graph
    }

    public List<Edge> getNeighbors(String vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    public Set<String> getVertices() {
        return adjacencyList.keySet();
    }

    // Initialize with sample city locations
    public void initializeSampleGraph() {
        // Sample locations: Sector 1 to Sector 25
        for (int i = 1; i <= 25; i++) {
            addVertex("Sector " + i);
        }

        // Add some sample edges with weights (distances in km)
        addEdge("Sector 1", "Sector 2", 2);
        addEdge("Sector 2", "Sector 3", 3);
        addEdge("Sector 3", "Sector 4", 2);
        addEdge("Sector 4", "Sector 5", 4);
        addEdge("Sector 5", "Sector 10", 5);
        addEdge("Sector 10", "Sector 14", 3);
        addEdge("Sector 14", "Sector 18", 4);
        addEdge("Sector 18", "Sector 20", 2);
        addEdge("Sector 20", "Sector 25", 5);
        addEdge("Sector 1", "Sector 6", 6);
        addEdge("Sector 6", "Sector 11", 3);
        addEdge("Sector 11", "Sector 15", 4);
        addEdge("Sector 15", "Sector 19", 2);
        addEdge("Sector 19", "Sector 23", 3);
        addEdge("Sector 2", "Sector 7", 4);
        addEdge("Sector 7", "Sector 12", 5);
        addEdge("Sector 12", "Sector 16", 3);
        addEdge("Sector 16", "Sector 20", 4);
        addEdge("Sector 3", "Sector 8", 3);
        addEdge("Sector 8", "Sector 13", 4);
        addEdge("Sector 13", "Sector 17", 2);
        addEdge("Sector 17", "Sector 21", 5);
        addEdge("Sector 4", "Sector 9", 6);
        addEdge("Sector 9", "Sector 14", 3);
        addEdge("Sector 14", "Sector 18", 4);
        addEdge("Sector 18", "Sector 22", 2);
        addEdge("Sector 22", "Sector 25", 4);
    }
}
