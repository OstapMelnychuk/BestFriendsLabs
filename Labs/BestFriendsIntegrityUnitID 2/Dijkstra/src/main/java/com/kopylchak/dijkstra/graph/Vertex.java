package com.kopylchak.dijkstra.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Vertex {
    private String name;
    private Map<Vertex, Long> neighbors;

    public Vertex(String name) {
        this.name = name;
        this.neighbors = new HashMap<>();
    }

    public void addNeighbor(Vertex neighbor, long distance) {
        neighbors.put(neighbor, distance);
    }

    public Map<Vertex, Long> getNeighbors() {
        return neighbors;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vertex vertex = (Vertex) o;
        return name.equals(vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Vertex{" +
            "name='" + name + '\'' +
            '}';
    }
}
