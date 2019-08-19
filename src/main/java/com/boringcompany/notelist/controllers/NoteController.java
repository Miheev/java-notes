package com.boringcompany.notelist.controllers;

import com.boringcompany.notelist.config.SwaggerDocumentationConfig;
import com.boringcompany.notelist.models.Note;
import com.boringcompany.notelist.services.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api
@RestController
@RequiredArgsConstructor
public class NoteController {
  static final String ENDPOINT = "/api/notes";
  static final String ENDPOINT_ENTITY = ENDPOINT + "/{noteId}";

  private final NoteService noteService;

  @ApiOperation(value = "GET note list",
    tags = {SwaggerDocumentationConfig.NOTE_LIST},
    response = Note.class,
    responseContainer = "List")
  @GetMapping(value = ENDPOINT,
    consumes = {MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})

  public List<Note> getAllNotes() {
    return noteService.getAllNotes();
  }

  @ApiOperation(value = "Save new note",
    tags = {SwaggerDocumentationConfig.NOTE_LIST},
    response = Note.class)
  @PostMapping(value = ENDPOINT,
    consumes = {MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})

  public Note createNote(
    @ApiParam(value = "New note details", required = true) @Valid @RequestBody final Note noteDetails) {
    return noteService.createNote(noteDetails);
  }

  @ApiOperation(value = "GET note",
    tags = {SwaggerDocumentationConfig.NOTE_LIST},
    response = Note.class)
  @ApiResponses({
    @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Note not found")
  })
  @GetMapping(value = ENDPOINT_ENTITY,
    consumes = {MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})

  public ResponseEntity<Note> getNote(@ApiParam(value = "Note ID", required = true) @PathVariable final long noteId) {
    return optionalResponse(noteService.getNote(noteId));
  }

  @ApiOperation(value = "Update note",
    tags = {SwaggerDocumentationConfig.NOTE_LIST},
    response = Note.class)
  @ApiResponses({
    @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Note not found")
  })
  @PutMapping(value = ENDPOINT_ENTITY,
    consumes = {MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})

  public ResponseEntity<Note> updateNote(
    @ApiParam(value = "Note ID", required = true) @PathVariable final long noteId,
    @ApiParam(value = "Note details", required = true) @Valid @RequestBody final Note noteDetails) {
    return optionalResponse(noteService.updateNote(noteId, noteDetails));
  }

  /**
   * Let operation be idempotent for consistency: always return 200, even if entity doesn't exist
   *
   * @param noteId number
   */
  @ApiOperation(value = "Delete note",
    tags = {SwaggerDocumentationConfig.NOTE_LIST})
  @ApiResponses({
    @ApiResponse(
      code = HttpServletResponse.SC_OK,
      response = void.class,
      message = "Successfully remove the given note")
  })
  @DeleteMapping(ENDPOINT_ENTITY)

  public void deleteNote(@ApiParam(value = "Note ID", required = true) @PathVariable final long noteId) {
    noteService.deleteNote(noteId);
  }

  private ResponseEntity<Note> optionalResponse(final Optional<Note> note) {
    return note.map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
  }
}
