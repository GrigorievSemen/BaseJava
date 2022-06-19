package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.exception.StorageException;
import ru.mystudies.basejava.model.Resume;
import ru.mystudies.basejava.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private final File directory;
    private StreamSerializer streamSerializer;

    public FileStorage(File dir, StreamSerializer streamSerializer) {
        Objects.requireNonNull(dir, "directory must not be null");

        this.directory = dir;
        this.streamSerializer = streamSerializer;
        if (!directory.canRead()) {
            throw new IllegalArgumentException("File " + directory.getName() + " cannot be read");
        }
        if (!directory.canWrite()) {
            throw new IllegalArgumentException("File " + directory.getName() + " cannot be write");
        }

    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (Exception e) {
            throw new StorageException("Error - file not written " + resume.getUuid(), e.getMessage());
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getName(), e.getMessage());
        }
        doUpdate(resume, file);
    }

    @Override
    public void clear() {
        for (File f : dirExist()) {
            doDelete(f);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Error - file not deleted ", file.getName());
        }
    }

    @Override
    public int size() {
        return dirExist().length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();

        for (File file : dirExist()) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (Exception e) {
            throw new StorageException("Error - file not read " + file.getName(), e.getMessage());
        }
    }

    @Override
    protected boolean isExist(File path) {
        return path.exists();
    }

    private File[] dirExist() {
        File files[] = directory.listFiles();
        if (files != null) {
            return files;
        }
        throw new StorageException("Error - file is not read", directory.getName());
    }
}