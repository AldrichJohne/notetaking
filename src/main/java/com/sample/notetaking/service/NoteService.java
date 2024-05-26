package com.sample.notetaking.service;

import com.sample.notetaking.model.Note;
import com.sample.notetaking.model.response.EitherLeftDto;
import com.sample.notetaking.model.response.NotesDto;
import io.vavr.control.Either;

import java.util.List;

public interface NoteService {
    Either<EitherLeftDto, NotesDto> createNote(Note noteDto);

    Either<EitherLeftDto, List<NotesDto>> getNotes();

    Either<EitherLeftDto, NotesDto> getNote(int id);

    Either<EitherLeftDto, NotesDto> updateNote(int id, Note noteDto);

    Either<EitherLeftDto, String> deleteNote(int id);


}
