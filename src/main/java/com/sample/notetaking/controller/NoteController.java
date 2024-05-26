package com.sample.notetaking.controller;

import com.sample.notetaking.model.Note;
import com.sample.notetaking.model.response.*;
import com.sample.notetaking.service.NoteService;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/notes")
    public ResponseEntity<ResponseObject> createNote(@RequestBody Note noteToAdd) {
        log.info("Received Request to Create a Note, Body: {}", noteToAdd);
        Either<EitherLeftDto, NotesDto> serviceResponse = noteService.createNote(noteToAdd);
        return processResponse(serviceResponse);

    }

    @GetMapping("/notes")
    public ResponseEntity<ResponseObject> getAllNotes() {
        log.info("Received Request to Retrieve All Notes");
        Either<EitherLeftDto, List<NotesDto>> serviceResponse = noteService.getNotes();
        return processResponse(serviceResponse);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<ResponseObject> getNote(
            @PathVariable int id
    ) {
        log.info("Received Request to Retrieve a Specific Note with ID: {}", id);
        Either<EitherLeftDto, NotesDto> serviceResponse = noteService.getNote(id);
        return processResponse(serviceResponse);
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<ResponseObject> updateNote(
            @PathVariable int id,
            @RequestBody Note note
    ) {
        log.info("Received Request to Updated a Specific Note with ID: {} and Updated Value: {}", id, note);
        Either<EitherLeftDto, NotesDto> serviceResponse = noteService.updateNote(id, note);
        return processResponse(serviceResponse);
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<ResponseObject> updateNote(
            @PathVariable int id
    ) {
        log.info("Received Request to Remove a Specific Note with ID: {}", id);
        Either<EitherLeftDto, String> serviceResponse = noteService.deleteNote(id);
        return processResponse(serviceResponse);
    }

    private static <T> ResponseEntity<ResponseObject> processResponse(
            Either<EitherLeftDto, T> serviceResponse
    ) {
        ResponseObject response = new ResponseObject();
        if (serviceResponse.isLeft()) {
            response.setResponse(null);
            response.setError(new ErrorResponseObj(serviceResponse.getLeft()));
            response.setStatus("FAILED");
            log.error("Error Response {}", response);
            return new ResponseEntity<>(response, serviceResponse.getLeft().getStatus());
        } else {
            response.setResponse(new response(serviceResponse.get()));
            response.setError(null);
            response.setStatus("SUCCESS");
            log.info("Success Response {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

}
