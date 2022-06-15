package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    public static final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    protected void doUpdate(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
        System.out.println("Resume <" + resume.getUuid() + "> updated successfully" + "\n");
    }

    @Override
    protected void doSave(Resume resume, String searchKey) {
        storage.put(resume.getUuid(), resume);
        System.out.println("Add new resume: " + resume.getUuid() + "\n");
    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
        System.out.println("\nResume <" + searchKey + "> deleted successfully");
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> pair : storage.entrySet()) {
            if (pair.getKey().equals(uuid)) {
                return uuid;
            }
        }
        return "-1";
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(List.of(storage.values().toArray(new Resume[0])));
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
    protected boolean isExist(String searchKey) {
        return searchKey.equals("-1") ? false : true;
    }
}