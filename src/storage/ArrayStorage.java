package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[3];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size + 1 > storage.length) {
            System.out.println("Array is full");
            return;
        }

        int index = getResumeIndexByUuid(resume.getUuid());

        if (index >= 0) {
            System.out.printf("Resume %s already exists\n", resume.getUuid());
        } else {
            storage[size++] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = getResumeIndexByUuid(uuid);

        if (index >= 0) {
            return storage[index];
        } else {
            System.out.printf("Resume %s not found\n", uuid);
            return null;
        }
    }

    public void update(Resume resume) {
        int index = getResumeIndexByUuid(resume.getUuid());

        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.printf("Resume %s not found\n", resume.getUuid());
        }
    }

    public void delete(String uuid) {
        int index = getResumeIndexByUuid(uuid);

        if (index >= 0) {
            size--;
            System.arraycopy(storage, index + 1, storage, index, size - index);
        } else {
            System.out.printf("Resume %s not found\n", uuid);
        }
    }

    public Resume[] getAll() {
        if (size < 1) return new Resume[0];
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private int getResumeIndexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) return i;
        }
        return -1;
    }
}
