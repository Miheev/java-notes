package com.boringcompany.notelist.services;

import com.boringcompany.notelist.models.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
  List<Note> getAllNotes();

  Optional<Note> getNote(long noteId);

  Note createNote(Note noteDetails);

  Optional<Note> updateNote(long noteId, Note noteDetails);

  void deleteNote(long noteId);
}
