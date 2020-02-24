package com.kopylchak.dijkstra.graph.io;

import com.google.inject.Singleton;
import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.Vertex;
import java.util.List;

@Singleton
public class ConsoleGraphPrinter implements GraphPrinter {
    private static final String FROM_VERTEX_FORMAT = "%s -> ";
    private static final String VERTEX_CHAIN_NODE_FORMAT = "%s -> ";
    private static final String TO_VERTEX_FORMAT = "%s(%d), ";

    @Override
    public void printGraph(Graph graph) {
        System.out.println("");
        for (Vertex v : graph.getVertexes()) {
            writeVertex(v, graph);
        }
        System.out.println("");
    }

    @Override
    public void printVertexChain(List<Vertex> vertexSet) {
        StringBuilder result = new StringBuilder();

        for (Vertex v : vertexSet) {
            result.append(String.format(VERTEX_CHAIN_NODE_FORMAT, v.getName()));
        }

        result.delete(result.length() - 4, result.length());

        System.out.println(result.toString());
    }

    private void writeVertex(Vertex vertex, Graph graph) {
        StringBuilder line = new StringBuilder(String.format(FROM_VERTEX_FORMAT, vertex.getName()));

        for (Vertex v : vertex.getNeighbors().keySet()) {
            line.append(String.format(TO_VERTEX_FORMAT, v.getName(), graph.getDistanceBetween(vertex, v)));
        }

        line.delete(line.length() - 2, line.length());

        System.out.println((line.toString()));
    }
}
