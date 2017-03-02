package fr.insalyon.tc.dia.locking;

/**
 * Represent a storage manipulation for data.
 */
public interface Store {
    String name();
    int size();
    long at(int index);
    void add(int index, long amount);
    void substract(int index, long amount);
}
