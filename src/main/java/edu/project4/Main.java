package edu.project4;

import edu.project4.generator.FractalFlameGenerator;
import edu.project4.generator.ImageCorrector;
import edu.project4.generator.Renderer;
import edu.project4.generator.SequentialRenderer;
import edu.project4.transformations.LinearTransformations;
import edu.project4.transformations.NonLinearTransformations;
import edu.project4.transformations.Transformation;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private Main() {
    }

    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) throws IOException {
        List<Color> colors = List.of(
            new Color(161, 0, 0),
            new Color(234, 35, 0),
            new Color(255, 129, 0),
            new Color(242, 85, 0),
            new Color(216, 0, 0)
        );
        Random random = new Random();
        List<Transformation> affins = new ArrayList<>();
        int index = 0;
        while (affins.size() < 5) {
            try {
                affins.add(LinearTransformations.affin(
                    random.nextDouble(-1, 1),
                    random.nextDouble(-1, 1),
                    random.nextDouble(-1, 1),
                    random.nextDouble(-1, 1),
                    random.nextDouble(-1, 1),
                    random.nextDouble(-1, 1),
                    colors.get(index)
                ));
            } catch (Exception ignore) {
                continue;
            }
            index++;
        }
        List<Transformation> variation = List.of(
            NonLinearTransformations.cross(),
            NonLinearTransformations.handkerchief()
        );

        Renderer renderer = new SequentialRenderer();
        FractalFlameGenerator.generate(renderer, 1920, 1080, affins, variation, 1000, (short) 500, true)
            .proccess(new ImageCorrector(2.2))
            .save(Path.of("file.jpg"), ImageUtils.ImageFormat.JPEG);
    }
}
