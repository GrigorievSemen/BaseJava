package ru.mystudies.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mystudies.basejava.Config;
import ru.mystudies.basejava.exception.ExistStorageException;
import ru.mystudies.basejava.exception.NotExistStorageException;
import ru.mystudies.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.mystudies.basejava.TestData.*;

public abstract class AbstractStorageTest {


    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    private static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);


    final private Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_3, FULL_NAME_3);
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_3));
    }

    @Test
    public void updateNotExist() {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume());
        });
    }

    @Test
    public void getAllSorted() throws IOException {
        List<Resume> resumes = new ArrayList<>(Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
        sortedArray(resumes);
        System.out.println(resumes);
        System.out.println(storage.getAllSorted().toArray());
        Assertions.assertArrayEquals(resumes.toArray(), storage.getAllSorted().toArray());
    }


    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void getNotExist() {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID_NOT_EXIST);
        });
    }

    @Test
    public void save() {
        Resume RESUME_4 = new Resume();
        storage.save(RESUME_4);
        assertGet(RESUME_4);
    }

    @Test
    public void saveExist() {
        ExistStorageException thrown = Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(RESUME_1);
        });
    }

    @Test
    public void delete() throws IOException {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_3);
            assertSize(2);
            storage.get(UUID_3);
        });
    }

    @Test
    public void deleteNotExist() {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_NOT_EXIST);
        });
    }

    public void assertSize(int expectedAmount) {
        assertEquals(expectedAmount, storage.size());
    }

    public void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    public List<Resume> sortedArray(List<Resume> resumes) {
        List<Resume> list = resumes;
        list.sort(RESUME_COMPARATOR);
        return list;
    }
}