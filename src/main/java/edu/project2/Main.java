package edu.project2;

import java.util.Arrays;
import java.util.List;

public class Main {
    private static final int HEIGHT = 10;
    private static final int WIDTH = 10;

    private Main() {
    }

    @SuppressWarnings("RegexpSinglelineJava")
    public static void main(String[] args) {
        MazeGenerator generator = new EllersGenerator();
        MazeRenderer printer = new ConsoleRenderer();
        MazeSolver solver = new BreadthFirstSolver();
        Maze maze = generator.generate(HEIGHT, WIDTH);
        List<Cell> passages = Arrays.stream(maze.getCells())
            .flatMap(Arrays::stream)
            .filter(cell -> cell.type() == Cell.CellType.PASSAGE)
            .toList();
        Cell firstCell = passages.get(0);
        Cell endCell = passages.get(passages.size() - 1);
        Point startPoint = new Point(firstCell.column(), firstCell.row());
        Point endPoint = new Point(endCell.column(), endCell.row());
        List<Point> path = solver.solve(maze, startPoint, endPoint);
        System.out.println(printer.render(maze, path));
    }
}
