package edu.hw1;

public class ChessUtils {
    private static final int[][] KNIGHT_MOVES = {
        {-2, -1},
        {-1, -2},
        {-2, 1},
        {-1, 2},
        {2, -1},
        {1, -2},
        {2, 1},
        {1, 2}
    };

    private ChessUtils() {
    }

    public static boolean knightBoardCapture(int[][] board) {
        final int BOARD_SIZE = board.length;
        for (int row = 0; row < BOARD_SIZE; ++row) {
            for (int column = 0; column < BOARD_SIZE; ++column) {
                if (board[row][column] == 0) {
                    continue;
                }
                for (var move : KNIGHT_MOVES) {
                    int nextRow = move[0] + row;
                    int nextColumn = move[1] + column;
                    if (nextColumn >= 0 && nextRow >= 0 && nextColumn < BOARD_SIZE && nextRow < BOARD_SIZE
                        && board[nextRow][nextColumn] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
