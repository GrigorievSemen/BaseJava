package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.exception.StorageException;
import ru.mystudies.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> implements WorkToFiles {

    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    abstract public void doWrite(Resume resume, OutputStream os);

    abstract public Resume doRead(InputStream is);

    @Override
    protected void doUpdate(Resume resume, Path Path) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(Path.toFile())));
        } catch (Exception e) {
            throw new StorageException("Error - Path not written " + resume.getUuid(), e.getMessage());
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
            doUpdate(resume, path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path, e.getMessage());
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Error - Path not deleted ", path.toString());
        }
    }

    @Override
    public int size() {
        List<Path> list;
        try {
            list = Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Error - directory id empty ", directory.toString());
        }
        return list.size();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(Path.of(uuid));
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        try {
            List<Path> list = Files.list(directory).collect(Collectors.toList());
            if (list != null) {
                for (Path path : list) {
                    resumes.add(doGet(path));
                }
            }
            return resumes;
        } catch (IOException e) {
            throw new StorageException("Error - Path is not read", directory.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (Exception e) {
            throw new StorageException("Error - Path not read " + path, e.getMessage());
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }
}