package edu.hw7.task3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SynchronizedPersonDatabase implements PersonDatabase {
    private final Map<Integer, Person> persons = new HashMap<>();
    private final Map<String, List<Person>> personsByName = new HashMap<>();
    private final Map<String, List<Person>> personsByAddress = new HashMap<>();
    private final Map<String, List<Person>> personsByPhone = new HashMap<>();

    @Override
    public synchronized void add(Person person) {
        Person previous = persons.put(person.id(), person);
        updateReverseKeys(previous, person);
    }

    @Override
    public synchronized void delete(int id) {
        Person oldValue = persons.remove(id);
        removeFromReverseKeys(oldValue);
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return personsByName.get(name);
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return personsByAddress.get(address);
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return personsByPhone.get(phone);
    }

    private void removeFromReverseKeys(Person person) {
        personsByPhone.get(person.phoneNumber()).remove(person);
        personsByName.get(person.name()).remove(person);
        personsByAddress.get(person.address()).remove(person);
    }

    private void updateReverseKeys(Person oldValue, Person newValue) {
        if (oldValue != null) {
            removeFromReverseKeys(oldValue);
        }
        personsByName.merge(newValue.name(), newListWith(newValue), SynchronizedPersonDatabase::unite);
        personsByAddress.merge(newValue.address(), newListWith(newValue), SynchronizedPersonDatabase::unite);
        personsByPhone.merge(newValue.phoneNumber(), newListWith(newValue), SynchronizedPersonDatabase::unite);
    }

    private static List<Person> unite(List<Person> uniteInto, List<Person> list) {
        uniteInto.addAll(list);
        return uniteInto;
    }

    private static List<Person> newListWith(Person person) {
        List<Person> result = new LinkedList<>();
        result.add(person);
        return result;
    }
}
