package edu.project2;

public record Cell(CellType type) {
    public enum CellType {
        PASSAGE,
        WALL
    }
}
