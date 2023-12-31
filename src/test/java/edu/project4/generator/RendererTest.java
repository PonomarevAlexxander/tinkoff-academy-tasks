package edu.project4.generator;

import edu.project4.transformations.LinearTransformations;
import edu.project4.transformations.NonLinearTransformations;
import edu.project4.transformations.Transformation;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import java.awt.Color;
import java.util.List;

@State(Scope.Benchmark)
public class RendererTest {
    @Param({"1000", "10000", "100000"})
    public int samples;
    public short iterPerSample = 500;
    public int threads = 4;
    public int x = 900;
    public int y = 900;
    public ParallelRenderer parallelRenderer;
    public SequentialRenderer sequentialRenderer;
    public List<Transformation> affins;
    public List<Transformation> variations;

    @Test
    void benchmark_render() throws RunnerException {
        Options options = new OptionsBuilder()
            .include(this.getClass().getName() + ".*")
            .mode(Mode.AverageTime)
            .warmupTime(TimeValue.seconds(1))
            .warmupIterations(2)
            .threads(1)
            .measurementIterations(2)
            .forks(1)
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .build();

        new Runner(options).run();
    }

    @Setup
    public void setup() {
        parallelRenderer = new ParallelRenderer(threads);
        sequentialRenderer = new SequentialRenderer();
        affins = List.of(
            LinearTransformations.affin(0.5, 0, 0, 0.5, 0, 0, Color.WHITE),
            LinearTransformations.affin(0.5, 0.5, 0.5, 0.5, 0.5, 0.5, Color.WHITE)
        );
        variations = List.of(
            NonLinearTransformations.cross()
        );
    }

    @Benchmark
    public FractalImage benchmark_sequential_render() {
        return sequentialRenderer.render(FractalImage.create(x, y), affins, variations, samples, iterPerSample, false);
    }

    @Benchmark
    public FractalImage benchmark_parallel_render() {
        return parallelRenderer.render(FractalImage.create(x, y), affins, variations, samples, iterPerSample, false);
    }
}
