package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveResume(int index, Resume resume) {
        index = index * (-1) - 1;

        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
        size++;
    }
}
