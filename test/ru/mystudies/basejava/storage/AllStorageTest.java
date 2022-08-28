package ru.mystudies.basejava.storage;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        SqlStorageTest.class
})
public class AllStorageTest {
}
