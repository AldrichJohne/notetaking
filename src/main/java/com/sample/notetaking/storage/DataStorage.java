package com.sample.notetaking.storage;

import com.sample.notetaking.model.Note;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStorage {
    private static final Map<Integer, Note> noteStorageMap = new ConcurrentHashMap<>();

    private DataStorage() {
    }

    private static class Holder {
        private static final DataStorage INSTANCE = new DataStorage();
    }

    public static DataStorage getInstance() {
        return Holder.INSTANCE;
    }

    public int addNote(Note note) {
        int id = generateNextId();
        noteStorageMap.put(id, note);
        return id;
    }

    public Note getNoteById(Integer id) {
        Note existingNote = noteStorageMap.get(id);
        if (existingNote != null) {
            return noteStorageMap.get(id);
        } else {
            throw new IllegalArgumentException("Note with ID " + id + " does not exist");
        }
    }

    public void updateNote(int id, String newTitle, String newBody) {
        Note existingNote = noteStorageMap.get(id);
        if (existingNote != null) {
            existingNote.setTitle(newTitle);
            existingNote.setBody(newBody);
            noteStorageMap.put(id, existingNote);
        } else {
            throw new IllegalArgumentException("Note with ID " + id + " does not exist");
        }
    }

    public void removeNoteById(Integer id) {
        Note existingNote = noteStorageMap.get(id);
        if(existingNote != null) {
            noteStorageMap.remove(id);
        } else {
            throw new IllegalArgumentException("Note with ID " + id + " does not exist");
        }
    }

    public Map<Integer, Note> getAllNotes() {
        return new ConcurrentHashMap<>(noteStorageMap);
    }

    private int generateNextId() {
        int nextId = 0;
        while (noteStorageMap.containsKey(nextId)) {
            nextId++;
        }
        return nextId;
    }
}