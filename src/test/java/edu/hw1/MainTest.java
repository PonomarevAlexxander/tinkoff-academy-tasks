package edu.hw1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static com.github.stefanbirkner.systemlambda.SystemLambda.*;

class MainTest {

    @Test
    void main() throws Exception {
        String text = tapSystemOut(() -> {
            Main.main(new String[] {});
        });
        Pattern regex = Pattern.compile("Hello, world!");
        Matcher matcher = regex.matcher(text);
        assertThat(matcher.find())
            .isTrue();
    }
}
