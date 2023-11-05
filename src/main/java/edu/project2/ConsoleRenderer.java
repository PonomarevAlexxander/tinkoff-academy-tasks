package edu.project2;

import java.util.List;
import java.util.Objects;

public class ConsoleRenderer implements MazeRenderer {
    private static final String WALL_SYMBOL = "\uD83D\uDFEA";
    private static final String PASSAGE_SYMBOL = "\uD83D\uDFE8";
    private static final String PATH_SYMBOL = "\uD83D\uDFE7";
    private static final char LINE_BREAK = '\n';

    @Override
    public String render(Maze maze) {
        return render(maze, null);
    }

    @Override
    public String render(Maze maze, List<Point> path) {
        Objects.requireNonNull(maze, "maze cant bu null");
        StringBuilder result = new StringBuilder();
        Cell[][] cells = maze.getCells();
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                String charToAppend = switch (cells[row][column].type()) {
                    case WALL -> WALL_SYMBOL;
                    case PASSAGE ->
                        Objects.nonNull(path) && path.contains(new Point(column, row)) ? PATH_SYMBOL : PASSAGE_SYMBOL;
                };
                result.append(charToAppend);
            }
            result.append(LINE_BREAK);
        }
        return result.toString();
    }
}
