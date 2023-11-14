package edu.project2;

public record Cell(CellType type, int row, int column) {
    public enum CellType {
        PASSAGE,
        WALL
    }
}
