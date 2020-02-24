package com.kopylchak.dijkstra.graph.io;

import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.Vertex;
import java.util.List;

public interface GraphPrinter {
    void printGraph(Graph graph);

    void printVertexChain(List<Vertex> vertexSet);
}
