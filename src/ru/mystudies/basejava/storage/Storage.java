package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.model.Resume;

import java.io.IOException;
import java.util.List;

public interface Storage {

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted() throws IOException;

    int size();
}