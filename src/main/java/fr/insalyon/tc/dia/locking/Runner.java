package fr.insalyon.tc.dia.locking;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.*;

public class Runner {

    private int threadCount = 4;
    private int jobsCount = 5000000;
    private int storeSize = 20;
    private ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    private LinkedList<Callable<Void>> jobs = new LinkedList<>();
    private HashMap<String, Long> execTime = new HashMap<>(3);

    public Runner(int threads, int jobs, int size){
        this.threadCount = threads;
        this.jobsCount = jobs;
        this.storeSize = size;
        this.executor  = Executors.newFixedThreadPool(threadCount);
        this.jobs = new LinkedList<>();
    }

    public HashMap<String, Long> getExecutionTimes(){
        return execTime;
    }

    public void run(){
        try {
            for (int i = 0; i < 5; i++) {
                bench(new HippieStore(storeSize));
            }
            for (int i = 0; i < 5; i++) {
                bench(new PessimisticStore(storeSize));
            }
            for (int i = 0; i < 5; i++) {
                bench(new OptimisticStore(storeSize));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void bench(final Store store) throws InterruptedException, ExecutionException {
        for (int i = 0; i < jobsCount; i++) {
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

        execTime.put(store.name(), stop-start);

        System.gc();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Runner(4,5000000, 20).run();
        System.exit(0);
    }
}
