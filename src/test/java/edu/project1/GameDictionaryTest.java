package edu.project1;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GameDictionaryTest {

    @Test
    void testGetNewWordToGetAllWordsFromDictionary() {
        GameDictionary dict = new GameDictionary(new ArrayList<>() {{
            add("first");
            add("second");
            add("third");
            add("fourth");
        }});
        dict.getNewWord();
        dict.getNewWord();
        dict.getNewWord();
        dict.getNewWord();
        assertThat(dict.getNewWord())
            .isNull();
    }
}
