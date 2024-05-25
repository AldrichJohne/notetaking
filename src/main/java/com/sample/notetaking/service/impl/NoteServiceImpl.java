package com.sample.notetaking.service.impl;

import com.sample.notetaking.model.Note;
import com.sample.notetaking.model.response.NoteResponse;
import com.sample.notetaking.service.NoteService;
import com.sample.notetaking.storage.DataStorage;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {
    DataStorage storage = DataStorage.getInstance();

    @Override
    public Either<String, NoteResponse> createNote(Note noteDto) {
        try {
            return Either.right(new NoteResponse(storage.addNote(noteDto), noteDto.getTitle(), noteDto.getBody()));
        } catch (Exception exception){
            return Either.left("Error creating a note");
        }
    }

    @Override
    public Either<String, List<NoteResponse>> getNotes() {
        try {
            return Either.right(storage.getAllNotes().entrySet().stream()
                    .map(entry -> new NoteResponse(entry.getKey(), entry.getValue().getTitle(), entry.getValue().getBody()))
                    .collect(Collectors.toList()));
        } catch (Exception exception) {
            return Either.left("Error retrieving all notes");
        }
    }

    @Override
    public Either<String, NoteResponse> getNote(int id) {
        try {
            Note note = storage.getNoteById(id);
            return Either.right(new NoteResponse(id, note.getTitle(), note.getBody()));
        } catch (Exception exception) {
            return Either.left(exception.getMessage());
        }
    }

    @Override
    public Either<String, NoteResponse> updateNote(int id, Note noteDto) {
        try {
            storage.updateNote(id, noteDto.getTitle(), noteDto.getBody());
            return Either.right(new NoteResponse(id, noteDto.getTitle(), noteDto.getBody()));
        } catch (Exception exception) {
            return Either.left(exception.getMessage());
        }
    }

    @Override
    public Either<String, String> deleteNote(int id) {
        try {
            storage.removeNoteById(id);
            return Either.right("Successfully remove not with ID: " + id);
        } catch (Exception exception) {
            return Either.left(exception.getMessage());
        }
    }
}
