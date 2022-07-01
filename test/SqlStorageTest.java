import ru.mystudies.basejava.Config;
import ru.mystudies.basejava.storage.SqlStorage;

public class SqlStorageTest extends AbstractStorageTest{
    public static SqlStorage sqlStorage = new SqlStorage(Config.get().getDbUrl(),
            Config.get().getDbUser(), Config.get().getDbPassword());

    public SqlStorageTest() {
        super(sqlStorage);
    }
}
