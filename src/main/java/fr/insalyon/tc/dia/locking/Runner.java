package fr.insalyon.tc.dia.locking;

import java.util.LinkedList;
import java.util.concurrent.*;

public class Runner {

    private static final int THREAD_COUNT = 4;
    private static final int JOB_COUNT = 5000000;
    private static final int STORE_SIZE = 20;
    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
    private final LinkedList<Callable<Void>> jobs = new LinkedList<>();

    private void bench(final Store store) throws InterruptedException, ExecutionException {
        for (int i = 0; i < JOB_COUNT; i++) {
            final boolean adding = i % 2 == 0;
            jobs.add(() -> {
                for (int j = 0; j < store.size(); j++) {
                    if (adding) {
                        store.add(j, 1);
                    } else {
                        store.substract(j, 1);
                    }
                }
                return null;
            });
        }
        long start = System.currentTimeMillis();
        for (Future<Void> future : executor.invokeAll(jobs)) {
            while (!future.isDone()) {
                // loop!
            }
        }
        long stop = System.currentTimeMillis();
        jobs.clear();
        System.out.println(store.name() + ": " + (stop - start) + "ms");
        for (int i = 0; i < store.size(); i++) {
            System.out.print(store.at(i) + " ");
        }
        System.out.println();
        System.gc();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Runner runner = new Runner();
        for (int i = 0; i < 5; i++) {
            runner.bench(new HippieStore(STORE_SIZE));
        }
        for (int i = 0; i < 5; i++) {
            runner.bench(new PessimisticStore(STORE_SIZE));
        }
        System.exit(0);
    }
}
