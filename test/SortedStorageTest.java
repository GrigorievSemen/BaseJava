import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mystudies.basejava.exception.StorageException;
import ru.mystudies.basejava.model.Resume;
import ru.mystudies.basejava.storage.AbstractArrayStorage;
import ru.mystudies.basejava.storage.SortedArrayStorage;

import static org.junit.jupiter.api.Assertions.fail;


public class SortedStorageTest extends AbstractStorageTest {
    public static SortedArrayStorage sortedArrayStorage = new SortedArrayStorage();

    public SortedStorageTest() {
        super(sortedArrayStorage);
    }

    @Test
    public void saveOverflow() {
        StorageException thrown = Assertions.assertThrows(StorageException.class, () -> {
            sortedArrayStorage.clear();
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                try {
                    sortedArrayStorage.save(new Resume());
                } catch (StorageException e) {
                    fail("The array was filled prematurely");
                }
            }
            sortedArrayStorage.save(new Resume());
        });
    }
}

