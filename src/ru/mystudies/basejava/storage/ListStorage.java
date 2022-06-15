package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    public static final List<Resume> storage = new ArrayList<>();

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        storage.set(searchKey, resume);
        System.out.println("Resume <" + resume.getUuid() + "> updated successfully" + "\n");
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        storage.add(resume);
        System.out.println("Add new resume: " + resume.getUuid() + "\n");
    }

    @Override
    protected void doDelete(Integer searchKey) {
        String uuid = storage.get(searchKey).getUuid();
        storage.remove((int) searchKey);
        System.out.println("\nResume <" + uuid + "> deleted successfully");
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(List.of(storage.toArray(new Resume[0])));
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected boolean isExist(Integer Integer) {
        return Integer >= 0;
    }
}
