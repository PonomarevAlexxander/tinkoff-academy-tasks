package edu.hw1;

import java.util.Objects;

public class Task1 {

    private Task1() {
    }

    public static int minutesToSeconds(String time) {
        final int SECONDS_IN_MINUTE = 60;
        Objects.requireNonNull(time);
        String[] minutesAndSeconds = time.split(":");
        if (minutesAndSeconds.length != 2) {
            return -1;
        }
        int seconds = Integer.parseInt(minutesAndSeconds[1]);
        return seconds >= SECONDS_IN_MINUTE ? -1 : Integer.parseInt(minutesAndSeconds[0]) * SECONDS_IN_MINUTE + seconds;
    }
}
