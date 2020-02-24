package com.kopylchak.dijkstra.graph;

import java.util.Set;

public interface Graph {
    void addVertex(Vertex vertex);

    void addEdge(String from, String to, long distance, boolean bidirectional);

    Vertex getVertex(String name);

    long getDistanceBetween(Vertex v1, Vertex v2);

    Set<Vertex> getVertexes();

    Set<Vertex> getVertexes(Vertex target);
}
