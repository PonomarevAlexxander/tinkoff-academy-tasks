package edu.hw1;

import java.util.Objects;

public class VideoUtils {

    private VideoUtils() {
    }

    //CHECKSTYLE:OFF: MagicNumber
    public static int minutesToSeconds(String time) {
        Objects.requireNonNull(time);
        String[] minutesAndSeconds = time.split(":");
        if (minutesAndSeconds.length != 2) {
            return -1;
        }
        int seconds = Integer.parseInt(minutesAndSeconds[1]);
        return seconds >= 60 ? -1 : Integer.parseInt(minutesAndSeconds[0]) * 60 + seconds;
    }
    //CHECKSTYLE:ON: MagicNumber
}
