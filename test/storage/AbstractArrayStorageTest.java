package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_UUID1 = new Resume(UUID_1);
    private static final Resume RESUME_UUID2 = new Resume(UUID_2);
    private static final Resume RESUME_UUID3 = new Resume(UUID_3);
    private static final Resume RESUME_UUID4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();

        try {
            storage.save(RESUME_UUID1);
            storage.save(RESUME_UUID2);
            storage.save(RESUME_UUID3);
        } catch (StorageException e){
            Assertions.fail("Overflow occurred prematurely");
        }
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

        storage.update(RESUME_UUID3);

        Assertions.assertEquals(expectedResume, storage.get(UUID_3));
    }

    @Test
    void delete() {
        int sizeBeforeDelete = storage.size();
        int expectedSize = sizeBeforeDelete - 1;

        storage.delete(UUID_1);

        Assertions.assertEquals(expectedSize, storage.size());
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

        Assertions.assertEquals(expectedSize, storage.getAll().length);
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

    @Test
    void checkStorageOverflow() {
        Assertions.assertThrows(StorageException.class, () -> {
            storage.save(RESUME_UUID4);
        });
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }
}