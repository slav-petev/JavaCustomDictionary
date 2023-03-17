package dictionary;

import java.util.Objects;

public class StaticDictionary<TKey, TValue> {
    private static final int initialCapacity = 1 << 4;

    private final KeyValuePair<TKey, TValue>[] entries;
    private int size = 0;
    private final double loadFactor = 0.75;
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

    public TValue delete(TKey key) throws Exception {
        int position = this.calculateHash(key);
        KeyValuePair<TKey, TValue> entry = this.entries[position];
        if(entry == null)
            return null;

        if (Objects.equals(entry.key(), key)) {
            // if deleted node as nodes on it, lets give them a chance to re insert it back
            if(entry.next() != null) {
                KeyValuePair<TKey,TValue> tmp = entry.next();
                while(tmp != null) {
                    this.put(tmp.key(), tmp.value());
                    tmp = tmp.next();
                }
            }
            // mark current bukcet pos null as head is deleted now
            entries[position] = null;
            size--;
            return entry.value();
        }
        // collision state, so we need find and delete and reattach nodes
        KeyValuePair<TKey, TValue> head = entry.next();
        KeyValuePair<TKey, TValue> parent = entry;
        // 1 [2,3,4]
        while (head != null) {
            if (Objects.equals(head.key(), key)) {
                // re attach nodes
                parent.setNext(head.next());
                this.size--;
                return head.value();
            }
            parent = head;
            head = head.next();
        }

        return null;
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