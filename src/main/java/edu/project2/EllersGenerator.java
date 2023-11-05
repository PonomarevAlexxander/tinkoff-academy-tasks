package edu.project2;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EllersGenerator implements MazeGenerator {
    private int nRows;
    private int nColumns;
    private Cell[][] maze;
    private Long set;
    private static final Random RANDOMIZER = new Random();

    @Override
    public Maze generate(int height, int width) {
        if (height < 2 || width < 2) {
            throw new IllegalArgumentException("minimum height and length should be 2");
        }
        this.nRows = height * 2 + 1;
        this.nColumns = width * 2 + 1;
        this.set = 0L;
        this.maze = new Cell[nRows][nColumns];

        fillBorder();
        Long[] setRepresentation = null;
        for (int row = 1; row < nRows - 2; row += 2) {
            setRepresentation = generateRow(setRepresentation);
            buildRightWalls(row, setRepresentation);
            buildHorizontalWalls(row, setRepresentation);
        }
        buildLastRow(setRepresentation);
        maze = Arrays.stream(maze)
            .map(cells -> Arrays.stream(cells)
                .map(cell -> Objects.isNull(cell) ? new Cell(Cell.CellType.PASSAGE) : cell)
                .toArray(Cell[]::new))
            .toArray(Cell[][]::new);
        return new Maze(height, width, maze);
    }

    private void buildLastRow(Long[] setRepresentation) {
        Long[] lastRepresentation = generateRow(setRepresentation);
        buildRightWalls(nRows - 2, lastRepresentation);
        for (int column = 1; column < nColumns - 2; column += 2) {
            Point current = new Point(column, nRows - 2);
            Point next = new Point(column + 2, nRows - 2);
            if (!areInSameSet(current, next, lastRepresentation)) {
                maze[nRows - 2][column + 1] = new Cell(Cell.CellType.PASSAGE);
            }
        }
    }

    private void buildHorizontalWalls(int row, Long[] setRepresentation) {
        Map<Long, Integer> elementsWithNoHorizontalWalls = Arrays.stream(setRepresentation)
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(Function.identity(), value -> 1, (oldV, one) -> oldV + 1));
        for (int column = 1; column < nColumns - 1; column += 2) {
            if (needToBuildWall() && (elementsWithNoHorizontalWalls.get(setRepresentation[column]) > 1)) {
                maze[row + 1][column] = new Cell(Cell.CellType.WALL);
                maze[row + 1][column + 1] = new Cell(Cell.CellType.WALL);
                maze[row + 1][column - 1] = new Cell(Cell.CellType.WALL);
                elementsWithNoHorizontalWalls.compute(setRepresentation[column], (key, value) -> value - 1);
                setRepresentation[column] = null;
            }
        }
    }

    private void buildRightWalls(int row, Long[] setRepresentation) {
        for (int column = 1; column < nColumns - 2; column += 2) {
            Point current = new Point(column, row);
            Point next = new Point(column + 2, row);
            if (needToBuildWall()) {
                buildRightWall(current);
            } else {
                if (areInSameSet(current, next, setRepresentation)) {
                    buildRightWall(current);
                } else {
                    uniteSets(current, next, setRepresentation);
                }
            }
        }
    }

    private void buildRightWall(Point position) {
        maze[position.y() - 1][position.x() + 1] = new Cell(Cell.CellType.WALL);
        maze[position.y()][position.x() + 1] = new Cell(Cell.CellType.WALL);
        maze[position.y() + 1][position.x() + 1] = new Cell(Cell.CellType.WALL);
    }

    private boolean areInSameSet(Point current, Point next, Long[] setRepresentation) {
        return setRepresentation[current.x()].equals(setRepresentation[next.x()]);
    }

    private Long[] generateRow(Long[] previousRow) {
        Long[] result;
        if (previousRow == null) {
            result = new Long[nColumns];
            for (int index = 1; index < nColumns; index += 2) {
                result[index] = set++;
            }
        } else {
            result = previousRow;
            for (int index = 1; index < nColumns; index += 2) {
                if (result[index] == null) {
                    result[index] = set++;
                }
            }
        }
        return result;
    }

    private boolean needToBuildWall() {
        return RANDOMIZER.nextBoolean();
    }

    private void uniteSets(Point current, Point next, Long[] setRepresentation) {
        Long currentSet = setRepresentation[current.x()];
        Long nextSet = setRepresentation[next.x()];
        for (int index = 0; index < setRepresentation.length; index++) {
            if (Objects.nonNull(setRepresentation[index]) && setRepresentation[index].equals(nextSet)) {
                setRepresentation[index] = currentSet;
            }
        }
    }

    private void fillBorder() {
        for (int row = 0; row < nRows; row++) {
            for (int column = 0; column < nColumns;) {
                maze[row][column] = new Cell(Cell.CellType.WALL);
                if (row % (nRows - 1) == 0) {
                    column++;
                } else {
                    column += nColumns - 1;
                }
            }
        }
    }
}
