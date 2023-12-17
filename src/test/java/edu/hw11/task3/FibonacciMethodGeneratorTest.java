package edu.hw11.task3;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Ownership;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.Implementation;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FibonacciMethodGeneratorTest {
    @Test
    void test_class_creation()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        Class<?> dynamicType = new ByteBuddy()
//            .subclass(Object.class)
//            .defineMethod("fib", long.class, Ownership.MEMBER, Visibility.PUBLIC)
//            .withParameters(int.class)
//            .intercept(new Implementation.Simple(new FibonacciMethodGenerator()))
//            .make()
//            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
//            .getLoaded();
//        Object o = dynamicType.getDeclaredConstructor().newInstance();
//        Method fib = dynamicType.getMethod("fib", int.class);
//        assertThat(fib.invoke(o, 0))
//            .isEqualTo(0L);
    }
}
