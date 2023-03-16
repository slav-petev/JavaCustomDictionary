package dictionary;

import java.util.Objects;

public class StaticDictionary<TKey, TValue> {
    private static final int initialCapacity = 1 << 4;

    private KeyValuePair<TKey, TValue>[] entries;
    private int size = 0;
    private double loadFactor = 0.75;
    private int capacity = initialCapacity;

    public StaticDictionary() {
        this(initialCapacity);
    }

    public StaticDictionary(int size) {
        this.capacity= size;
        this.entries = new KeyValuePair[size];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public KeyValuePair<TKey, TValue>[] entries() {
        return this.entries;
    }

    public boolean containsKey(TKey key) {
        return this.get(key) != null;
    }

    public TValue get (TKey key) {
        if (this.entries.length == 0) {
            return null;
        }

        if (key == null) {
            return this.entries[0].value();
        }

        int bucketIndex = this.calculateHash(key);
        KeyValuePair<TKey, TValue> entry = this.entries[bucketIndex];
        if (entry == null) {
            return null;
        }

        while (entry.next() != null && !Objects.equals(entry.key(), key)) {
            entry = entry.next();
        }

        if (Objects.equals(entry.key(), key)) {
            return entry.value();
        }

        return null;
    }

    public TValue put(TKey key, TValue value) throws Exception {
        if (this.size == this.capacity) {
            throw new Exception("Dictionary is already full!");
        }

        KeyValuePair<TKey, TValue> newEntry = new KeyValuePair<>(key, value, null);
        if (key == null) {
            KeyValuePair<TKey, TValue> nullEntry = this.entries[0];
            if (nullEntry != null) {
                newEntry.setNext(nullEntry);
                this.entries[0] = newEntry;
            } else {
                this.entries[0] = newEntry;
                this.size++;
            }

            return value;
        }

        int bucketPosition = this.calculateHash(key);
        if (this.putInternal(newEntry, this.entries, bucketPosition) != null) {
            this.size++;

            return value;
        }

        return value;
    }

    private int calculateHash(TKey key) {
        return Objects.hash(key) % this.entries.length;
    }

    private TValue putInternal(KeyValuePair<TKey, TValue> entry, KeyValuePair<TKey, TValue>[] entries,
                               int position) {
        KeyValuePair<TKey, TValue> existing = this.entries[position];
        if (existing != null) {
            if (Objects.equals(existing.key(), entry.key())) {
                existing.setValue(entry.value());
            } else {
                System.out.printf("Found collision for put for key:: %s Value::%s", entry.key(), entry.value());
                KeyValuePair<TKey, TValue> tmp = existing.next();
                entry.setNext(tmp);
                existing.setNext(entry);
            }
        } else {
            this.entries[position] = entry;
        }

        return entry.value();
    }
}