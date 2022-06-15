package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.exception.StorageException;
import ru.mystudies.basejava.model.Resume;
import ru.mystudies.basejava.storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {

    private final Path directory;
    private StreamSerializer streamSerializer;

    public PathStorage(String dir, StreamSerializer streamSerializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.streamSerializer = streamSerializer;
    }

    @Override
    protected void doUpdate(Resume resume, Path Path) {
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(Path.toFile())));
        } catch (Exception e) {
            throw new StorageException("Error - Path not written " + resume.getUuid(), e.getMessage());
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path, e.getMessage());
        }
        doUpdate(resume, path);
    }

    @Override
    public void clear() {
        dirExist().forEach(this::doDelete);
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
        return dirExist().size();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(Path.of(uuid));
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        for (Path path : dirExist()) {
            resumes.add(doGet(path));
        }
        return resumes;
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (Exception e) {
            throw new StorageException("Error - Path not read " + path, e.getMessage());
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    private List<Path> dirExist() {
        List<Path> list = null;
        try {
            list = Files.list(directory).collect(Collectors.toList());
            if (list != null) {
                return list;
            }
        } catch (IOException e) {
        }
        throw new StorageException("Error - Path is not read", directory.toString());
    }
}
