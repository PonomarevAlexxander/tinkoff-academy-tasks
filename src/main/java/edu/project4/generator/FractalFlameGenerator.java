package edu.project4.generator;

import edu.project4.ImageUtils;
import edu.project4.transformations.Transformation;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class FractalFlameGenerator {
    private FractalImage image;

    private FractalFlameGenerator(FractalImage image) {
        this.image = image;
    }

    @SuppressWarnings("ParameterNumber")
    public static FractalFlameGenerator generate(
        Renderer renderer,
        int width,
        int height,
        List<Transformation> afines,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        boolean symetri
    ) {
        return new FractalFlameGenerator(renderer.render(
            FractalImage.create(width, height),
            afines,
            variations,
            samples,
            iterPerSample,
            symetri
        ));
    }

    public FractalFlameGenerator proccess(ImageProcessor processor, ImageProcessor... processors) {
        processor.process(image);
        for (var proc : processors) {
            proc.process(image);
        }
        return this;
    }

    public FractalImage getImage() {
        return image;
    }

    public void save(Path file, ImageUtils.ImageFormat format) throws IOException {
        ImageUtils.save(image, file, format);
    }
}
