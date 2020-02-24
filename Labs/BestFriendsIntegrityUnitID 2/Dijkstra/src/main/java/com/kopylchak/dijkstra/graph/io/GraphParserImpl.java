package com.kopylchak.dijkstra.graph.io;

import com.google.inject.Singleton;
import com.kopylchak.dijkstra.graph.Graph;
import com.kopylchak.dijkstra.graph.GraphImpl;
import com.kopylchak.dijkstra.graph.Vertex;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class GraphParserImpl implements GraphParser {
    private static final String EDGE_REGEX = "\\s*(?<edge>\\w+)\\((?<distance>\\d+)\\)\\s*";
    private static final String FROM_EDGE_REGEX = "\\s*(?<from>\\w+)\\s*->.*";

    @Override
    public Graph parseGraph(List<String> stringsToParse) {
        Graph graph = new GraphImpl();
        Pattern edgePattern = Pattern.compile(EDGE_REGEX);
        Pattern fromEdgePattern = Pattern.compile(FROM_EDGE_REGEX);
        Matcher matcher;
        Matcher fromEdgeMatcher;
        String vertexName;

        for (String row : stringsToParse) {
            matcher = edgePattern.matcher(row);
            fromEdgeMatcher = fromEdgePattern.matcher(row);

            if (fromEdgeMatcher.find()) {
                vertexName = fromEdgeMatcher.group("from");
                graph.addVertex(new Vertex(vertexName));
            } else if (matcher.matches()) {
                graph.addVertex(new Vertex(matcher.group("edge")));
                continue;
            } else {
                throw new RuntimeException();
            }

            if (matcher.find(1)) {
                do {
                    graph.addEdge(vertexName, matcher.group("edge"),
                        Long.parseLong(matcher.group("distance")), false);
                } while (matcher.find());
            }
        }

        return graph;
    }
}