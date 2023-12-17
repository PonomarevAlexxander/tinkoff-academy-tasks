package edu.hw11.task2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MessWithMethodsTest {
    @Test
    void test_method_implementation_changing() {
        ByteBuddyAgent.install();
        new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(named("sum")).intercept(MethodDelegation.to(ChangedArithmeticUtils.class))
            .make()
            .load(ArithmeticUtils.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        ArithmeticUtils utils = new ArithmeticUtils();
        assertThat(utils.sum(10, 20))
            .isEqualTo(200);
    }

    public static class ChangedArithmeticUtils {
        public static int sum(int a, int b) {
            return a * b;
        }
    }

    public static class ArithmeticUtils {
        public int sum(int a, int b) {
            return a + b;
        }
    }
}
