package com.company;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadCreator {
    private ArrayList<Equation> equations;

    public ThreadCreator(ArrayList<Equation> equations) {
        this.equations = equations;
    }

    public boolean createAndRunThreads(int threadQuantity) {
        ExecutorService service = Executors.newFixedThreadPool(threadQuantity);
        List<Callable<Boolean>> countingThreads = new ArrayList<>();
        List<Future<Boolean>> futures;
        countingThreads.add(new CountingThreads(equations, equations.subList(0, equations.size() / threadQuantity)));
        for (int i = 1; i < threadQuantity; i++) {
            countingThreads.add(new CountingThreads(equations, equations.subList((equations.size() / 8) * i, (equations.size() / 8) * (i + 1))));
        }
        try {
            Instant startThread = Instant.now();
            futures = service.invokeAll(countingThreads);
            Instant endThread = Instant.now();
            Duration intervalThread = Duration.between(startThread, endThread);
            System.out.println("Execution time with " + threadQuantity + " threads is: " + intervalThread.getSeconds() + " seconds.");
            return check(futures);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
        return false;
    }

    private Boolean check(List<Future<Boolean>> futures) throws ExecutionException, InterruptedException {
        for (Future<Boolean> result : futures) {
            if (result.get().equals(true)) {
                return true;
            }
        }
        return false;
    }
}
