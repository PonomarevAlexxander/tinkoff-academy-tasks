package edu.hw11.task1;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HelloBuddyTest {

    @Test
    public void test_HelloWorld() throws Exception {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(named("toString")).intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        assertThat(dynamicType.getDeclaredConstructor().newInstance().toString())
            .isEqualTo("Hello, ByteBuddy!");
    }
}
