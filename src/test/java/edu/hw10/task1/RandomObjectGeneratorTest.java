package edu.hw10.task1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RandomObjectGeneratorTest {
    @Test
    void test_nextObject_on_valid_record() {
        RandomObjectGenerator rog = new RandomObjectGenerator();

        Person person = rog.nextObject(Person.class);
        assertThat(person)
            .isNotNull()
            .isInstanceOf(Person.class);
        assertThat(person.id())
            .isNotNull()
            .isBetween(0L, Long.MAX_VALUE);
        assertThat(person.age())
            .isBetween(0, Integer.MAX_VALUE);
    }

    @Test
    void test_nextObject_on_valid_class() {
        RandomObjectGenerator rog = new RandomObjectGenerator();

        Fruit fruit = rog.nextObject(Fruit.class);
        assertThat(fruit)
            .isNotNull()
            .isInstanceOf(Fruit.class);
        assertThat(fruit.getWeight())
            .isNotNull();
    }

    @Test
    void test_nextObject_with_fabric_method_on_valid_class() {
        RandomObjectGenerator rog = new RandomObjectGenerator();

        Document document = rog.nextObject(Document.class, "create");
        assertThat(document)
            .isInstanceOf(Document.class);
        assertThat(document.text)
            .isNull();
    }

    @Test
    void test_nextObject_on_invalid_record() {
        RandomObjectGenerator rog = new RandomObjectGenerator();

        assertThatThrownBy(() -> rog.nextObject(Document.class))
            .isInstanceOf(IllegalArgumentException.class);
    }

    public record Document(String text) {
        public static Document create() {
            return new Document(null);
        }
    }

    public record Person(@NotNull @Min(0) Long id, @Min(0) int age, boolean subscribed) {
    }

    public static class Fruit {
        private Integer weight;
        private boolean wormed;

        public Fruit(@NotNull Integer weight, boolean wormed) {
            this.weight = weight;
            this.wormed = wormed;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public boolean isWormed() {
            return wormed;
        }

        public void setWormed(boolean wormed) {
            this.wormed = wormed;
        }
    }
}
