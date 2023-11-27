package edu.hw7.task3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWritePersonDatabase implements PersonDatabase {
    private final Map<Integer, Person> persons = new HashMap<>();
    private final Map<String, List<Person>> personsByName = new HashMap<>();
    private final Map<String, List<Person>> personsByAddress = new HashMap<>();
    private final Map<String, List<Person>> personsByPhone = new HashMap<>();

    ReadWriteLock lock = new ReentrantReadWriteLock(true);

    @Override
    public void add(Person person) {
        lock.writeLock().lock();
        try {
            Person previous = persons.put(person.id(), person);
            updateReverseKeys(previous, person);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        lock.writeLock().lock();
        try {
            Person oldValue = persons.remove(id);
            removeFromReverseKeys(oldValue);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        List<Person> result;
        try {
            result = personsByName.get(name);
        } finally {
            lock.readLock().unlock();
        }
        return result;
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        List<Person> result;
        try {
            result = personsByAddress.get(address);
        } finally {
            lock.readLock().unlock();
        }
        return result;
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        List<Person> result;
        try {
            result = personsByPhone.get(phone);
        } finally {
            lock.readLock().unlock();
        }
        return result;
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
        personsByName.merge(newValue.name(), newListWith(newValue), ReadWritePersonDatabase::unite);
        personsByAddress.merge(newValue.address(), newListWith(newValue), ReadWritePersonDatabase::unite);
        personsByPhone.merge(newValue.phoneNumber(), newListWith(newValue), ReadWritePersonDatabase::unite);
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
