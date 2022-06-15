import ru.mystudies.basejava.storage.PathStorage;
import ru.mystudies.basejava.storage.serializer.ObjectStreamSerializer;

public class PathStorageTest extends AbstractStorageTest {

    public static PathStorage pathStorage = new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer());

    public PathStorageTest() {
        super(pathStorage);
    }
}
