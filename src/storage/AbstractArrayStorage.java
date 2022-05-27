package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        if (size < 1) return new Resume[0];
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        int index = (Integer) searchKey;
        return storage[index];
    }

    @Override
    protected boolean isExist(Object searchKey) {
        int index = (Integer) searchKey;
        return (index >= 0);
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            int index = (Integer) searchKey;
            saveResume(index, resume);
            size++;
        }
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        int index = (Integer) searchKey;
        storage[index] = resume;
    }

    @Override
    protected void doDelete(Object searchKey) {
        int index = (Integer) searchKey;
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        size--;
    }

    public int size() {
        return size;
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void saveResume(int index, Resume resume);
}
