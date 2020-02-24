package com.kopylchak.dijkstra.graph.algorithms;

import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.Vertex;
import java.util.List;

public interface Dijkstra {
    List<Vertex> findShortestPath(Graph graph, String from, String to);
}