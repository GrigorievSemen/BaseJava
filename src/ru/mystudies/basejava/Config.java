package ru.mystudies.basejava;

import ru.mystudies.basejava.storage.SqlStorage;
import ru.mystudies.basejava.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
   private static final File PROPS = new File("C:\\Users\\Sem\\Desktop\\Programming\\JavaProject\\ReadyProjects\\BaseJava\\HW\\config\\resumes.properties");
    //private static final String PROPS = "/resumes.properties";
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final Storage storage;

    private Properties props = new Properties();
    private Config() {
       // try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public Storage getStorage() {
        return storage;
    }

    public File getStorageDir() {
        return storageDir;
    }

}