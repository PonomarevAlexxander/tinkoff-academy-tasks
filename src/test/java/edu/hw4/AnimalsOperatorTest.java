package edu.hw4;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnimalsOperatorTest {
    @Test
    void test_sortedByHeight() {
        List<Animal> animals = List.of(
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 40, 20, true),
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 10, 20, true),
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 60, 20, true),
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 80, 20, true)
        );
        List<Animal> expected = List.of(
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 10, 20, true),
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 40, 20, true),
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 60, 20, true),
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 80, 20, true)
        );
        assertThat(AnimalsOperator.sortedByHeight(animals))
            .isEqualTo(expected);
    }

    @Test
    void test_getKHeaviest() {
        List<Animal> animals = List.of(
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 40, 20, true),
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 10, 40, true),
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 60, 10, true),
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 80, 15, true)
        );
        List<Animal> expected = List.of(
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 10, 40, true),
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 40, 20, true)
        );
        assertThat(AnimalsOperator.getKHeaviest(animals, 2))
            .isEqualTo(expected);
    }

    @Test
    void test_countByType() {
        List<Animal> animals = List.of(
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 40, 20, true),
            new Animal("dog", Animal.Type.FISH, Animal.Sex.M, 1, 10, 40, true),
            new Animal("dog", Animal.Type.BIRD, Animal.Sex.M, 1, 60, 10, true),
            new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 80, 15, true)
        );
        Map<Animal.Type, Long> expected = Map.of(
            Animal.Type.DOG, 2L,
            Animal.Type.FISH, 1L,
            Animal.Type.BIRD, 1L
        );
        assertThat(AnimalsOperator.countByType(animals))
            .isEqualTo(expected);
    }

    @Test
    void test_getAnimalWithLongestName() {
        List<Animal> animals = List.of(
            new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 1, 40, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 1, 10, 40, true),
            new Animal("Goldy", Animal.Type.BIRD, Animal.Sex.M, 1, 60, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 1, 80, 15, true)
        );
        Animal expected = new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 1, 40, 20, true);
        assertThat(AnimalsOperator.getAnimalWithLongestName(animals))
            .isEqualTo(expected);
    }

    @Test
    void test_getMostFrequentSex() {
        List<Animal> animals = List.of(
            new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 1, 40, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 1, 10, 40, true),
            new Animal("Goldy", Animal.Type.BIRD, Animal.Sex.F, 1, 60, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 1, 80, 15, true)
        );
        assertThat(AnimalsOperator.getMostFrequentSex(animals))
            .isEqualTo(Animal.Sex.M);
    }

    @Test
    void test_getHeaviestByType() {
        List<Animal> animals = List.of(
            new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 1, 40, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 1, 10, 40, true),
            new Animal("Goldy", Animal.Type.DOG, Animal.Sex.F, 1, 60, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 1, 80, 15, true)
        );
        Map<Animal.Type, Animal> expected = Map.of(
            Animal.Type.DOG, new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 1, 40, 20, true),
            Animal.Type.FISH, new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 1, 10, 40, true)
        );
        assertThat(AnimalsOperator.getHeaviestByType(animals))
            .isEqualTo(expected);
    }

    @Test
    void test_getKthOldest() {
        List<Animal> animals = List.of(
            new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 40, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 10, 40, true),
            new Animal("Goldy", Animal.Type.DOG, Animal.Sex.F, 1, 60, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 3, 80, 15, true)
        );
        assertThat(AnimalsOperator.getKthOldest(animals, 2))
            .isEqualTo(new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 3, 80, 15, true));
    }

    @Test
    void test_getHeaviest() {
        List<Animal> animals = List.of(
            new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 40, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 10, 40, true),
            new Animal("Goldy", Animal.Type.DOG, Animal.Sex.F, 1, 60, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 3, 80, 15, true)
        );
        assertThat(AnimalsOperator.getHeaviest(animals, 60))
            .isEqualTo(new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 10, 40, true));
    }

    @Test
    void test_getPawsSum() {
        List<Animal> animals = List.of(
            new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 40, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 10, 40, true),
            new Animal("Goldy", Animal.Type.DOG, Animal.Sex.F, 1, 60, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 3, 80, 15, true)
        );
        assertThat(AnimalsOperator.getPawsSum(animals))
            .isEqualTo(12);
    }

    @Test
    void test_getAgedNotAsPawsNumber() {
        List<Animal> animals = List.of(
            new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 40, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 10, 40, true),
            new Animal("Goldy", Animal.Type.DOG, Animal.Sex.F, 1, 60, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 4, 80, 15, true)
        );

        List<Animal> expected = List.of(
            new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 40, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 10, 40, true),
            new Animal("Goldy", Animal.Type.DOG, Animal.Sex.F, 1, 60, 10, true)
        );
        assertThat(AnimalsOperator.getAgedNotAsPawsNumber(animals))
            .isEqualTo(expected);
    }

    @Test
    void test_getDangerousAnimals() {
        List<Animal> animals = List.of(
            new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 120, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 40, false),
            new Animal("Goldy", Animal.Type.DOG, Animal.Sex.F, 1, 100, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 4, 80, 15, true)
        );

        List<Animal> expected = List.of(
            new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 120, 20, true)
        );
        assertThat(AnimalsOperator.getDangerousAnimals(animals))
            .isEqualTo(expected);
    }

    @Test
    void test_getNumberOfFatAnimals() {
        List<Animal> animals = List.of(
            new Animal("Mr.Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 120, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 10, 40, false),
            new Animal("Goldy", Animal.Type.DOG, Animal.Sex.F, 1, 20, 25, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 4, 20, 20, true)
        );
        assertThat(AnimalsOperator.getNumberOfFatAnimals(animals))
            .isEqualTo(2);
    }

    @Test
    void test_getAnimalsWithComplexNames() {
        List<Animal> animals = List.of(
            new Animal("Mr. Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 120, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 40, false),
            new Animal("Goldy Fish", Animal.Type.DOG, Animal.Sex.F, 1, 100, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 4, 80, 15, true)
        );

        List<Animal> expected = List.of(
            new Animal("Mr. Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 120, 20, true),
            new Animal("Goldy Fish", Animal.Type.DOG, Animal.Sex.F, 1, 100, 10, true)
        );
        assertThat(AnimalsOperator.getAnimalsWithComplexNames(animals))
            .isEqualTo(expected);
    }

    @Test
    void test_containsTallDog() {
        List<Animal> animals = List.of(
            new Animal("Mr. Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 120, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 40, false),
            new Animal("Goldy Fish", Animal.Type.DOG, Animal.Sex.F, 1, 100, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 4, 80, 15, true)
        );
        assertThat(AnimalsOperator.containsTallDog(animals, 100))
            .isEqualTo(true);
        assertThat(AnimalsOperator.containsTallDog(animals, 150))
            .isEqualTo(false);
    }

    @Test
    void test_getWeightSumByType() {
        List<Animal> animals = List.of(
            new Animal("Mr. Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 120, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 40, false),
            new Animal("Goldy Fish", Animal.Type.DOG, Animal.Sex.F, 1, 100, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 4, 80, 15, true)
        );
        Map<Animal.Type, Integer> expected = Map.of(
            Animal.Type.DOG, 35,
            Animal.Type.FISH, 40
        );
        assertThat(AnimalsOperator.getWeightSumByType(animals, 2, 6))
            .isEqualTo(expected);
    }

    @Test
    void test_sortedByTypeSexAndName() {
        List<Animal> animals = List.of(
            new Animal("Mr. Peabody", Animal.Type.CAT, Animal.Sex.M, 2, 120, 20, true),
            new Animal("Arnold", Animal.Type.CAT, Animal.Sex.M, 2, 120, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 40, false),
            new Animal("Goldy Fish", Animal.Type.DOG, Animal.Sex.F, 1, 100, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 4, 80, 15, true)
        );

        List<Animal> expected = List.of(
            new Animal("Arnold", Animal.Type.CAT, Animal.Sex.M, 2, 120, 20, true),
            new Animal("Mr. Peabody", Animal.Type.CAT, Animal.Sex.M, 2, 120, 20, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 4, 80, 15, true),
            new Animal("Goldy Fish", Animal.Type.DOG, Animal.Sex.F, 1, 100, 10, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 40, false)

        );
        assertThat(AnimalsOperator.sortedByTypeSexAndName(animals))
            .isEqualTo(expected);
    }

    @Test
    void test_doSpidersBiteMoreOftenThanDogs() {
        List<Animal> animals = new java.util.ArrayList<>(List.of(
            new Animal("Mr. Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 120, 20, false),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 40, false),
            new Animal("Spider Man", Animal.Type.SPIDER, Animal.Sex.M, 5, 111, 40, false),
            new Animal("Desert Spider", Animal.Type.SPIDER, Animal.Sex.M, 5, 111, 40, true),
            new Animal("Goldy Fish", Animal.Type.DOG, Animal.Sex.F, 1, 100, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 4, 80, 15, false)
        ));
        assertThat(AnimalsOperator.doSpidersBiteMoreOftenThanDogs(animals))
            .isEqualTo(false);
        animals.add(new Animal("Venom", Animal.Type.SPIDER, Animal.Sex.M, 5, 111, 40, true));
        assertThat(AnimalsOperator.doSpidersBiteMoreOftenThanDogs(animals))
            .isEqualTo(true);
    }

    @Test
    void test_getHeaviestFish() {
        List<Animal> animals1 = new java.util.ArrayList<>(List.of(
            new Animal("Mr. Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 120, 20, false),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 40, false),
            new Animal("Spider Man", Animal.Type.SPIDER, Animal.Sex.M, 5, 111, 40, false),
            new Animal("Desert Spider", Animal.Type.SPIDER, Animal.Sex.M, 5, 111, 40, true),
            new Animal("Goldy Fish", Animal.Type.DOG, Animal.Sex.F, 1, 100, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 4, 80, 15, false)
        ));
        List<Animal> animals2 = new java.util.ArrayList<>(List.of(
            new Animal("Mr. Peabody", Animal.Type.DOG, Animal.Sex.M, 2, 120, 20, false),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 20, false),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 10, false),
            new Animal("Spider Man", Animal.Type.SPIDER, Animal.Sex.M, 5, 111, 40, false)
        ));
        assertThat(AnimalsOperator.getHeaviestFish(animals1, animals2))
            .isEqualTo(new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 40, false));
    }

    @Test
    void test_getValidationInformation() {
        List<Animal> animals = List.of(
            new Animal("Mr. Peabody", Animal.Type.DOG, Animal.Sex.M, -2, 0, 20, true),
            new Animal("Churchill", Animal.Type.FISH, Animal.Sex.M, 5, 111, 40, false),
            new Animal("Goldy Fish", null, null, 1, 100, 10, true),
            new Animal("Hachiko", Animal.Type.DOG, Animal.Sex.M, 4, 80, 15, true)
        );
        assertThat(AnimalsOperator.getValidationInformation(animals))
            .asString()
            .contains("height", "age", "sex", "type");
    }
}
