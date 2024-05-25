package com.sample.notetaking.service;

import com.sample.notetaking.model.Note;
import com.sample.notetaking.model.response.NoteResponse;
import io.vavr.control.Either;

import java.util.List;

public interface NoteService {
    public Either<String, NoteResponse> createNote(Note noteDto);

    public Either<String, List<NoteResponse>> getNotes();

    public Either<String, NoteResponse> getNote(int id);

    public Either<String, NoteResponse> updateNote(int id, Note noteDto);

    public Either<String, String> deleteNote(int id);


}
