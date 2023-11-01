package edu.project1;

    import java.util.List;
import java.util.Scanner;

public class HangmanGameRunner {
    private HangmanGameRunner() {
    }

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ConsoleWriter console = new ConsoleWriter(System.out);
            GameDictionary dictionary = new GameDictionary(List.of("godfather"));
            GallowsProvider gallows = new ClassicGallowsProvider();
            HangmanGame gameDriver = new HangmanGame(dictionary, gallows, console, scanner);

            gameDriver.initSession();
            HangmanGame.GameState state = HangmanGame.GameState.CONTINUE;
            while (state == HangmanGame.GameState.CONTINUE) {
                state = gameDriver.startTurn();
            }
        }
    }
}
