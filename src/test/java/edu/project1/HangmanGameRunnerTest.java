package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HangmanGameRunnerTest {

    @Test
    @DisplayName("Test HangmanGameRunner with valid word answers")
    void testHangmanGameRunnerWithValidWordAnswers() throws Exception {
        withTextFromSystemIn("godfather\n", "No\n")
            .execute(() -> {
                String output = tapSystemOut(() -> {
                    HangmanGameRunner.main(new String[] {});
                });
                assertThat(output)
                    .contains("You've won!!!");
            });

    }

    @Test
    @DisplayName("Test HangmanGameRunner with valid answers")
    void testHangmanGameRunnerWithValidLetterAnswers() throws Exception {
        withTextFromSystemIn("g\no\nd\nf\na\na\na\na\nt\nh\ne\nr\n", "No\n")
            .execute(() -> {
                String output = tapSystemOut(() -> {
                    HangmanGameRunner.main(new String[] {});
                });
                assertThat(output)
                    .contains("You've won!!!");
            });

    }

    @Test
    @DisplayName("Test HangmanGameRunner with invalid answers")
    void testHangmanGameRunnerWithInValidLetterAnswers() throws Exception {
        withTextFromSystemIn("i\nq\nk\nl\nr\nu\n", "No\n")
            .execute(() -> {
                String output = tapSystemOut(() -> {
                    HangmanGameRunner.main(new String[] {});
                });
                assertThat(output)
                    .contains("Sorry :( You've lost the game");
            });

    }

    @Test
    @DisplayName("Test HangmanGameRunner to exit")
    void testHangmanGameRunnerToExit() throws Exception {
        withTextFromSystemIn("/exit\n")
            .execute(() -> {
                String output = tapSystemOut(() -> {
                    HangmanGameRunner.main(new String[] {});
                });
                assertThat(output)
                    .contains("Game end. See you soon!");
            });

    }
}
