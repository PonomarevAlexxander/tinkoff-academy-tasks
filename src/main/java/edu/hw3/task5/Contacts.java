package edu.hw3.task5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Contacts {
    private static final String ASCENDING_ORDER = "ASC";
    private static final String DESCENDING_ORDER = "DESC";

    private Contacts() {

    }

    public static List<Contact> parseContacts(List<String> contacts, String compareFormat) {
        if (compareFormat == null
            || !(compareFormat.equals(ASCENDING_ORDER) || compareFormat.equals(DESCENDING_ORDER))) {
            throw new IllegalArgumentException("provided compare format is not available");
        }
        if (contacts == null) {
            return new ArrayList<>();
        }
        return contacts.stream()
            .map(Contact::fromString)
            .sorted(compareFormat.equals(ASCENDING_ORDER) ? Comparator.naturalOrder() : Comparator.reverseOrder())
            .collect(Collectors.toList());
    }
}
