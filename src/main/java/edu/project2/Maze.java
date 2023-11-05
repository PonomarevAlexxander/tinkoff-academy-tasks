package edu.project2;

import java.util.Arrays;

public class Maze {
    private final Cell[][] cells;
    private final int height;
    private final int width;

    public Maze(int height, int width, Cell[][] cells) {
        this.cells = Arrays.copyOf(cells, cells.length);
        this.height = height;
        this.width = width;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(Point cellPosition) {
        return cells[cellPosition.y()][cellPosition.x()];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
