package com.kopylchak.dijkstra.graph.algorithms;

import com.google.inject.Singleton;
import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.Vertex;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class DijkstraImpl implements Dijkstra {
    private static final long INFINITY_NUMBER = -1;

    @Override
    public List<Vertex> findShortestPath(Graph graph, String from, String to) {
        Set<Vertex> visited = new HashSet<>();
        Vertex current = Objects.requireNonNull(graph.getVertex(from), "No such vertex exists " + from);
        Vertex target = Objects.requireNonNull(graph.getVertex(to), "No such vertex exists " + from);
        Map<Vertex, Long> marks = getInitMarks(graph, current);

        markVertexes(graph, current, marks, visited);
        while ((current = findNextVertex(current, marks, visited)) != null) {
            markVertexes(graph, current, marks, visited);
        }

        return buildShortestPath(graph,
            Objects.requireNonNull(graph.getVertex(from), "No such vertex exists " + from),
            target, marks);
    }

    protected void markVertexes(Graph graph, Vertex current, Map<Vertex, Long> marks, Set<Vertex> visited) {
        long curVertexMark = marks.get(current);
        Set<Vertex> nonVisitedNeighbours = current.getNeighbors()
            .keySet()
            .stream()
            .filter(v -> !visited.contains(v))
            .collect(Collectors.toSet());

        for (Vertex v : nonVisitedNeighbours) {
            long curMark = marks.get(v);

            if (curMark == -1 || curMark > curVertexMark + graph.getDistanceBetween(current, v)) {
                marks.put(v, curVertexMark + graph.getDistanceBetween(current, v));
            }
        }

        visited.add(current);
    }

    protected Vertex findNextVertex(Vertex current, Map<Vertex, Long> marks, Set<Vertex> visited) {
        return current.getNeighbors()
            .keySet()
            .stream()
            .filter(v -> !visited.contains(v))
            .min(Comparator.comparing(marks::get))
            .orElse(null);
    }

    private Map<Vertex, Long> getInitMarks(Graph graph, Vertex startVertex) {
        Map<Vertex, Long> marks = new HashMap<>();

        graph.getVertexes()
            .forEach(v -> marks.put(v, INFINITY_NUMBER));

        marks.put(startVertex, 0L);

        return marks;
    }

    protected List<Vertex> buildShortestPath(Graph graph, Vertex start, Vertex target, Map<Vertex, Long> marks) {
        List<Vertex> shortestPath = new ArrayList<>(Collections.singleton(target));
        Vertex current = target;

        while (!current.equals(start)) {
            long currentMark = marks.get(current);
            Vertex finalCurrent = current;

            current = current.getNeighbors()
                .keySet()
                .stream()
                .filter(v -> currentMark - graph.getDistanceBetween(v, finalCurrent) == marks.get(v))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no path from " + start.getName() + " to " +
                    target.getName()));

            shortestPath.add(current);
        }
        Collections.reverse(shortestPath);

        return shortestPath;
    }
}