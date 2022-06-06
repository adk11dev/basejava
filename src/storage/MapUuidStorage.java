package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        String uuid = searchKey.toString();
        return storage.containsKey(uuid);
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        String uuid = searchKey.toString();
        storage.put(uuid, resume);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        String uuid = searchKey.toString();
        storage.put(uuid, resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        String uuid = searchKey.toString();
        storage.remove(uuid);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        String uuid = searchKey.toString();
        return storage.get(uuid);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
