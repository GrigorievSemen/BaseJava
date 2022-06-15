package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.exception.StorageException;
import ru.mystudies.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileStorage extends AbstractStorage<File> implements WorkToFiles {

    private final File directory;

    public AbstractFileStorage(File directory) {
        if (!directory.canRead()) {
            throw new IllegalArgumentException("File " + directory.getName() + " cannot be read");
        }
        if (!directory.canWrite()) {
            throw new IllegalArgumentException("File " + directory.getName() + " cannot be write");
        }
        this.directory = directory;
    }

    abstract public void doWrite(Resume resume, OutputStream os);

    abstract public Resume doRead(InputStream is);

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (Exception e) {
            throw new StorageException("Error - file not written " + resume.getUuid(), e.getMessage());
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doUpdate(resume, file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getName(), e.getMessage());
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                doDelete(f);
            }
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
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Error - directory id empty ", directory.getName());
        }
        return list.length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        File files[] = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                list.add(doGet(file));
            }
            return list;
        }
        throw new StorageException("Error - file is not read", directory.getName());
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (Exception e) {
            throw new StorageException("Error - file not read " + file.getName(), e.getMessage());
        }
    }

    @Override
    protected boolean isExist(File path) {
        return path.exists();
    }
}