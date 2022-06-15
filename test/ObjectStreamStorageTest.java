import ru.mystudies.basejava.storage.ObjectStreamStorage;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() throws Exception {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}
