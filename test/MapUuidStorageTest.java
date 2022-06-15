import ru.mystudies.basejava.storage.MapUuidStorage;

public class MapUuidStorageTest extends AbstractStorageTest {
    public static MapUuidStorage mapUuidStorage = new MapUuidStorage();

    public MapUuidStorageTest() {
        super(mapUuidStorage);
    }
}
