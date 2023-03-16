package dictionary;

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
}