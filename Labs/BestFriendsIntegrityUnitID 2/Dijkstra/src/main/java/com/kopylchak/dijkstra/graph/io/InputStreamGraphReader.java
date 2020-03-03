package com.kopylchak.dijkstra.graph.io;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.kopylchak.dijkstra.graph.Graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class InputStreamGraphReader implements GraphReader {
    private GraphParser graphParser;

    @Inject
    public InputStreamGraphReader(GraphParser graphParser) {
        this.graphParser = graphParser;
    }

    @Override
    public Graph readGraph(InputStream inputStream) {
        String row;
        List<String> rows = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((row = bufferedReader.readLine()) != null) {
                rows.add(row);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return graphParser.parseGraph(rows);
    }
}
