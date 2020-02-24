package com.kopylchak.dijkstra.graph.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.GraphImpl;
import com.kopylchak.dijkstra.graph.Vertex;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DijkstraTest {
    private static Graph graph;
    private static Dijkstra dijkstra;

    @BeforeAll
    private static void init() {
        graph = new GraphImpl();
        dijkstra = new DijkstraImpl();

        graph.addVertex(new Vertex("0"));
        graph.addVertex(new Vertex("1"));
        graph.addVertex(new Vertex("2"));
        graph.addVertex(new Vertex("3"));
        graph.addVertex(new Vertex("4"));
        graph.addVertex(new Vertex("5"));
        graph.addVertex(new Vertex("6"));

        graph.addEdge("0", "1", 3, true);
        graph.addEdge("0", "6", 10, true);
        graph.addEdge("1", "5", 11, true);
        graph.addEdge("1", "3", 5, true);
        graph.addEdge("1", "2", 3, true);
        graph.addEdge("2", "5", 3, true);
        graph.addEdge("3", "6", 7, true);
        graph.addEdge("3", "4", 1, true);
        graph.addEdge("4", "5", 7, true);
        graph.addEdge("5", "6", 5, true);
    }

    @ParameterizedTest
    @MethodSource("provideParams")
    public void test(String start, String target, List<Vertex> shortestPath) {
        List<Vertex> expected = Arrays.asList(new Vertex("0"), new Vertex("1"), new Vertex("3"),
            new Vertex("4"));

        assertEquals(expected, dijkstra.findShortestPath(graph, "0", "4"));
    }

    private static Stream<Arguments> provideParams() {
        return Stream.of(
            Arguments.of("0", "4", Arrays.asList(new Vertex("0"), new Vertex("1"), new Vertex("3"),
                new Vertex("4")),
                Arguments.of("0", "5", Arrays.asList(new Vertex("0"), new Vertex("1"), new Vertex("2"),
                    new Vertex("5")),
                    Arguments.of("6", "10", Arrays.asList(new Vertex("6"), new Vertex("0")),
                        Arguments.of("3", "1", Arrays.asList(new Vertex("3"), new Vertex("1")))
                    ))));
    }
}
