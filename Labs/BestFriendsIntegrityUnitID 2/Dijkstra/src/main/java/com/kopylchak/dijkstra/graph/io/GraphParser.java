package com.kopylchak.dijkstra.graph.io;

import com.kopylchak.dijkstra.graph.Graph;
import java.util.List;

/**
 * Implementations of this interface are used for parsing {@link Graph} from given {@link String}.
 */
public interface GraphParser {
    /**
     * Method, that parses each given string and build {@link Graph}.
     *
     * @param stringsToParse {@link List} of {@link String} to parse.
     * @return {@link Graph} parsed from stringsToParse param.
     */
    Graph parseGraph(List<String> stringsToParse);
}