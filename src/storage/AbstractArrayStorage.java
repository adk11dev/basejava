package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size = 0;

    public void save(Resume resume) {
        if (size + 1 > storage.length) {
            System.out.println("Array is full");
            return;
        }

        int index = getIndex(resume.getUuid());

        if (index >= 0) {
            System.out.printf("Resume %s already exists\n", resume.getUuid());
        } else {
            saveResume(index, resume);
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.printf("Resume %s not found\n", resume.getUuid());
        }
    }

    public Resume[] getAll() {
        if (size < 1) return new Resume[0];
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index >= 0) {
            size--;
            System.arraycopy(storage, index + 1, storage, index, size - index);
        } else {
            System.out.printf("Resume %s not found\n", uuid);
        }
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index >= 0) {
            return storage[index];
        } else {
            System.out.printf("Resume %s not found\n", uuid);
            return null;
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void saveResume(int index, Resume resume);
}
