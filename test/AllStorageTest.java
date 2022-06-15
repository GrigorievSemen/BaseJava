import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({StorageTest.class,
        SortedStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        ObjectStreamStorageTest.class,
        ObjectStreamPathStorageTest.class
})
public class AllStorageTest {
}
