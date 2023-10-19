package edu.project1;

import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public final class HangmanGame {
    private final GameDictionary dictionary;
    private final GallowsProvider gallows;
    private SortedSet<Character> remainingLetters;
    private final ConsoleWriter consoleWriter;
    private String currentWord;
    private StringBuffer maskedWord;

    private final Scanner scanner;

    private static final String EXIT = "/exit";

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

    @SuppressWarnings("ReturnCount")
    public boolean startTurn() {
        currentWord = dictionary.getNewWord();
        if (currentWord == null) {
            consoleWriter.printFinalMessage();
            return false;
        }
        maskedWord = new StringBuffer(currentWord.replaceAll(".", "."));
        consoleWriter.printWordToGuess(maskedWord.toString());
        gallows.destroyGallows();
        updateLetters();

        while (gallows.getLeftAttempts() > 0) {
            consoleWriter.printAskingMessage();
            String answer = scanner.nextLine();
            if (answer.equals(EXIT)) {
                consoleWriter.printFinalMessage();
                return false;
            }
            if (answer.isBlank()) {
                continue;
            }

            Boolean success;
            if (answer.length() > 1) {
                success = tryToGuessWord(answer);
                if (success) {
                    return exitGame();
                }
            } else {
                success = tryToOpenLetter(answer.charAt(0));
            }
            if (Boolean.TRUE.equals(success)) {
                consoleWriter.printSuccessMessage();
                if (tryToGuessWord(maskedWord.toString())) {
                    return exitGame();
                }
            } else if (Boolean.FALSE.equals(success)) {
                consoleWriter.printFailureMessage(answer);
                gallows.buildNextElement();
            }
            consoleWriter.printStatistics(remainingLetters, gallows.getLeftAttempts());
            consoleWriter.printWordToGuess(maskedWord.toString());
            String gallowsString = gallows.getGallows();
            if (gallowsString != null) {
                consoleWriter.printGallows(gallowsString);
            }
        }
        consoleWriter.printLoseMessage();
        consoleWriter.printWordToGuess(currentWord);
        return exitGame();
    }

    private Boolean tryToGuessWord(String answer) {
        if (answer.equals(currentWord)) {
            consoleWriter.printWinMessage();
            return true;
        }
        return false;
    }

    private Boolean exitGame() {
        consoleWriter.printContinueMessage();
        String continueGame = scanner.nextLine();
        if (continueGame.equals("Yes")) {
            return true;
        }
        consoleWriter.printFinalMessage();
        return false;
    }

    private Boolean tryToOpenLetter(char letter) {
        if (remainingLetters.contains(letter)) {
            boolean success = false;
            for (int index = 0; index < currentWord.length(); index++) {
                if (currentWord.charAt(index) == letter) {
                    maskedWord.setCharAt(index, letter);
                    success = true;
                }
            }
            remainingLetters.remove(letter);
            return success;
        }
        consoleWriter.printWrongLetterMessage();
        return null;
    }

    private void updateLetters() {
        remainingLetters = new TreeSet<>();
        for (char letter = 'a'; letter < 'z'; letter++) {
            remainingLetters.add(letter);
        }
    }
}
