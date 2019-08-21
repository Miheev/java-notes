package com.boringcompany.notelist.services;

import com.boringcompany.notelist.models.Note;
import com.boringcompany.notelist.models.NoteDTO;

import java.util.List;
import java.util.Optional;

public interface NoteService {
  List<NoteDTO> getAllNotes();

  Optional<Note> getNote(long noteId);

  Note createNote(Note noteDetails);

  Optional<Note> updateNote(long noteId, NoteDTO noteDetails);

  void deleteNote(long noteId);
}
