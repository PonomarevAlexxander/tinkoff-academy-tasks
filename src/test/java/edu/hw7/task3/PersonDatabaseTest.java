package edu.hw7.task3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PersonDatabaseTest {
    @ParameterizedTest
    @MethodSource("provideDatabase")
    void test_PersonDatabase(PersonDatabase db) throws ExecutionException, InterruptedException {
        db.add(new Person(1, "Stue", "St Petersburg", "1102"));
        db.add(new Person(2, "Stue", "Moscow", "1111"));
        db.add(new Person(3, "Martin", "Berlin", "5432"));
        Person expected = new Person(4, "Alex", "Hamburg", "4545");
        db.add(expected);
        try (ExecutorService service = Executors.newFixedThreadPool(3)) {
            CompletableFuture<List<Person>> byAddress = CompletableFuture.supplyAsync(() -> db.findByAddress("Hamburg"), service);
            CompletableFuture<List<Person>> byName = CompletableFuture.supplyAsync(() -> db.findByName("Alex"), service);
            CompletableFuture<List<Person>> byPhone = CompletableFuture.supplyAsync(() -> db.findByPhone("4545"), service);
            assertThat(byAddress.get())
                .isEqualTo(byName.get())
                .isEqualTo(byPhone.get())
                .asList()
                .containsOnly(expected);
        }
        db.delete(2);
        assertThat(db.findByName("Stue").size())
            .isEqualTo(1);
    }

    private static Stream<Arguments> provideDatabase() {
        return Stream.of(
            Arguments.of(new SynchronizedPersonDatabase()),
            Arguments.of(new ReadWritePersonDatabase())
        );
    }
}
