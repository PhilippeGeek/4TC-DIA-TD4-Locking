package fr.insalyon.tc.dia.locking;

/**
 * Concurrency care-less storage.
 */
class HippieStore implements Store {

    private final int size;
    private final long[] values;

    HippieStore(int size) {
        this.size = size;
        values = new long[size];
    }

    @Override
    public String name() {
        return "Hippie";
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
    public void add(int index, long amount) {
        values[index] = values[index] + amount;
    }

    @Override
    public void substract(int index, long amount) {
        values[index] = values[index] - amount;
    }
}
