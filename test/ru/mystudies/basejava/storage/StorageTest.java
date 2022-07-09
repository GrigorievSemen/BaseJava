package ru.mystudies.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mystudies.basejava.exception.StorageException;
import ru.mystudies.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.fail;

public class StorageTest extends AbstractStorageTest {
    public static ArrayStorage arrayStorage = new ArrayStorage();

    public StorageTest() {
        super(arrayStorage);
    }


    @Test
    public void saveOverflow() {

        StorageException thrown = Assertions.assertThrows(StorageException.class, () -> {
            arrayStorage.clear();
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                try {
                    arrayStorage.save(new Resume());
                } catch (StorageException e) {
                    fail("The array was filled prematurely");
                }
            }
            arrayStorage.save(new Resume());
        });
    }
}
