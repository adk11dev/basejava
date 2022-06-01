package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

abstract class AbstractStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_UUID1 = new Resume(UUID_1);
    private static final Resume RESUME_UUID2 = new Resume(UUID_2);
    private static final Resume RESUME_UUID3 = new Resume(UUID_3);
    private static final Resume RESUME_UUID4 = new Resume(UUID_4);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();

        storage.save(RESUME_UUID1);
        storage.save(RESUME_UUID2);
        storage.save(RESUME_UUID3);
    }

    @Test
    void save() {
        int sizeBeforeAdd = storage.size();
        int expectedSize = sizeBeforeAdd + 1;

        storage.save(RESUME_UUID4);

        Assertions.assertEquals(expectedSize, storage.size());
        Assertions.assertEquals(RESUME_UUID4, storage.get(UUID_4));
    }

    @Test
    void update() {
        Resume expectedResume = RESUME_UUID3;

        storage.update(RESUME_UUID2);

        Assertions.assertTrue(expectedResume == storage.get(UUID_3));
    }

    @Test
    void delete() {
        int sizeBeforeDelete = storage.size();
        int expectedSize = sizeBeforeDelete - 1;

        storage.delete(UUID_1);

        Assertions.assertEquals(expectedSize, storage.size());
    }

    @Test
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete("dummy");
        });
    }

    @Test
    void clear() {
        int expectedSize = 0;

        storage.clear();

        Assertions.assertEquals(expectedSize, storage.size());
    }

    @Test
    void getAll() {
        int expectedSize = storage.size();
        Resume[] result = storage.getAll();

        Assertions.assertEquals(expectedSize, result.length);
    }

    @Test
    void get() {
        Resume expectedResume = RESUME_UUID1;

        Assertions.assertEquals(expectedResume, storage.get(UUID_1));
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(RESUME_UUID1);
        });
    }

    @Disabled
    @Test
    void checkStorageOverflow() {
        Assertions.assertThrows(StorageException.class, () -> {
            try {
                for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    storage.save(new Resume());
                }
            } catch (StorageException e) {
                Assertions.fail();
            }
            storage.save(new Resume());
        });
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }
}