package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void addResume(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[size];
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}