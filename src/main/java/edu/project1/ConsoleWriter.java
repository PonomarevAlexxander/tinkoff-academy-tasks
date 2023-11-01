package edu.project1;

import java.io.PrintStream;
import java.util.SortedSet;

public class ConsoleWriter {
    private final PrintStream stream;
    private static final String GREETING_FORMAT = """
        >>====================================================<<
          Hi, there! Hangman game starts...
          You will have to guess the word with limited number
          of faults or a man will be hanged. Good luck!
          You can end the game running '/exit' command.
        >>====================================================<<
        """;
    private static final String ASK_FOR_ANSWER_MESSAGE = """
        >>====================================================<<
          Type one letter, that you want to open in word or
          the whole word to check your predictions
        >>====================================================<<
        """;
    private static final String WORD_FORMAT = """
        >>====================================================<<
          Word is: %1$s
        >>====================================================<<
        """;
    private static final String SUCCESS_MESSAGE = """
        >>====================================================<<
          Your answer is right!
        >>====================================================<<
         """;
    private static final String FAILURE_LETTER_FORMAT = """
        >>====================================================<<
          There are no '%1$c' in the word :(
        >>====================================================<<
        """;
    private static final String FAILURE_WORD_FORMAT = """
        >>====================================================<<
          '%1$s' is not the answer :(
        >>====================================================<<
        """;
    private static final String STATISTICS_FORMAT = """
        >>====================================================<<
          Remaining letters: %1$s
          You have %2$d attempts left.
        >>====================================================<<
        """;
    private static final String WRONG_LETTER_MESSAGE = """
        >>====================================================<<
          You already have named this letter, try to think
          sth new
        >>====================================================<<
        """;
    private static final String WIN_MESSAGE = """
        >>====================================================<<
          You've won!!!
        >>====================================================<<
        """;
    private static final String LOSE_MESSAGE = """
        >>====================================================<<
          Sorry :( You've lost the game
        >>====================================================<<
        """;
    private static final String CONTINUE_MESSAGE = """
        >>====================================================<<
          Do you want to play one more time? Yes/No
        >>====================================================<<
        """;
    private static final String FINAL_MESSAGE = """
        >>====================================================<<
          Game end. See you soon!
        >>====================================================<<
        """;

    public ConsoleWriter(PrintStream stream) {
        this.stream = stream;
    }

    public void printGreeting() {
        stream.print(GREETING_FORMAT);
    }

    public void printAskingMessage() {
        stream.print(ASK_FOR_ANSWER_MESSAGE);
    }

    public void printWrongLetterMessage() {
        stream.print(WRONG_LETTER_MESSAGE);
    }

    public void printWordToGuess(String word) {
        stream.format(WORD_FORMAT, word);
    }

    public void printContinueMessage() {
        stream.print(CONTINUE_MESSAGE);
    }

    public void printFinalMessage() {
        stream.print(FINAL_MESSAGE);
    }

    public void printGallows(String gallows) {
        stream.print(gallows);
    }

    public void printSuccessMessage() {
        stream.print(SUCCESS_MESSAGE);
    }

    public void printFailureMessage(String answer) {
        if (answer.length() > 1) {
            stream.format(FAILURE_WORD_FORMAT, answer);
        } else {
            stream.format(FAILURE_LETTER_FORMAT, answer.charAt(0));
        }
    }

    public void printStatistics(SortedSet<Character> remainingLetters, int attemptsLeft) {
        stream.format(STATISTICS_FORMAT, remainingLetters.toString(), attemptsLeft);
    }

    public void printWinMessage() {
        stream.print(WIN_MESSAGE);
    }

    public void printLoseMessage() {
        stream.print(LOSE_MESSAGE);
    }
}
