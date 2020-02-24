package com.kopylchak.dijkstra.graph.algorithms;

import com.google.inject.Singleton;
import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.Vertex;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class ParallelDijkstra extends DijkstraImpl {
    @Override
    protected void markVertexes(Graph graph, Vertex current, Map<Vertex, Long> marks, Set<Vertex> visited) {
        long curVertexMark = marks.get(current);
        Set<Vertex> nonVisitedNeighbours = current.getNeighbors()
            .keySet()
            .parallelStream()
            .filter(v -> !visited.contains(v))
            .collect(Collectors.toSet());

        nonVisitedNeighbours.parallelStream()
            .forEach(v -> {
                long curMark = marks.get(v);

                if (curMark == -1 || curMark > curVertexMark + graph.getDistanceBetween(current, v)) {
                    marks.put(v, curVertexMark + graph.getDistanceBetween(current, v));
                }
            });

        visited.add(current);
    }

    @Override
    protected Vertex findNextVertex(Vertex current, Map<Vertex, Long> marks, Set<Vertex> visited) {
        return current.getNeighbors()
            .keySet()
            .parallelStream()
            .filter(v -> !visited.contains(v))
            .min(Comparator.comparing(marks::get))
            .orElse(null);
    }

    @Override
    protected List<Vertex> buildShortestPath(Graph graph, Vertex start, Vertex target, Map<Vertex, Long> marks) {
        List<Vertex> shortestPath = new ArrayList<>(Collections.singleton(target));
        Vertex current = target;

        while (!current.equals(start)) {
            long currentMark = marks.get(current);
            Vertex finalCurrent = current;

            current = current.getNeighbors()
                .keySet()
                .parallelStream()
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