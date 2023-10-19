package edu.project1;

import java.util.ArrayList;
import java.util.List;

public class ClassicGallowsProvider implements GallowsProvider {
    private static final String FIRST_FRAME = """







           _|___
        """;
    private static final String SECOND_FRAME = """

            |
            |
            |
            |
            |
            |
           _|___
        """;
    private static final String THIRD_FRAME = """
             _______
            |/
            |
            |
            |
            |
            |
           _|___
        """;
    private static final String FOURTH_FRAME = """
             _______
            |/      |
            |
            |
            |
            |
            |
           _|___
        """;
    private static final String FIFTH_FRAME = """
             _______
            |/      |
            |      (_)
            |      \\|/
            |       |
            |      / \\
            |
           _|___
        """;

    private static final List<String> FRAMES = new ArrayList<>() {{
        add(FIRST_FRAME);
        add(SECOND_FRAME);
        add(THIRD_FRAME);
        add(FOURTH_FRAME);
        add(FIFTH_FRAME);
    }};

    private int currentFrame = -1;

    @Override
    public int getLeftAttempts() {
        return FRAMES.size() - currentFrame - 1;
    }

    @Override
    public String getGallows() {
        if (currentFrame < 0) {
            return null;
        }
        return FRAMES.get(currentFrame);
    }

    @Override
    public void buildNextElement() {
        if (currentFrame == (FRAMES.size() - 1)) {
            throw new IllegalStateException("Gallows is already built");
        }
        ++currentFrame;
    }

    @Override
    public void destroyGallows() {
        currentFrame = -1;
    }
}
