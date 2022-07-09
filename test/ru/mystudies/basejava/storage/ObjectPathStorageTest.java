package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public static PathStorage pathStorage = new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer());
    //public static PathStorage pathStorage = new PathStorage("HW\\storage", new ObjectStreamSerializer());

    public ObjectPathStorageTest() {
        super(pathStorage);
    }
}
