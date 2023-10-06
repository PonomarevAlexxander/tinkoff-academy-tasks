package edu.hw1;

public class Task7 {
    private Task7() {
    }

    //CHECKSTYLE:OFF: MultipleStringLiterals
    public static int rotateRight(int num, int nPositions) {
        if (num <= 0) {
            throw new IllegalArgumentException("Number should be positive");
        }

        int bitsToCode = (int) Math.ceil(Math.log(num + 1) / Math.log(2));
        int nShiftsRight = nPositions % bitsToCode;
        if (num == 1 || nShiftsRight == 0) {
            return num;
        }
        int nShiftsLeft = (bitsToCode - nShiftsRight) % bitsToCode;
        String bin = Integer.toBinaryString(num << nShiftsLeft).substring(nShiftsLeft);
        return (num >> nShiftsRight) | Integer.parseInt(bin, 2);
    }

    public static int rotateLeft(int num, int nPositions) {
        if (num <= 0) {
            throw new IllegalArgumentException("Number should be positive");
        }

        int bitsToCode = (int) Math.ceil(Math.log(num + 1) / Math.log(2));
        int nShiftsLeft = nPositions % bitsToCode;
        if (num == 1 || nShiftsLeft == 0) {
            return num;
        }
        int nShiftsRight = bitsToCode - nShiftsLeft;
        String bin = Integer.toBinaryString(num << nShiftsLeft).substring(nShiftsLeft);
        return (num >> nShiftsRight) | Integer.parseInt(bin, 2);
    }
    //CHECKSTYLE:ON: MultipleStringLiterals
}
