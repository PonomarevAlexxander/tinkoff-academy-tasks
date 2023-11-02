package edu.hw4;

import java.util.HashSet;
import java.util.Set;

public class AnimalValidationError implements ValidationError {
    private final String fieldName;
    private final String message;

    private AnimalValidationError(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static Set<ValidationError> validateAnimal(Animal animal) {
        Set<ValidationError> errors = new HashSet<>();
        if (animal.name().isBlank()) {
            errors.add(new AnimalValidationError("name", "name should be not blank string"));
        }
        if (animal.type() == null) {
            errors.add(new AnimalValidationError("type", "type should be not null"));
        }
        if (animal.sex() == null) {
            errors.add(new AnimalValidationError("sex", "sex should be not null"));
        }
        if (animal.age() < 0) {
            errors.add(new AnimalValidationError("age", "age should be positive number"));
        }
        if (animal.height() <= 0) {
            errors.add(new AnimalValidationError("height", "height should be positive number"));
        }
        if (animal.weight() <= 0) {
            errors.add(new AnimalValidationError("weight", "weight should be positive number"));
        }
        return errors;
    }
}
