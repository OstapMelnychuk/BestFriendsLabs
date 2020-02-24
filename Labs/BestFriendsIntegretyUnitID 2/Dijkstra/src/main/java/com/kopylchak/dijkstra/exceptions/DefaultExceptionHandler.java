package com.kopylchak.dijkstra.exceptions;

import com.google.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.apache.log4j.Logger;

@Singleton
public class DefaultExceptionHandler implements ExceptionHandler {
    private static final Logger logger = Logger.getLogger(DefaultExceptionHandler.class);
    private Map<Class<? extends Exception>, Consumer<Exception>> actions;

    public DefaultExceptionHandler() {
        actions = new HashMap<>();
        configure();
    }

    public final void handle(Exception exception) {
        boolean isHandled = false;
        for (Class<? extends Exception> c : actions.keySet()) {
            if (c.isInstance(exception)) {
                actions.get(c).accept(exception);
                isHandled = true;
                break;
            }
        }

        if (!isHandled) {
            logger.error(exception.getMessage());
        }
    }

    protected void configure() {
        bind(Exception.class, t -> logger.error(t.getMessage()));
    }

    protected DefaultExceptionHandler bind(Class<? extends Exception> exception, Consumer<Exception> action) {
        actions.put(exception, action);

        return this;
    }
}