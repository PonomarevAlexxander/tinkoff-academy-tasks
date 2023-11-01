package edu.hw3.task5;

import org.jetbrains.annotations.NotNull;

public record Contact(@NotNull String name, String surname) implements Comparable<Contact> {

    public boolean hasSurname() {
        return surname != null;
    }

    public static Contact fromString(@NotNull String contact) {
        String[] nameAndSurname = contact.split(" ");
        if (nameAndSurname.length > 2 || nameAndSurname.length == 0) {
            throw new IllegalArgumentException("contact string should be in format 'Name Surname'");
        }
        if (nameAndSurname.length != 2) {
            return new Contact(nameAndSurname[0], null);
        }
        return new Contact(nameAndSurname[0], nameAndSurname[1]);
    }

    @Override
    public int compareTo(@NotNull Contact o) {
        if (!this.hasSurname() || !o.hasSurname()) {
            return name.compareTo(o.name);
        }
        return surname.compareTo(o.surname);
    }
}
