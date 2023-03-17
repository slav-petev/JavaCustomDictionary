package dictionary;

import java.util.Objects;

public class KeyValuePair<TKey, TValue> {
    private TKey key;
    private TValue value;
    private KeyValuePair<TKey, TValue> next;
    public KeyValuePair(TKey key, TValue value, KeyValuePair<TKey, TValue> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }
    public TKey key() {
        return this.key;
    }
    public TValue value() {
        return this.value;
    }
    public void setValue(TValue value) {
        this.value = value;
    }
    public KeyValuePair<TKey, TValue> next() {
        return this.next;
    }
    public void setNext(KeyValuePair<TKey, TValue> next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;

        }

        if (!(o instanceof KeyValuePair)) {
            return false;
        }

        KeyValuePair<TKey, TValue> e = (KeyValuePair<TKey, TValue>) o;
        return Objects.equals(e.key, this.key)
                && Objects.equals(e.value, this.value);
    }

    @Override
    public String toString() {
        return String.format("Entry{key=%s, value=%s, next=%s", this.key, this.value, this.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.value);
    }
}