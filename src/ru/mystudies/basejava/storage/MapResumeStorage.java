package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    public static final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    protected void doUpdate(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
        System.out.println("Resume <" + resume.getUuid() + "> updated successfully" + "\n");
    }

    @Override
    protected void doSave(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
        System.out.println("Add new resume: " + resume.getUuid() + "\n");
    }

    @Override
    protected void doDelete(Resume resume) {
        String key = resume.getUuid();
        storage.remove(key);
        System.out.println("\nResume <" + key + "> deleted successfully");
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
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
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }
}