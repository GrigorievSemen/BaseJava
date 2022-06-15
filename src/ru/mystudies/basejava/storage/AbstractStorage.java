package ru.mystudies.basejava.storage;

import ru.mystudies.basejava.exception.ExistStorageException;
import ru.mystudies.basejava.exception.NotExistStorageException;
import ru.mystudies.basejava.model.Resume;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract List<Resume> doCopyAll() throws IOException;

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = getExistingKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getNotExistingKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistingKey(uuid);
        doDelete(searchKey);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistingKey(uuid);
        return doGet(searchKey);
    }

    private SK getExistingKey(String uuid) {
        SK SK = getSearchKey(uuid);
        if (!isExist(SK)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return SK;
    }

    private SK getNotExistingKey(String uuid) {
        SK SK = getSearchKey(uuid);
        if (isExist(SK)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return SK;
    }

    public List<Resume> getAllSorted() throws IOException {
        List<Resume> list = doCopyAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }
}