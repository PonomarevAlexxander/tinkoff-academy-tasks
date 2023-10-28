package edu.project1;

import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public final class HangmanGame {
    public enum GameState {
        WIN,
        LOSE,
        WRONG_INPUT,
        SUCCESS,
        FAILURE,
        EXIT,
        CONTINUE
    }

    private static final String EXIT = "/exit";

    private final GameDictionary dictionary;
    private final GallowsProvider gallows;
    private final ConsoleWriter consoleWriter;
    private final Scanner scanner;

    private SortedSet<Character> remainingLetters;
    private String currentWord;
    private StringBuilder maskedWord;

    public HangmanGame(
        GameDictionary dictionary,
        GallowsProvider gallows,
        ConsoleWriter consoleWriter,
        Scanner scanner
    ) {
        this.dictionary = dictionary;
        this.gallows = gallows;
        this.consoleWriter = consoleWriter;
        this.scanner = scanner;
        updateLetters();
    }

    public void initSession() {
        consoleWriter.printGreeting();
    }

    public GameState startTurn() {
        currentWord = dictionary.getNewWord();
        if (currentWord == null) {
            consoleWriter.printFinalMessage();
            return GameState.EXIT;
        }
        maskedWord = new StringBuilder(currentWord.replaceAll(".", "."));
        consoleWriter.printWordToGuess(maskedWord.toString());
        gallows.destroyGallows();
        updateLetters();

        GameState turnState = runTurn();
        switch (turnState) {
            case EXIT -> {
                consoleWriter.printFinalMessage();
                return turnState;
            }
            case LOSE -> {
                consoleWriter.printLoseMessage();
                consoleWriter.printWordToGuess(currentWord);
            }
            case WIN -> consoleWriter.printWinMessage();
            default -> {
            }
        }
        return exitGame();
    }

    private GameState runTurn() {
        while (gallows.getLeftAttempts() > 0) {
            String answer = getNextAnswer();
            GameState tryState = tryToAnswer(answer);
            switch (tryState) {
                case WIN -> {
                    return GameState.WIN;
                }
                case SUCCESS -> {
                    consoleWriter.printSuccessMessage();
                    if (tryToGuessWord(maskedWord.toString()) == GameState.WIN) {
                        return GameState.WIN;
                    }
                }
                case FAILURE -> {
                    consoleWriter.printFailureMessage(answer);
                    gallows.buildNextElement();
                }
                case WRONG_INPUT -> {
                    continue;
                }
                case EXIT -> {
                    return tryState;
                }
                default -> {
                }
            }

            consoleWriter.printStatistics(remainingLetters, gallows.getLeftAttempts());
            consoleWriter.printWordToGuess(maskedWord.toString());
            String gallowsString = gallows.getGallows();
            if (gallowsString != null) {
                consoleWriter.printGallows(gallowsString);
            }
        }
        return GameState.LOSE;
    }

    private String getNextAnswer() {
        consoleWriter.printAskingMessage();
        return scanner.nextLine();
    }

    private GameState tryToAnswer(String answer) {
        if (answer.equals(EXIT)) {
            return GameState.EXIT;
        }
        if (answer.isBlank()) {
            return GameState.WRONG_INPUT;
        }
        if (answer.length() > 1) {
            return tryToGuessWord(answer);
        }
        return tryToOpenLetter(answer.charAt(0));
    }

    private GameState tryToGuessWord(String answer) {
        if (answer.equals(currentWord)) {
            return GameState.WIN;
        }
        return GameState.FAILURE;
    }

    private GameState exitGame() {
        consoleWriter.printContinueMessage();
        String continueGame = scanner.nextLine();
        if (continueGame.equals("Yes")) {
            return GameState.CONTINUE;
        }
        consoleWriter.printFinalMessage();
        return GameState.EXIT;
    }

    private GameState tryToOpenLetter(char letter) {
        if (remainingLetters.contains(letter)) {
            GameState state = GameState.FAILURE;
            for (int index = 0; index < currentWord.length(); index++) {
                if (currentWord.charAt(index) == letter) {
                    maskedWord.setCharAt(index, letter);
                    state = GameState.SUCCESS;
                }
            }
            remainingLetters.remove(letter);
            return state;
        }
        consoleWriter.printWrongLetterMessage();
        return GameState.WRONG_INPUT;
    }

    private void updateLetters() {
        remainingLetters = new TreeSet<>();
        for (char letter = 'a'; letter < 'z'; letter++) {
            remainingLetters.add(letter);
        }
    }
}
