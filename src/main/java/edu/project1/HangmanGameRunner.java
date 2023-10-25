package edu.project1;

import java.util.ArrayList;
import java.util.Scanner;

public class HangmanGameRunner {
    private HangmanGameRunner() {
    }

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ConsoleWriter console = new ConsoleWriter(System.out);
            GameDictionary dictionary = new GameDictionary(new ArrayList<>() { {
                add("godfather");
            } });
            GallowsProvider gallows = new ClassicGallowsProvider();
            HangmanGame gameDriver = new HangmanGame(dictionary, gallows, console, scanner);

            gameDriver.initSession();
            while (gameDriver.startTurn()) {
            }
        }
    }
}
