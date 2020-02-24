package com.kopylchak.dijkstra.graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GraphImpl implements Graph {
    private Set<Vertex> graph;

    public GraphImpl() {
        graph = new HashSet<>();
    }

    @Override
    public void addVertex(Vertex vertex) {
        graph.add(vertex);
    }

    @Override
    public void addEdge(final String from, final String to, long distance, boolean bidirectional) {
        Vertex fromVertex = getVertex(from);
        Vertex toVertex = getVertex(to);

        if (fromVertex != null) {
            if (toVertex == null) {
                toVertex = new Vertex(to);

                addVertex(toVertex);
            }

            fromVertex.addNeighbor(toVertex, distance);

            if (bidirectional) {
                toVertex.addNeighbor(fromVertex, distance);
            }
        }
    }

    @Override
    public Set<Vertex> getVertexes() {
        return graph;
    }

    @Override
    public Vertex getVertex(final String name) {
        return graph.stream()
            .filter(v -> v.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    @Override
    public long getDistanceBetween(Vertex v1, Vertex v2) {
        return Objects.requireNonNull(v1.getNeighbors().get(v2), "There is no way from " + v1 + " to " + v2);
    }

    @Override
    public Set<Vertex> getVertexes(Vertex target) {
        return graph.stream()
            .filter(v -> v.getNeighbors().containsKey(target))
            .collect(Collectors.toSet());
    }
}