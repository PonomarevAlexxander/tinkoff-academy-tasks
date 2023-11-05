package edu.project2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BreadthFirstSolver implements MazeSolver {
    private static final Point[] D_POINTS = new Point[] {
        new Point(1, 0),
        new Point(0, 1),
        new Point(-1, 0),
        new Point(0, -1)
    };
    private Maze maze;
    private Map<Point, Point> parents;

    @Override
    public List<Point> solve(Maze maze, Point start, Point end) {
        if (maze.getCell(start).type() == Cell.CellType.WALL || maze.getCell(end).type() == Cell.CellType.WALL) {
            throw new IllegalArgumentException("choose another points");
        }
        this.maze = maze;
        this.parents = new HashMap<>();
        Queue<Point> edgeCells = new LinkedList<>();

        edgeCells.add(start);
        parents.put(start, null);
        while (!edgeCells.isEmpty()) {
            Point current = edgeCells.poll();
            if (current.equals(end)) {
                return constructPath(end);
            }
            addAvailableCells(current, edgeCells);
        }
        return null;
    }

    private void addAvailableCells(Point from, Queue<Point> cellQueue) {
        for (Point dPoint : D_POINTS) {
            Point next = new Point(from.x() + dPoint.x(), from.y() + dPoint.y());
            if (maze.getCell(next).type() == Cell.CellType.PASSAGE && !parents.containsKey(next)) {
                cellQueue.add(next);
                parents.put(next, from);
            }
        }
    }

    private List<Point> constructPath(Point end) {
        Point current = end;
        LinkedList<Point> path = new LinkedList<>();
        while (current != null) {
            path.addFirst(current);
            current = parents.get(current);
        }
        return path;
    }
}
