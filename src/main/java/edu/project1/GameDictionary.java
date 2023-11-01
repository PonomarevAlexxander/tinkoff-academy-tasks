package edu.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameDictionary {
    private final List<String> wordsCollection;
    private static final Random RANDOM = new Random();

    public GameDictionary(List<String> wordsCollection) {
        this.wordsCollection = new ArrayList<>(wordsCollection);
    }

    public String getNewWord() {
        if (wordsCollection.isEmpty()) {
            return null;
        }
        int newWordIndex = RANDOM.nextInt(wordsCollection.size());
        String newWord = wordsCollection.get(newWordIndex);
        wordsCollection.remove(newWordIndex);
        return newWord;
    }
}
