package com.sample.notetaking.controller;

import com.sample.notetaking.model.Note;
import com.sample.notetaking.model.response.ErrorResponseObj;
import com.sample.notetaking.model.response.NoteResponse;
import com.sample.notetaking.model.response.response;
import com.sample.notetaking.model.response.ResponseObject;
import com.sample.notetaking.service.NoteService;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/notes")
    public ResponseEntity<ResponseObject> createNote(@RequestBody Note noteToAdd) {
        ResponseObject response = new ResponseObject();

        Either<String, NoteResponse> serviceResponse = noteService.createNote(noteToAdd);
        if(serviceResponse.isLeft()) {
            response.setResponse(null);
            response.setError(new ErrorResponseObj(serviceResponse.getLeft()));
            response.setStatus("FAILED");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            response.setResponse(new response(serviceResponse.get()));
            response.setError(null);
            response.setStatus("SUCCESS");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

    @GetMapping("/notes")
    public ResponseEntity<ResponseObject> getAllNotes() {
        ResponseObject response = new ResponseObject();

        Either<String, List<NoteResponse>> serviceResponse = noteService.getNotes();
        if(serviceResponse.isLeft()) {
            response.setResponse(null);
            response.setError(new ErrorResponseObj(serviceResponse.getLeft()));
            response.setStatus("FAILED");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            response.setResponse(new response(serviceResponse.get()));
            response.setError(null);
            response.setStatus("SUCCESS");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
