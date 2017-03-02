package fr.insalyon.tc.dia.locking;

/**
 * Concurrency care-less storage.
 */
class PessimisticStore implements Store {

    private final int size;
    private final long[] values;

    PessimisticStore(int size) {
        this.size = size;
        values = new long[size];
    }

    @Override
    public String name() {
        return "Pessimistic";
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public long at(int index) {
        return values[index];
    }

    @Override
    public synchronized void add(int index, long amount) {
        values[index] = values[index] + amount;
    }

    @Override
    public synchronized void substract(int index, long amount) {
        values[index] = values[index] - amount;
    }
}
