package edu.hw10.task1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class RandomObjectGenerator {
    private final Random random = new Random();
    private final Map<Class<?>, Function<Parameter, Object>> generators = Map.of(
        Integer.class, this::getRandomInteger,
        int.class, this::getRandomInteger,
        Float.class, this::getRandomFloat,
        float.class, this::getRandomFloat,
        Double.class, this::getRandomDouble,
        double.class, this::getRandomDouble,
        Long.class, this::getRandomLong,
        long.class, this::getRandomLong,
        Boolean.class, this::getRandomBool,
        boolean.class, this::getRandomBool
    );
    private final double nullProbability = 0.1;

    public <T> T nextObject(Class<T> clazz) {
        for (var constructor : clazz.getConstructors()) {
            List<Object> generatedParameters = generateRandomParameters(constructor.getParameters());
            if (generatedParameters == null) {
                continue;
            }
            try {
                return (T) constructor.newInstance(generatedParameters.toArray());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("provided class has no public constructors with supported parameters");
    }

    public <T> T nextObject(Class<T> clazz, String fabricMethod) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(fabricMethod) && method.getReturnType().equals(clazz)
                && Modifier.isStatic(method.getModifiers())) {

                List<Object> generatedParameters = generateRandomParameters(method.getParameters());
                if (generatedParameters == null) {
                    continue;
                }
                try {
                    return (T) method.invoke(null, generatedParameters.toArray());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new IllegalArgumentException("provided class has no public fabric method with supported parameters");
    }

    private List<Object> generateRandomParameters(Parameter... parameters) {
        List<Object> generatedParams = new LinkedList<>();
        for (var parameter : parameters) {
            var generator = generators.get(parameter.getType());
            if (generator == null) {
                return null;
            }
            generatedParams.add(generator.apply(parameter));
        }
        return generatedParams;
    }

    private Integer getRandomInteger(Parameter parameter) {
        boolean notNull = parameter.isAnnotationPresent(NotNull.class);
        if (!notNull && (parameter.getType() != int.class) && (random.nextDouble() < nullProbability)) {
            return null;
        }
        return random.nextInt(getLowerBound(parameter), getUpperBound(parameter));
    }

    private Float getRandomFloat(Parameter parameter) {
        boolean notNull = parameter.isAnnotationPresent(NotNull.class);
        if (!notNull && (parameter.getType() != float.class) && (random.nextDouble() < nullProbability)) {
            return null;
        }
        return random.nextFloat(getLowerBound(parameter), getUpperBound(parameter));
    }

    private Double getRandomDouble(Parameter parameter) {
        boolean notNull = parameter.isAnnotationPresent(NotNull.class);
        if (!notNull && (parameter.getType() != boolean.class) && (random.nextDouble() < nullProbability)) {
            return null;
        }
        return random.nextDouble(getLowerBound(parameter), getUpperBound(parameter));
    }

    private Long getRandomLong(Parameter parameter) {
        boolean notNull = parameter.isAnnotationPresent(NotNull.class);
        if (!notNull && (parameter.getType() != long.class) && (random.nextDouble() < nullProbability)) {
            return null;
        }
        return random.nextLong(getLowerBound(parameter), getUpperBound(parameter));
    }

    private Boolean getRandomBool(Parameter parameter) {
        boolean notNull = parameter.isAnnotationPresent(NotNull.class);
        if (!notNull && (parameter.getType() != boolean.class) && (random.nextDouble() < nullProbability)) {
            return null;
        }
        return random.nextBoolean();
    }

    private int getLowerBound(Parameter parameter) {
        int min = Integer.MIN_VALUE;
        if (parameter.isAnnotationPresent(Min.class)) {
            min = parameter.getAnnotation(Min.class).value();
        }
        return min;
    }

    private int getUpperBound(Parameter parameter) {
        int max = Integer.MAX_VALUE;
        if (parameter.isAnnotationPresent(Max.class)) {
            max = parameter.getAnnotation(Max.class).value();
        }
        return max;
    }
}
