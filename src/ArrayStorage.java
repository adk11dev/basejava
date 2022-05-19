import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume resume) {
        storage[size++] = resume;
    }

    Resume get(String uuid) {
        int indexResume = getResumeIndexByUuid(uuid);

        if (indexResume >= 0) return storage[indexResume];

        return null;
    }

    void delete(String uuid) {
        int indexResume = getResumeIndexByUuid(uuid);

        if (indexResume >= 0) System.arraycopy(storage, indexResume + 1, storage, indexResume, size - indexResume);
        size--;
    }

    Resume[] getAll() {
        if (size < 1) return new Resume[0];
        return Arrays.copyOfRange(storage, 0, size);
    }

    int size() {
        return size;
    }

    private int getResumeIndexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) return i;
        }

        return -1;
    }
}
