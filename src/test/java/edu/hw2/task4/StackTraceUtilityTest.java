package edu.hw2.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StackTraceUtilityTest {

    @Test
    @DisplayName("Test callingInfo")
    void testCallingInfo() {
        CallingInfo info = StackTraceUtility.callingInfo();
        assertThat(info)
            .extracting(CallingInfo::className, CallingInfo::methodName)
            .containsOnly("edu.hw2.task4.StackTraceUtilityTest", "testCallingInfo");
    }
}
