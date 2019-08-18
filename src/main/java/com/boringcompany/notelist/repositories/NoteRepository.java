package com.boringcompany.notelist.repositories;

import com.boringcompany.notelist.models.Note;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {
}
