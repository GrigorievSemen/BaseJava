import ru.mystudies.basejava.storage.MapResumeStorage;

public class MapResumeStorageTest extends AbstractStorageTest {
    public static MapResumeStorage mapResumeStorage = new MapResumeStorage();

    public MapResumeStorageTest() {
        super(mapResumeStorage);
    }
}
