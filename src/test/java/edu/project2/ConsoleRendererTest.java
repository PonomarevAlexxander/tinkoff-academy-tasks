package edu.project2;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ConsoleRendererTest {
    @Test
    void test_render_on_valid_data() {
        Cell[][] cells = new Cell[][] {
            {new Cell(Cell.CellType.WALL), new Cell(Cell.CellType.WALL), new Cell(Cell.CellType.WALL),
                new Cell(Cell.CellType.WALL), new Cell(Cell.CellType.WALL)},
            {new Cell(Cell.CellType.WALL), new Cell(Cell.CellType.WALL), new Cell(Cell.CellType.PASSAGE),
                new Cell(Cell.CellType.PASSAGE), new Cell(Cell.CellType.WALL)},
            {new Cell(Cell.CellType.WALL), new Cell(Cell.CellType.PASSAGE), new Cell(Cell.CellType.WALL),
                new Cell(Cell.CellType.PASSAGE), new Cell(Cell.CellType.WALL)},
            {new Cell(Cell.CellType.WALL), new Cell(Cell.CellType.PASSAGE), new Cell(Cell.CellType.PASSAGE),
                new Cell(Cell.CellType.PASSAGE), new Cell(Cell.CellType.WALL)},
            {new Cell(Cell.CellType.WALL), new Cell(Cell.CellType.WALL), new Cell(Cell.CellType.WALL),
                new Cell(Cell.CellType.WALL), new Cell(Cell.CellType.WALL)}
        };
        Maze maze = new Maze(2, 2, cells);
        List<Point> path = List.of(new Point(1, 2), new Point(1, 3), new Point(2, 3));
        ConsoleRenderer renderer = new ConsoleRenderer();
        String result = renderer.render(maze, path);
        String expected = "\uD83D\uDFEA\uD83D\uDFEA\uD83D\uDFEA\uD83D\uDFEA\uD83D\uDFEA\n" +
            "\uD83D\uDFEA\uD83D\uDFEA\uD83D\uDFE8\uD83D\uDFE8\uD83D\uDFEA\n" +
            "\uD83D\uDFEA\uD83D\uDFE7\uD83D\uDFEA\uD83D\uDFE8\uD83D\uDFEA\n" +
            "\uD83D\uDFEA\uD83D\uDFE7\uD83D\uDFE7\uD83D\uDFE8\uD83D\uDFEA\n" +
            "\uD83D\uDFEA\uD83D\uDFEA\uD83D\uDFEA\uD83D\uDFEA\uD83D\uDFEA\n";
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void test_render_on_invalid_data() {
        ConsoleRenderer renderer = new ConsoleRenderer();
        assertThatThrownBy(() -> renderer.render(null, new ArrayList<>()))
            .isInstanceOf(NullPointerException.class);
    }
}
