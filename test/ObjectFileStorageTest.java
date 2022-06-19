import ru.mystudies.basejava.storage.FileStorage;
import ru.mystudies.basejava.storage.serializer.ObjectStreamSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public static FileStorage fileStorage = new FileStorage(STORAGE_DIR, new ObjectStreamSerializer());

    public ObjectFileStorageTest() {
        super(fileStorage);
    }
}
