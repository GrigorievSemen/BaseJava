import ru.mystudies.basejava.storage.PathStorage;
import ru.mystudies.basejava.storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public static PathStorage pathStorage = new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer());

    public ObjectPathStorageTest() {
        super(pathStorage);
    }
}
