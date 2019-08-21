package com.boringcompany.notelist.services;

import com.boringcompany.notelist.config.NodeListProperties;
import com.boringcompany.notelist.models.Note;
import com.boringcompany.notelist.models.NoteDTO;
import com.boringcompany.notelist.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {
  private final NoteRepository noteRepository;
  private final NodeListProperties appConfig;

  @Override
  public List<NoteDTO> getAllNotes() {
    return noteRepository.findAll(appConfig.getLimitedPageable())
      .stream()
      .map(NoteDTO::new)
      .collect(Collectors.toList());
  }

  @Override
  public Optional<Note> getNote(final long noteId) {
    return noteRepository.findById(noteId);
  }

  @Override
  public Note createNote(final Note noteDetails) {
    return noteRepository.save(noteDetails);
  }

  @Transactional
  @Override
  public Optional<Note> updateNote(final long noteId, final NoteDTO noteDetails) {
    Optional<Note> noteRaw = getNote(noteId);
    if (!noteRaw.isPresent()) {
      return noteRaw;
    }

    Note note = noteRaw.get();
    note.setName(noteDetails.getName());
    note.setDescription(noteDetails.getDescription());

    return Optional.of(noteRepository.save(note));
  }

  @Override
  public void deleteNote(final long noteId) {
    try {
      noteRepository.deleteById(noteId);
    } catch (EmptyResultDataAccessException exception) {
      log.debug(exception.getMessage());
    }
  }
}
