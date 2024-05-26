package com.sample.notetaking.service.impl;

import com.sample.notetaking.model.Note;
import com.sample.notetaking.model.response.EitherLeftDto;
import com.sample.notetaking.model.response.NotesDto;
import com.sample.notetaking.service.NoteService;
import com.sample.notetaking.storage.DataStorage;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {
    DataStorage storage = DataStorage.getInstance();

    @Override
    public Either<EitherLeftDto, NotesDto> createNote(Note noteDto) {
        log.info("Validating Create Note request");
        if ("".equals(noteDto.getTitle())
                || "".equals(noteDto.getBody())
                || noteDto.getTitle() == null
                || noteDto.getBody() == null) {
            log.error("Invalid Create Note request");
            return Either.left(new EitherLeftDto(HttpStatus.BAD_REQUEST, "Missing required field"));
        }
        try {
            log.info("Creating Note, Note: {}", noteDto);
            return Either.right(new NotesDto(storage.addNote(noteDto), noteDto.getTitle(), noteDto.getBody()));
        } catch (Exception exception){
            log.error("Error Encountered While Creating a Note");
            return Either.left(new EitherLeftDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating a note"));
        }
    }

    @Override
    public Either<EitherLeftDto, List<NotesDto>> getNotes() {
        try {
            log.info("Retrieving Notes");
            return Either.right(storage.getAllNotes().entrySet().stream()
                    .map(entry -> new NotesDto(entry.getKey(), entry.getValue().getTitle(), entry.getValue().getBody()))
                    .toList());
        } catch (Exception exception) {
            log.error("Error Encountered While Retrieving All Notes");
            return Either.left(new EitherLeftDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving all notes"));
        }
    }

    @Override
    public Either<EitherLeftDto, NotesDto> getNote(int id) {
        try {
            log.info("Retrieving a Specific Note with ID {}", id);
            Note note = storage.getNoteById(id);
            return Either.right(new NotesDto(id, note.getTitle(), note.getBody()));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return Either.left(new EitherLeftDto(HttpStatus.BAD_REQUEST, exception.getMessage()));
        }
    }

    @Override
    public Either<EitherLeftDto, NotesDto> updateNote(int id, Note noteDto) {
        try {
            log.info("Updating Note with ID {} and Updated Note Value {}", id, noteDto);
            storage.updateNote(id, noteDto.getTitle(), noteDto.getBody());
            return Either.right(new NotesDto(id, noteDto.getTitle(), noteDto.getBody()));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return Either.left(new EitherLeftDto(HttpStatus.BAD_REQUEST, exception.getMessage()));
        }
    }

    @Override
    public Either<EitherLeftDto, String> deleteNote(int id) {
        try {
            log.info("Removing Note with ID {}", id);
            storage.removeNoteById(id);
            return Either.right("Successfully remove note with ID: " + id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return Either.left(new EitherLeftDto(HttpStatus.BAD_REQUEST, exception.getMessage()));
        }
    }
}
