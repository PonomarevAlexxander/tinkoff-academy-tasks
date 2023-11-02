package edu.hw4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalsOperator {
    private static final String NULL_EXCEPTION_MESSAGE = "list of animals must be not null";

    private AnimalsOperator() {

    }

    public static List<Animal> sortedByHeight(List<Animal> animals) {
        checkIfNull(animals);
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::height))
            .toList();
    }

    public static List<Animal> getKHeaviest(List<Animal> animals, int k) {
        checkIfNull(animals);
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(k)
            .toList();
    }

    public static Map<Animal.Type, Long> countByType(List<Animal> animals) {
        checkIfNull(animals);
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.counting()));
    }

    public static Animal getAnimalWithLongestName(List<Animal> animals) {
        checkIfNull(animals);
        return animals.stream()
            .max(Comparator.comparing(animal -> animal.name().length()))
            .orElseThrow();
    }

    public static Animal.Sex getMostFrequentSex(List<Animal> animals) {
        checkIfNull(animals);
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElseThrow()
            .getKey();
    }

    public static Map<Animal.Type, Animal> getHeaviestByType(List<Animal> animals) {
        checkIfNull(animals);
        return animals.stream()
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.maxBy(Comparator.comparing(Animal::weight))
            ))
            .entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, value -> value.getValue().orElseThrow()));
    }

    public static Animal getKthOldest(List<Animal> animals, int k) {
        checkIfNull(animals);
        return animals.stream()
            .sorted(Comparator.comparing(Animal::age).reversed())
            .limit(k)
            .reduce((first, second) -> second)
            .orElseThrow();
    }

    public static Animal getHeaviest(List<Animal> animals, int maximumHeight) {
        checkIfNull(animals);
        return animals.stream()
            .filter(animal -> animal.height() < maximumHeight)
            .max(Comparator.comparing(Animal::weight))
            .orElseThrow();
    }

    public static Integer getPawsSum(List<Animal> animals) {
        checkIfNull(animals);
        return animals.stream()
            .mapToInt(Animal::paws)
            .sum();
    }

    public static List<Animal> getAgedNotAsPawsNumber(List<Animal> animals) {
        checkIfNull(animals);
        return animals.stream()
            .filter(animal -> animal.age() != animal.paws())
            .toList();
    }

    public static List<Animal> getDangerousAnimals(List<Animal> animals) {
        checkIfNull(animals);
        final int fromHeight = 100;
        return animals.stream()
            .filter(animal -> animal.bites() && animal.height() > fromHeight)
            .toList();
    }

    public static Integer getNumberOfFatAnimals(List<Animal> animals) {
        checkIfNull(animals);
        return Math.toIntExact(animals.stream()
            .filter(animal -> animal.weight() > animal.height())
            .count());
    }

    public static List<Animal> getAnimalsWithComplexNames(List<Animal> animals) {
        checkIfNull(animals);
        return animals.stream()
            .filter(animal -> animal.name().split(" ").length > 1)
            .toList();
    }

    public static Boolean containsTallDog(List<Animal> animals, int minHeight) {
        checkIfNull(animals);
        return animals.stream()
            .anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() > minHeight);
    }

    public static Map<Animal.Type, Integer> getWeightSumByType(List<Animal> animals, int minAge, int maxAge) {
        checkIfNull(animals);
        return animals.stream()
            .filter(animal -> animal.age() <= maxAge && animal.age() >= minAge)
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.summingInt(Animal::weight)
            ));
    }

    public static List<Animal> sortedByTypeSexAndName(List<Animal> animals) {
        checkIfNull(animals);
        return animals.stream()
            .sorted(Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name))
            .toList();
    }

    public static Boolean doSpidersBiteMoreOftenThanDogs(List<Animal> animals) {
        checkIfNull(animals);
        long spidersThatBite = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER && animal.bites())
            .count();
        long dogsThatBite = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG && animal.bites())
            .count();
        return spidersThatBite > dogsThatBite;
    }

    @SafeVarargs public static Animal getHeaviestFish(List<Animal>... animals) {
        if (animals.length < 2) {
            throw new IllegalArgumentException("minimum 2 lists required");
        }
        return Arrays.stream(animals)
            .flatMap(List::stream)
            .filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparing(Animal::weight))
            .orElseThrow();
    }

    public static Map<String, Set<ValidationError>> validateAnimals(List<Animal> animals) {
        checkIfNull(animals);
        return animals.stream()
            .map(animal -> Map.entry(animal.name(), AnimalValidationError.validateAnimal(animal)))
            .filter(entry -> !entry.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<String, String> getValidationInformation(List<Animal> animals) {
        checkIfNull(animals);
        return validateAnimals(animals).entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> "Errors in fields: " + String.join(", ", entry.getValue().stream()
                    .map(ValidationError::getFieldName)
                    .toList())
            ));
    }

    private static void checkIfNull(List<Animal> animals) {
        Objects.requireNonNull(animals, NULL_EXCEPTION_MESSAGE);
    }
}
