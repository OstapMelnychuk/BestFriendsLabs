package com.kopylchak.dijkstra.graph.io;

import com.kopylchak.dijkstra.graph.Graph;

public interface GraphGenerator {
    Graph generate(long vertexNumber);

    String randomVertex(Graph graph);
}
