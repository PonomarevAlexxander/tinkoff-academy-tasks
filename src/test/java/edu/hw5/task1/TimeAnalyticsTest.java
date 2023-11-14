package edu.hw5.task1;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TimeAnalyticsTest {

    @Test
    void test_averageSessionTime() {
        List<String> durations = List.of(
            "2022-03-12, 20:20 - 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022-04-02, 01:20"
        );
        assertThat(TimeAnalytics.averageSessionTime(durations))
            .isEqualTo(Duration.parse("PT3H40M0S"));
    }

}
