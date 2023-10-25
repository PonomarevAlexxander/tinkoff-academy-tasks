package edu.hw3.task5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Contacts {
    private static final String ASCENDING_ORDER = "ASC";
    private static final String DESCENDING_ORDER = "DESC";

    public static List<Object> parseContacts(List<String> contacts, String compareFormat) {
        if (compareFormat == null ||
            !(compareFormat.equals(ASCENDING_ORDER) || compareFormat.equals(DESCENDING_ORDER))) {
            throw new IllegalArgumentException("provided compare format is not available");
        }
        if (contacts == null) {
            return new ArrayList<>();
        }

        List<String> sortedContacts = new ArrayList<>(contacts);
        Comparator<String> comparator = (o1, o2) -> {
            String[] firstName = o1.split(" ");
            String[] secondName = o2.split(" ");
            if (firstName.length + firstName.length != 4) {
                return firstName[0].compareTo(secondName[0]);
            }
            return firstName[1].compareTo(secondName[1]);
        };
        if (compareFormat.equals(DESCENDING_ORDER)) {
            comparator = comparator.reversed();
        }
        sortedContacts.sort(comparator);
        return new ArrayList<>(sortedContacts);
    }
}
