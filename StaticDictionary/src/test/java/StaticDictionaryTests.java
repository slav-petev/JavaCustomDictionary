import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import dictionary.StaticDictionary;

public class StaticDictionaryTests {
    private StaticDictionary<String, Integer> testDictionary;

    @BeforeEach
    public void setup() {
        this.testDictionary = new StaticDictionary<String, Integer>();
    }

    @Test
    public void sizeShouldBeOneWhenAddingToEmpty() throws Exception {
        this.testDictionary.put("Java", 1);

        Assertions.assertEquals(1, this.testDictionary.size());
    }

    @Test
    public void shouldUpdateValueWhenKeyExists() throws Exception {
        this.testDictionary.put("Java", 1);
        int newValue = 10;
        this.testDictionary.put("Java", newValue);

        Assertions.assertEquals(newValue, this.testDictionary.get("Java"));
    }

    @Test
    public void shouldReturnNullWhenRetrievingNonExistingKey() {
        Assertions.assertNull(this.testDictionary.get("C#"));
    }
}
