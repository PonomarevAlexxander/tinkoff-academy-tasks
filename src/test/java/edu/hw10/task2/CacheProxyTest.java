package edu.hw10.task2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CacheProxyTest {

    @Test
    void test_create() {
        Factorial original = new SimpleFact();
        Factorial proxied = CacheProxy.create(original, Factorial.class);

        assertThat(proxied.compute(100))
            .isEqualTo(original.compute(100));
    }

    private interface Factorial {
        @Cache
        long compute(long number);
    }

    private class SimpleFact implements Factorial {

        @Override
        public long compute(long number) {
            if (number < 2) {
                return 1;
            }
            return number * compute(number - 1);
        }
    }
}
