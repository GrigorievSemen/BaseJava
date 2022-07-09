package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.exception.StorageException;
import ru.mystudies.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Array based ru.mystudies.basejava.storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    public static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void doUpdate(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
        System.out.println("Resume <" + resume.getUuid() + "> updated successfully" + "\n");
    }

    @Override
    public void doSave(Resume resume, Integer searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            addResume(resume, searchKey);
            size++;
            System.out.println("Add new resume: " + resume.getUuid() + "\n");
        }
    }

    @Override
    public void doDelete(Integer searchKey) {
        size--;
        deleteResume(searchKey);
        System.out.println("\nResume <" + storage[searchKey].getUuid() + "> deleted successfully");
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(List.of(Arrays.copyOfRange(storage, 0, size)));
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract void deleteResume(int index);

    protected abstract void addResume(Resume resume, int index);
}