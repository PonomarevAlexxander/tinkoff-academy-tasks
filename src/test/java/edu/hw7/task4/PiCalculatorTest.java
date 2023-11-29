package edu.hw7.task4;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@State(Scope.Benchmark)
public class PiCalculatorTest {
    @Param({"10000000", "100000000", "1000000000"})
    public long iterations;

    @Test
    void benchmark_getPi() throws RunnerException {
        Options options = new OptionsBuilder()
            .include(this.getClass().getName() + ".*")
            .mode(Mode.AverageTime)
            .warmupTime(TimeValue.seconds(1))
            .warmupIterations(6)
            .threads(1)
            .measurementIterations(1)
            .forks(1)
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .build();

        new Runner(options).run();
    }

    @ParameterizedTest
    @ValueSource(longs = {10000000, 100000000, 1000000000})
    void test_getPi_offset(long iterations) {
        PiCalculator sequentialPiCalculator = new SequentialPiCalculator(iterations);
        assertThat(sequentialPiCalculator.getPi())
            .isCloseTo(Math.PI, Offset.offset(1E-2));
        PiCalculator parallelPiCalculator = new ParallelPiCalculator(iterations);
        assertThat(parallelPiCalculator.getPi())
            .isCloseTo(Math.PI, Offset.offset(1E-2));
    }

    @Benchmark
    public void benchmark_sequential_getPi() {
        PiCalculator sequential = new SequentialPiCalculator(iterations);
        sequential.getPi();
    }

    @Benchmark
    public void benchmark_parallel_getPi() {
        PiCalculator parallel = new ParallelPiCalculator(iterations);
        parallel.getPi();
    }
}
