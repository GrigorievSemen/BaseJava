import ru.mystudies.basejava.storage.ObjectStreamPathStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR.getAbsolutePath()));
    }
}
