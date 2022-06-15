package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface WorkToFiles {
    void doWrite(Resume resume, OutputStream os);

    Resume doRead(InputStream is);
}
