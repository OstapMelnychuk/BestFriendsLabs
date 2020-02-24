package com.kopylchak.dijkstra.graph.productivity;

import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.algorithms.Dijkstra;
import java.time.Duration;

public interface DijkstraProductivityTest {
    void generateGraphs(long vertexNumber, int graphCount);

    Duration test(Dijkstra dijkstra);

    Duration test(Graph graph, Dijkstra dijkstra);

    Duration testParallel(Dijkstra dijkstra, int threadCount);

    Duration testParallel(Graph graph, Dijkstra dijkstra, int threadCount);
}