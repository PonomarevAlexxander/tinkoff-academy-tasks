package edu.hw3.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BracketsClusteringTest {

    @ParameterizedTest
    @MethodSource("provideBrackets")
    void test_BracketsClustering_on_normal_data(String str, List<String> expected) {
        assertThat(BracketsClustering.clusterize(str))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    void test_BracketsClustering_on_null_data(String str) {
        assertThatThrownBy(() -> BracketsClustering.clusterize(str))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"(()", "()()))"})
    void test_BracketsClustering_on_not_stabilized_data(String str) {
        assertThatThrownBy(() -> BracketsClustering.clusterize(str))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideBrackets() {
        return Stream.of(
            Arguments.of("", new ArrayList<String>()),
            Arguments.of("()()()", new ArrayList<String>(){{
                    add("()");
                    add("()");
                    add("()");
                }}),
            Arguments.of("((()))", new ArrayList<String>(){{
                add("((()))");
            }}),
            Arguments.of("((()))(())()()(()())", new ArrayList<String>(){{
                add("((()))");
                add("(())");
                add("()");
                add("()");
                add("(()())");
            }}),
            Arguments.of("((())())(()(()()))", new ArrayList<String>(){{
                add("((())())");
                add("(()(()()))");
            }})
        );
    }
}
