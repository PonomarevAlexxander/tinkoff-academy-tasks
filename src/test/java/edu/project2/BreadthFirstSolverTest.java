package edu.project2;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BreadthFirstSolverTest {
    @Test
    void test_solve_on_valid_data() {
        Cell[][] cells = new Cell[][] {
            {new Cell(Cell.CellType.WALL, 0, 0), new Cell(Cell.CellType.WALL, 0, 1), new Cell(Cell.CellType.WALL, 0, 2),
                new Cell(Cell.CellType.WALL, 0, 3), new Cell(Cell.CellType.WALL, 0, 4)},
            {new Cell(Cell.CellType.WALL, 1, 0), new Cell(Cell.CellType.WALL, 1, 1), new Cell(Cell.CellType.PASSAGE, 1, 2),
                new Cell(Cell.CellType.PASSAGE, 1, 3), new Cell(Cell.CellType.WALL, 1, 4)},
            {new Cell(Cell.CellType.WALL, 2, 0), new Cell(Cell.CellType.PASSAGE, 2, 1), new Cell(Cell.CellType.WALL, 2, 2),
                new Cell(Cell.CellType.PASSAGE, 2, 3), new Cell(Cell.CellType.WALL, 2, 4)},
            {new Cell(Cell.CellType.WALL, 3, 0), new Cell(Cell.CellType.PASSAGE, 3, 1), new Cell(Cell.CellType.PASSAGE, 3, 2),
                new Cell(Cell.CellType.PASSAGE, 3, 3), new Cell(Cell.CellType.WALL, 3, 4)},
            {new Cell(Cell.CellType.WALL, 4, 0), new Cell(Cell.CellType.WALL, 4, 1), new Cell(Cell.CellType.WALL, 4, 2),
                new Cell(Cell.CellType.WALL, 4, 3), new Cell(Cell.CellType.WALL, 4, 4)}
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
            {new Cell(Cell.CellType.WALL, 0, 0), new Cell(Cell.CellType.WALL, 0, 1), new Cell(Cell.CellType.WALL, 0, 2),
                new Cell(Cell.CellType.WALL, 0, 3), new Cell(Cell.CellType.WALL, 0, 4)},
            {new Cell(Cell.CellType.WALL, 1, 0), new Cell(Cell.CellType.WALL, 1, 1), new Cell(Cell.CellType.PASSAGE, 1, 2),
                new Cell(Cell.CellType.PASSAGE, 1, 3), new Cell(Cell.CellType.WALL, 1, 4)},
            {new Cell(Cell.CellType.WALL, 2, 0), new Cell(Cell.CellType.PASSAGE, 2, 1), new Cell(Cell.CellType.WALL, 2, 2),
                new Cell(Cell.CellType.PASSAGE, 2, 3), new Cell(Cell.CellType.WALL, 2, 4)},
            {new Cell(Cell.CellType.WALL, 3, 0), new Cell(Cell.CellType.PASSAGE, 3, 1), new Cell(Cell.CellType.PASSAGE, 3, 2),
                new Cell(Cell.CellType.PASSAGE, 3, 3), new Cell(Cell.CellType.WALL, 3, 4)},
            {new Cell(Cell.CellType.WALL, 4, 0), new Cell(Cell.CellType.WALL, 4, 1), new Cell(Cell.CellType.WALL, 4, 2),
                new Cell(Cell.CellType.WALL, 4, 3), new Cell(Cell.CellType.WALL, 4, 4)}
        };
        Maze maze = new Maze(2, 2, cells);
        BreadthFirstSolver solver = new BreadthFirstSolver();
        assertThatThrownBy(() -> solver.solve(maze, new Point(0, 0), new Point(1, 1)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
