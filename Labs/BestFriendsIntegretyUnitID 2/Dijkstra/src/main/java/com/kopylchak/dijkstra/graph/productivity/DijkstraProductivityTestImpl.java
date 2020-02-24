package com.kopylchak.dijkstra.graph.productivity;

import com.google.inject.Inject;
import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.algorithms.Dijkstra;
import com.kopylchak.dijkstra.graph.io.GraphGenerator;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.javatuples.Pair;

public class DijkstraProductivityTestImpl implements DijkstraProductivityTest {
    private GraphGenerator graphGenerator;
    private List<Graph> graphs;
    private Map<Graph, Pair<String, String>> fromToMap;

    private static final long AWAIT_TERMINATION_TIME_IN_SECONDS = 120;

    @Inject
    public DijkstraProductivityTestImpl(GraphGenerator graphGenerator) {
        this.graphGenerator = graphGenerator;
    }

    @Override
    public void generateGraphs(long vertexNumber, int graphCount) {
        Graph graph;
        graphs = new ArrayList<>(graphCount);
        fromToMap = new HashMap<>();

        for (int i = 0; i < graphCount; i++) {
            graph = graphGenerator.generate(vertexNumber);
            graphs.add(graph);

            fromToMap.put(graph, new Pair<>(graphGenerator.randomVertex(graph), graphGenerator.randomVertex(graph)));
        }
    }

    @Override
    public Duration test(Dijkstra dijkstra) {
        Instant start = Instant.now();

        for (Graph g : graphs) {
            dijkstra.findShortestPath(g, fromToMap.get(g).getValue0(), fromToMap.get(g).getValue1());
        }

        return Duration.between(start, Instant.now());
    }

    @Override
    public Duration test(Graph graph, Dijkstra dijkstra) {
        fromToMap = new HashMap<>();

        graphs = Collections.singletonList(graph);
        fromToMap.put(graph, new Pair<>(graphGenerator.randomVertex(graph), graphGenerator.randomVertex(graph)));

        return test(dijkstra);
    }

    @Override
    public Duration testParallel(Dijkstra dijkstra, int threadCount) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        Instant start = Instant.now();

        graphs.forEach(g -> executorService.execute(
            () -> dijkstra.findShortestPath(g, fromToMap.get(g).getValue0(), fromToMap.get(g).getValue1())));

        executorService.shutdown();

        try {
            executorService.awaitTermination(AWAIT_TERMINATION_TIME_IN_SECONDS, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Duration.between(start, Instant.now());
    }

    @Override
    public Duration testParallel(Graph graph, Dijkstra dijkstra, int threadCount) {
        fromToMap = new HashMap<>();

        graphs = Collections.singletonList(graph);
        fromToMap.put(graph, new Pair<>(graphGenerator.randomVertex(graph), graphGenerator.randomVertex(graph)));

        return testParallel(dijkstra, threadCount);
    }
}
