package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.exception.StorageException;
import ru.mystudies.basejava.model.Resume;
import ru.mystudies.basejava.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private StreamSerializer streamSerializer;

    public PathStorage(String dir, StreamSerializer streamSerializer) {
        System.out.println(dir);
        Objects.requireNonNull(dir, "directory must not be null");
        this.directory = Paths.get(dir);
        this.streamSerializer = streamSerializer;

        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            System.out.println(directory);
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }

    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (Exception e) {
            throw new StorageException("Error - Path not written " + resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + path, getFileName(path), e);
        }
        doUpdate(resume, path);
    }

    @Override
    public void clear() {
        getPathsList().forEach(this::doDelete);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Error - Path not deleted ", getFileName(path), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    public List<Resume> doCopyAll() {
        return getPathsList().stream().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (Exception e) {
            throw new StorageException("Error - Path not read " + path, getFileName(path), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public int size() {
        return getPathsList().size();
    }

    private List<Path> getPathsList() {
        List<Path> list = null;
        try {
            list = Files.list(directory).collect(Collectors.toList());
            return list;
        } catch (IOException e) {
            throw new StorageException("Error - Path is not read", directory.toString(), e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
