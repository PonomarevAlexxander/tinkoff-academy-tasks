package edu.hw2.task1;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ExprTest {

    @ParameterizedTest
    @DisplayName("Test testExprEvaluation on normal data")
    @MethodSource("provideExprsForTest")
    void testExprEvaluation(Expr expr, double result) {
        assertThat(expr.evaluate())
            .isEqualTo(result);
    }

    private static Stream<Arguments> provideExprsForTest() {
        var two = new Expr.Constant(2);
        var four = new Expr.Constant(4);
        var negOne = new Expr.Negate(new Expr.Constant(1));
        var sumTwoFour = new Expr.Addition(two, four);
        var mult = new Expr.Multiplication(sumTwoFour, negOne);
        var exp = new Expr.Exponent(mult, 2);
        var res = new Expr.Addition(exp, new Expr.Constant(1));
        return Stream.of(
            Arguments.of(two, 2),
            Arguments.of(negOne, -1),
            Arguments.of(sumTwoFour, 6),
            Arguments.of(mult, -6),
            Arguments.of(exp, 36),
            Arguments.of(res, 37)
        );
    }
}
