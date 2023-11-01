package edu.hw3.task5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ContactsTest {

    @ParameterizedTest
    @MethodSource("provideValidContacts")
    void test_Contacts_parsContacts_on_valid_data(List<String> contacts, String compareOrder, List<Contacts> expected) {
        assertThat(Contacts.parseContacts(contacts, compareOrder))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"SOME", "RANDOM", "STRING"})
    @NullSource
    void test_Contacts_parsContacts_with_invalid_compare_type(String compareOrder) {
        assertThatThrownBy(() -> Contacts.parseContacts(List.of("Hi Mikki", "Li Chon", "Mike Corleone"), compareOrder))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideValidContacts() {
        return Stream.of(
            Arguments.of(
                List.of("John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"),
                "ASC",
                List.of(
                    Contact.fromString("Thomas Aquinas"),
                    Contact.fromString("Rene Descartes"),
                    Contact.fromString("David Hume"),
                    Contact.fromString("John Locke")
                )
            ),
            Arguments.of(
                List.of("Paul Erdos", "Leonhard Euler", "Carl Gauss"),
                "DESC",
                List.of(
                    Contact.fromString("Carl Gauss"),
                    Contact.fromString("Leonhard Euler"),
                    Contact.fromString("Paul Erdos")
                )
            ),
            Arguments.of(
                new ArrayList<String>(),
                "DESC",
                new ArrayList<>()
            ),
            Arguments.of(
                null,
                "DESC",
                new ArrayList<>()
            )
        );
    }

}
