package edu.project4.generator;

import edu.project4.transformations.Transformation;
import java.util.List;

@FunctionalInterface
public interface Renderer {
    FractalImage render(
        FractalImage canvas,
        List<Transformation> afines,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        boolean symetri
    );
}
