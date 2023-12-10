package edu.hw9.task3;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.MazeSolver;
import edu.project2.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class ParallelBreadthFIrstSolver implements MazeSolver {
    private static final Point[] D_POINTS = new Point[] {
        new Point(1, 0),
        new Point(0, 1),
        new Point(-1, 0),
        new Point(0, -1)
    };
    private Maze maze;
    private ConcurrentHashMap<Point, Point> parents;
    private final int threads = 4;
    private AtomicBoolean pathFound;

    @Override
    @SuppressWarnings("MagicNumber")
    public List<Point> solve(Maze maze, Point start, Point end) {
        if (maze.getCell(start).type() == Cell.CellType.WALL || maze.getCell(end).type() == Cell.CellType.WALL) {
            throw new IllegalArgumentException("choose another points");
        }
        this.maze = maze;
        this.parents = new ConcurrentHashMap<>();
        this.pathFound = new AtomicBoolean(false);
        BlockingQueue<Point> edgeCells = new LinkedBlockingQueue<>();

        edgeCells.add(start);
        parents.put(start, new Point(-1, -1));
        try (ExecutorService executorService = Executors.newFixedThreadPool(4)) {
            var resultingFuture = CompletableFuture.anyOf(Stream.generate(() -> CompletableFuture.supplyAsync(
                    () -> this.findPath(edgeCells, end),
                    executorService
                ))
                .limit(threads)
                .toArray(CompletableFuture[]::new));
            return (List<Point>) resultingFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Point> findPath(BlockingQueue<Point> cellQueue, Point end) {
        while (!pathFound.get()) {
            Point current = null;
            try {
                current = cellQueue.take();
            } catch (InterruptedException e) {
                continue;
            }
            if (current.equals(end)) {
                pathFound.set(true);
                return constructPath(end);
            }
            addAvailableCells(current, cellQueue);
        }
        return null;
    }

    @SuppressWarnings("MagicNumber")
    private void addAvailableCells(Point from, BlockingQueue<Point> cellQueue) {
        for (Point dPoint : D_POINTS) {
            Point next = new Point(from.x() + dPoint.x(), from.y() + dPoint.y());
            if (maze.getCell(next).type() == Cell.CellType.PASSAGE && !parents.containsKey(next)) {
                try {
                    if (!cellQueue.offer(next, 5, TimeUnit.SECONDS)) {
                        Thread.currentThread().interrupt();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                parents.put(next, from);
            }
        }
    }

    private List<Point> constructPath(Point end) {
        Point current = end;
        LinkedList<Point> path = new LinkedList<>();
        Point finalFlag = new Point(-1, -1);
        while (!current.equals(finalFlag)) {
            path.addFirst(current);
            current = parents.get(current);
        }
        return path;
    }
}
