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
    void pilotTest() {
        Assertions.assertNotEquals(null, this.testDictionary, "");
    }
}
