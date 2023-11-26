package edu.hw7.task2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.math.BigInteger;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class FactorialTest {

    @ParameterizedTest
    @MethodSource("provideData")
    void test_factorial_with_normal_data(int number, BigInteger expected) {
        assertThat(Factorial.factorial(number))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -100})
    void test_factorial_with_illegal_data(int number) {
        assertThatThrownBy(() -> Factorial.factorial(number))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
            Arguments.of(0, BigInteger.valueOf(1)),
            Arguments.of(10, BigInteger.valueOf(3628800)),
            Arguments.of(20, BigInteger.valueOf(2432902008176640000L))
        );
    }
}
