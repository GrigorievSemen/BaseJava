package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.exception.StorageException;
import ru.mystudies.basejava.model.Resume;

import java.io.*;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    public ObjectStreamPathStorage(String directory) {
        super(directory);
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        } catch (IOException e) {
            throw new StorageException("Error write resume", null, e);
        }
    }

    @Override
    public Resume doRead(InputStream is) {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
