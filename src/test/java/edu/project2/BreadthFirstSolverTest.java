package edu.project2;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BreadthFirstSolverTest {
    @Test
    void test_solve_on_valid_data() {
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
        BreadthFirstSolver solver = new BreadthFirstSolver();
        assertThat(solver.solve(maze, new Point(1, 2), new Point(2, 3)))
            .isEqualTo(List.of(
                new Point(1, 2),
                new Point(1, 3),
                new Point(2, 3)
            ));
    }

    @Test
    void test_solve_on_invalid_data() {
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
        BreadthFirstSolver solver = new BreadthFirstSolver();
        assertThatThrownBy(() -> solver.solve(maze, new Point(0, 0), new Point(1, 1)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
